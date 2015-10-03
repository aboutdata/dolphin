package com.sabsari.dolphin.api.auth.component;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sabsari.dolphin.api.auth.component.HttpAuthentication;
import com.sabsari.dolphin.api.config.WebMvcConfig;
import com.sabsari.dolphin.core.auth.domain.AccessToken;
import com.sabsari.dolphin.core.auth.domain.code.HttpAuthenticationScheme;
import com.sabsari.dolphin.core.auth.domain.code.TokenTypeHint;
import com.sabsari.dolphin.core.auth.service.AuthorizationService;
import com.sabsari.dolphin.core.common.util.SimpleCrypto;
import com.sabsari.dolphin.core.config.ContextConfig;
import com.sabsari.dolphin.core.history.service.HistoryService;
import com.sabsari.dolphin.core.member.domain.Application;
import com.sabsari.dolphin.core.member.domain.User;
import com.sabsari.dolphin.core.member.service.AdminService;
import com.sabsari.dolphin.core.member.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class,WebMvcConfig.class})
@WebAppConfiguration
@ActiveProfiles(value="dev")
public class HttpAuthenticationTest {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private HttpAuthentication httpAuthentication;
	
	@Autowired
	private HistoryService historyService;
		
	private static String GROUP_KEY 	= null;	
	private static String CLIENT_ID 	= null;
	private static String CLIENT_SECRET = null;	
	private static String USER_KEY		= null;	
	private static String ACCESS_TOKEN	= null;
	private static String PASSWORD		= "aaaa1111";	
	private static String EMAIL_ID		= "testtest@gg.com";
	
	private final String AUTH_HEADER = "Authorization";
	
	@Before
	public void _pre() {
		GROUP_KEY = adminService.createUserGroup("test_user_group");
		Application application = adminService.registApplication(GROUP_KEY, "test_app_name");
		CLIENT_ID = application.getClientId();
		CLIENT_SECRET = application.getClientSecret();
		User user = userService.createUser(CLIENT_ID, EMAIL_ID, null, null, PASSWORD, null, null, null);
		USER_KEY = user.getUserKey();		
		
		AccessToken accessToken = authorizationService.issueAccessToken(CLIENT_ID);
		ACCESS_TOKEN = accessToken.getAccessToken();
	}
	
	@After
	public void _post() {
		userService.deleteUser(USER_KEY);
		adminService.unregistApplication(CLIENT_ID);
		adminService.deleteUserGroup(GROUP_KEY);
		authorizationService.revokeToken(TokenTypeHint.ACCESS_TOKEN, ACCESS_TOKEN);
		historyService.clearHistory();
	}
		
	@Test
	public void _1_인증Basic테스트() {
		String target = SimpleCrypto.base64Encode(CLIENT_ID + ":" + CLIENT_SECRET);		
		MockHttpServletRequest request = new MockHttpServletRequest();		
		request.addHeader(AUTH_HEADER, HttpAuthenticationScheme.BASIC.scheme() + " " + target);
		
		try {
			httpAuthentication.authenticateBasic(request);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void _2_인증Bearer테스트() {
		String target = SimpleCrypto.base64Encode(ACCESS_TOKEN);
		MockHttpServletRequest request = new MockHttpServletRequest();		
		request.addHeader(AUTH_HEADER, HttpAuthenticationScheme.BEARER.scheme() + " " + target);
		
		try {
			httpAuthentication.authenticateBearer(request);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
