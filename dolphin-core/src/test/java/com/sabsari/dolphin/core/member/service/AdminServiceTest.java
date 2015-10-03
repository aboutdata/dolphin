package com.sabsari.dolphin.core.member.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sabsari.dolphin.core.config.ContextConfig;
import com.sabsari.dolphin.core.exception.business.NotFoundClientIdException;
import com.sabsari.dolphin.core.exception.business.NotFoundGroupException;
import com.sabsari.dolphin.core.member.domain.Application;
import com.sabsari.dolphin.core.member.domain.UserGroup;
import com.sabsari.dolphin.core.member.service.AdminService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class})
@ActiveProfiles(value = "dev")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminServiceTest {

	@Autowired
	AdminService adminService;
	
	private static String GROUP_KEY = null;
	private static String CLIENT_ID = null;
	private static String CLIENT_SECRET = null;
	
	private final String groupName = "test_user_group";
	private final String appName = "test_application";
	
	@Test
	public void _0_createUserGroupTest() {		
		GROUP_KEY = adminService.createUserGroup(groupName);
	}
	
	@Test
	public void _1_registApplicationTest() {
		Application application = adminService.registApplication(GROUP_KEY, appName);
		CLIENT_ID = application.getClientId();
		CLIENT_SECRET = application.getClientSecret();
	}
	
	@Test
	@Transactional
	public void _2_getUserGroupTest() {
		UserGroup userGroup = adminService.getUserGroup(GROUP_KEY);
		assertThat(groupName, is(userGroup.getGroupName()));
		assertThat(CLIENT_ID, is(userGroup.getApplications().get(0).getClientId()));
	}
	
	@Test
	public void _3_getApplicationTest() {
		Application application = adminService.getApplication(CLIENT_ID);
		assertThat(appName, is(application.getAppName()));
		assertThat(GROUP_KEY, is(application.getGroupKey()));
	}
	
	@Test
	public void _4_resetClientSecretTest() {
		String newClientSecret = adminService.resetClientSecret(CLIENT_ID);
		
		assertNotEquals(newClientSecret, CLIENT_SECRET);
	}
	
	@Test(expected=NotFoundClientIdException.class)
	public void _5_unregistApplicationTest() {
		adminService.unregistApplication(CLIENT_ID);
		
		adminService.getApplication(CLIENT_ID);
	}
	
	@Test(expected=NotFoundGroupException.class)
	public void _6_deleteUserGroupTest() {
		adminService.deleteUserGroup(GROUP_KEY);
		
		adminService.getUserGroup(GROUP_KEY);
	}
	
	@Test
	public void _7_createAdminTest() {
		Application application = adminService.createAdmin();
		assertEquals("11e61fea-e649-41b5-a290-efa3e0255d67", application.getGroupKey());
		assertEquals("HKxyG6hiETcnfQjrUbQcJ7rqP0DBJITj", application.getClientSecret());
	}
}
