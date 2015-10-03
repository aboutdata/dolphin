package com.sabsari.dolphin.test.core.member.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sabsari.dolphin.core.config.ContextConfig;
import com.sabsari.dolphin.core.history.service.HistoryService;
import com.sabsari.dolphin.core.member.domain.Application;
import com.sabsari.dolphin.core.member.domain.User;
import com.sabsari.dolphin.core.member.domain.code.AuthStatusCode;
import com.sabsari.dolphin.core.member.domain.code.Gender;
import com.sabsari.dolphin.core.member.domain.code.YesNo;
import com.sabsari.dolphin.core.member.service.AdminService;
import com.sabsari.dolphin.core.member.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class})
@ActiveProfiles(value = "dev")
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private HistoryService historyService;
	
	private static String GROUP_KEY;
	private static String CLIENT_ID;
	
	private static String USER_KEY		= null;	
	private static String EMAIL_ID		= "sabsabsab@gg.com";
	
	private final String FIRST_NAME		= "first";
	private final String LAST_NAME		= "last";		
	private final String BIRTHDAY		= "19990101";		
	private final String PASSWORD		= "aaaa1111";
	private final String PHONE			= "01099998888";
	private final Gender GENDER			= Gender.MALE;
	private final AuthStatusCode AUTH_STATUS_CODE = AuthStatusCode.NORMAL;
	private final YesNo EMAIL_VERIFIED	= YesNo.NO;
	
	@Before
	@Transactional
	public void _테스트용그룹_어플리케이션정보생성() {		
		GROUP_KEY = adminService.createUserGroup("test_user_group");
		Application application = adminService.registApplication(GROUP_KEY, "test_application");
		CLIENT_ID = application.getClientId();
		
		User user = userService.createUser(CLIENT_ID, EMAIL_ID, AUTH_STATUS_CODE, EMAIL_VERIFIED, FIRST_NAME, LAST_NAME, PASSWORD, PHONE, BIRTHDAY, GENDER);
		USER_KEY = user.getUserKey();
	}
	
	@After
	@Transactional
	public void _테스트용그룹_어플리케이션정보삭제() {
		userService.deleteUser(USER_KEY);
		adminService.unregistApplication(CLIENT_ID);
		adminService.deleteUserGroup(GROUP_KEY);
		historyService.clearHistory();
	}
		
	@Test
	public void _유저삭제_v1_1() {
		User user = userService.createUser(CLIENT_ID, "sdkfasld@lsidf.com", null, null, "aaaa1111", null, null, null);
		userService.deleteUser_v1_1(user.getUserKey(), CLIENT_ID);
//		historyService.
	}
	
	@Test
	public void _유저조회1() {
		User user = userService.getUser(USER_KEY);
		
		assertThat(EMAIL_ID, is(user.getEmailId()));
		assertThat(AUTH_STATUS_CODE, is(user.getAuthentication().getAuthStatusCode()));
	}
	
	@Test
	public void _유저조회2() {
		User user = userService.getUser(CLIENT_ID, EMAIL_ID);
		
		assertThat(USER_KEY, is(user.getUserKey()));
		assertThat(AUTH_STATUS_CODE, is(user.getAuthentication().getAuthStatusCode()));
	}
	
	@Test
	public void _이메일중복확인() {		
		boolean result = userService.isEmailIdAvailable(CLIENT_ID, EMAIL_ID);		
		assertFalse(result);
		
		result = userService.isEmailIdAvailable(CLIENT_ID, "eidjehjgfn@skld.com");
		assertTrue(result);
	}	
	
	@Test
	public void _이메일인증확인() {
		boolean result = userService.isEmailVerified(USER_KEY);		
		assertFalse(result);
		
		userService.verifyEmail(USER_KEY);
		
		result = userService.isEmailVerified(USER_KEY);	
		assertTrue(result);
	}
	
	@Test
	public void _권한확인() {
		boolean result = userService.hasPermission(USER_KEY, CLIENT_ID);
		assertTrue(result);
		
		result = userService.hasPermission(USER_KEY, "anonymous");
		assertFalse(result);
	}
	
	@Test
	public void _유저프로파일변경() {
		String firstName = "sari";
		String lsatName = "sab";
		String birthday = "20000819";
		String phone = "01166662222";
		Gender gender = Gender.FEMALE;
		
		User user = userService.updateUser(USER_KEY, firstName, lsatName, birthday, phone, gender);
		
		assertNotEquals(GENDER.getCode(), user.getProfile().getGender());
		assertEquals(firstName, user.getProfile().getFirstName());
	}
	
	@Test
	public void _이메일변경() {
		String emailId = "dkdki@sk.com";		
		userService.updateEmailId(USER_KEY, emailId, YesNo.YES);
		
		boolean result = userService.isEmailVerified(USER_KEY);
		assertTrue(result);
		
		User user = userService.getUser(USER_KEY);
		assertEquals(emailId, user.getEmailId());
		
		EMAIL_ID = emailId;
	}
}
