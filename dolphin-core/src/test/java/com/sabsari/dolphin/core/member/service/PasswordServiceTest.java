package com.sabsari.dolphin.core.member.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sabsari.dolphin.core.auth.service.AuthorizationService;
import com.sabsari.dolphin.core.config.ContextConfig;
import com.sabsari.dolphin.core.history.service.HistoryService;
import com.sabsari.dolphin.core.member.domain.Application;
import com.sabsari.dolphin.core.member.domain.User;
import com.sabsari.dolphin.core.member.service.AdminService;
import com.sabsari.dolphin.core.member.service.PasswordService;
import com.sabsari.dolphin.core.member.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class})
@ActiveProfiles(value = "dev")
public class PasswordServiceTest {
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private HistoryService historyService;
	
	private static String GROUP_KEY 		= null;
	private static String CLIENT_ID 		= null;
	
	private static String USER_KEY		= null;	
	private static String PASSWORD		= "aaaa1111";
	
	private static String EMAIL_ID		= "testtest@gg.com";
	
	@Before
	public void _테스트데이터생성() {
		GROUP_KEY = adminService.createUserGroup("test_user_group");
		Application application = adminService.registApplication(GROUP_KEY, "test_app_name");
		CLIENT_ID = application.getClientId();
				
		User user = userService.createUser(CLIENT_ID, EMAIL_ID, null, null, PASSWORD, null, null, null);
		USER_KEY = user.getUserKey();
	}
	
	@After
	public void _테스트데이터삭제() {
		userService.deleteUser(USER_KEY);
		adminService.unregistApplication(CLIENT_ID);
		adminService.deleteUserGroup(GROUP_KEY);
		historyService.clearHistory();
	}
		
	@Test
	public void _패스워드초기화() {
		String newPassword = passwordService.resetPassword(USER_KEY);
		authorizationService.verifyUserPassword(CLIENT_ID, EMAIL_ID, newPassword);
	}
	
	@Test
	public void _패스워드변경() {
		String newPassword = "yyyy2222";
		passwordService.updatePassword(USER_KEY, PASSWORD, newPassword);
		String userKey = authorizationService.verifyUserPassword(CLIENT_ID, EMAIL_ID, newPassword);
		assertEquals(userKey, USER_KEY);
		
		String newPassword_ = "kkkk3333";
		passwordService.updatePassword(USER_KEY, newPassword_);
		String userKey_ = authorizationService.verifyUserPassword(CLIENT_ID, EMAIL_ID, newPassword_);
		assertEquals(userKey_, USER_KEY);
	}
}
