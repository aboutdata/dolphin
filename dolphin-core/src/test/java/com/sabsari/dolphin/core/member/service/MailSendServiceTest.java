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

import com.sabsari.dolphin.core.config.ContextConfig;
import com.sabsari.dolphin.core.member.domain.Application;
import com.sabsari.dolphin.core.member.domain.EmailVerification;
import com.sabsari.dolphin.core.member.domain.User;
import com.sabsari.dolphin.core.member.repository.EmailVerificationRepository;
import com.sabsari.dolphin.core.member.service.AdminService;
import com.sabsari.dolphin.core.member.service.MailSendService;
import com.sabsari.dolphin.core.member.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class})
@ActiveProfiles(value = "dev")
public class MailSendServiceTest {
	
	@Autowired
	MailSendService mailSendService;
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
    private EmailVerificationRepository emailVerificationRepository;
	
	private static String GROUP_KEY 		= null;
	private static String CLIENT_ID 		= null;
	
	private static String USER_KEY		= null;
	private static String V_VALUE		= null;
	private static String PASSWORD		= "aaaa1111";
	
	private static String EMAIL_ID		= "testtest@gg.com";
	
	@Before
	public void _테스트데이터생성() {
		GROUP_KEY = adminService.createUserGroup("test_user_group");
		Application application = adminService.registApplication(GROUP_KEY, "test_app_name");
		CLIENT_ID = application.getClientId();
		
		User user = userService.createUser(CLIENT_ID, EMAIL_ID, null, null, PASSWORD, null, null, null);
		USER_KEY = user.getUserKey();
		EmailVerification emailVerification = emailVerificationRepository.findOne(user.getUserSeq());
		V_VALUE = emailVerification.getVerificationValue();
	}
	
	@After
	public void _테스트데이터삭제() {
		userService.deleteUser(USER_KEY);
		adminService.unregistApplication(CLIENT_ID);
		adminService.deleteUserGroup(GROUP_KEY);
	}
	
	@Test
	public void _이메일인증() {
		String userKey = mailSendService.verifyEmail(V_VALUE);
		assertEquals(USER_KEY, userKey);
	}
		
//	@Test
	public void _비동기메일전송테스트() {
		System.out.println("before.........");
		mailSendService.sendMail("aaaa", "sabsari@sk.com", "제목입니다.", "본문입니다.");		
		System.out.println("after.........");
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("finish.........");
	}	
}
