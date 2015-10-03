package com.sabsari.dolphin.test.core.auth.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sabsari.dolphin.core.auth.domain.AccessToken;
import com.sabsari.dolphin.core.auth.domain.RefreshToken;
import com.sabsari.dolphin.core.auth.domain.code.TokenTypeHint;
import com.sabsari.dolphin.core.auth.service.AuthorizationService;
import com.sabsari.dolphin.core.config.ContextConfig;
import com.sabsari.dolphin.core.history.service.HistoryService;
import com.sabsari.dolphin.core.member.domain.Application;
import com.sabsari.dolphin.core.member.domain.User;
import com.sabsari.dolphin.core.member.service.AdminService;
import com.sabsari.dolphin.core.member.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class})
@ActiveProfiles(value = "dev")
public class AuthorizationServiceTest {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private HistoryService historyService;
	
	private static String ACCESS_TOKEN;
	private static String REFRESH_TOKEN;
		
	private static String GROUP_KEY 		= null;
	private static String CLIENT_ID 		= null;
	private static String CLIENT_SECRET		= null;
	
	private static String USER_KEY		= null;	
	private static String PASSWORD		= "aaaa1111";
	
	private static String EMAIL_ID		= "testtest@gg.com";
	
	@Before
	public void _pre() {
		GROUP_KEY = adminService.createUserGroup("test_user_group");
		Application application = adminService.registApplication(GROUP_KEY, "test_app_name");
		CLIENT_ID = application.getClientId();
		CLIENT_SECRET = application.getClientSecret();
		User user = userService.createUser(CLIENT_ID, EMAIL_ID, null, null, PASSWORD, null, null, null);
		USER_KEY = user.getUserKey();
	}
	
	@After
	public void _post() {
		userService.deleteUser(USER_KEY);
		adminService.unregistApplication(CLIENT_ID);
		adminService.deleteUserGroup(GROUP_KEY);
		historyService.clearHistory();
	}
	
	@Test
	public void _인증() {
		String userKey = authorizationService.verifyUserPassword(CLIENT_ID, EMAIL_ID, PASSWORD);
		assertEquals(USER_KEY, userKey);
	}
	
	@Test
	public void _클라이언트시크릿검증() {
		Application application = authorizationService.verifyClientSecret(CLIENT_ID, CLIENT_SECRET);
		assertEquals(GROUP_KEY, application.getGroupKey());
	}
	
	@Test
	public void _동의확인() {
		boolean result = authorizationService.hasPermission(USER_KEY, CLIENT_ID);
		assertTrue(result);
	}
	
	@Test
	public void _억세스토큰() {
		AccessToken accessToken01 = authorizationService.issueAccessToken(CLIENT_ID);
		ACCESS_TOKEN = accessToken01.getAccessToken();		
		assertEquals(CLIENT_ID, accessToken01.getOwner());
		
		AccessToken accessToken02 = authorizationService.issueAccessToken(CLIENT_ID);
		String accessTokenAgain = accessToken02.getAccessToken();		
		assertEquals(ACCESS_TOKEN, accessTokenAgain);
		
		// 조회
		AccessToken accessToken03 = authorizationService.verifyAccessToken(ACCESS_TOKEN);		
		assertEquals(CLIENT_ID, accessToken03.getOwner());
		
		// 삭제
		authorizationService.revokeToken(TokenTypeHint.ACCESS_TOKEN, ACCESS_TOKEN);
	}	
	
	@Test
	public void _리프레시토큰() {
		RefreshToken refreshToken01 = authorizationService.issueRefreshToken(USER_KEY);
		REFRESH_TOKEN = refreshToken01.getRefreshToken();
		assertEquals(USER_KEY, refreshToken01.getAccessToken().getOwner());
		assertFalse(refreshToken01.isExpired());
		
		RefreshToken refreshToken02 = authorizationService.issueRefreshToken(USER_KEY);
		String refreshTokenAgaing = refreshToken02.getRefreshToken();
		assertEquals(REFRESH_TOKEN, refreshTokenAgaing);
		assertEquals(USER_KEY, refreshToken02.getAccessToken().getOwner());
		assertFalse(refreshToken02.isExpired());
		
		RefreshToken refreshToken03 = authorizationService.refreshToken(REFRESH_TOKEN);
		REFRESH_TOKEN = refreshToken03.getRefreshToken();
		assertEquals(USER_KEY, refreshToken03.getAccessToken().getOwner());
		
		authorizationService.revokeToken(TokenTypeHint.REFRESH_TOKEN, REFRESH_TOKEN);
	}	
}
