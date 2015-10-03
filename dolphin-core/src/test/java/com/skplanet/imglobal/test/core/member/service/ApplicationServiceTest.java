package com.sabsari.dolphin.test.core.member.service;

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
import com.sabsari.dolphin.core.member.service.AdminService;
import com.sabsari.dolphin.core.member.service.ApplicationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class})
@ActiveProfiles(value = "dev")
public class ApplicationServiceTest {
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private AdminService adminService;
	
	private static String GROUP_KEY 		= null;
	private static String CLIENT_ID 		= null;
	private static String CLIENT_SECRET		= null;
		
	@Before
	public void _테스트데이터생성() {
		GROUP_KEY = adminService.createUserGroup("test_user_group");
		Application application = adminService.registApplication(GROUP_KEY, "test_app_name");
		CLIENT_ID = application.getClientId();
		CLIENT_SECRET = application.getClientSecret();
	}
	
	@After
	public void _테스트데이터삭제() {
		adminService.unregistApplication(CLIENT_ID);
		adminService.deleteUserGroup(GROUP_KEY);
	}
	
	@Test
	public void _그룹키조회() {
		String groupKey = applicationService.getGroupKey(CLIENT_ID);
		assertEquals(GROUP_KEY, groupKey);
	}
	
	@Test
	public void _어플리케이션조회() {
		Application application = applicationService.getApplication(CLIENT_ID);
		assertNotNull(application);
		assertEquals(GROUP_KEY, application.getGroupKey());
	}
	
	@Test
	public void _어플리케이션확인() {
		boolean result = applicationService.isExistApplication(CLIENT_ID);
		assertTrue(result);
	}
	
	@Test
	public void _시크릿리셋() {
		String newSecret = applicationService.resetSecret(CLIENT_ID);
		assertNotEquals(newSecret, CLIENT_SECRET);
	}
}
