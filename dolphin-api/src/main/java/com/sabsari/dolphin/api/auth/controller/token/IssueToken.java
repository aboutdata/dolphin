package com.sabsari.dolphin.api.auth.controller.token;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sabsari.dolphin.api.annotation.Api;
import com.sabsari.dolphin.api.auth.model.constants.PropertyValues;
import com.sabsari.dolphin.api.auth.model.result.AbstractTokenResult;
import com.sabsari.dolphin.api.auth.model.result.AccessTokenResult;
import com.sabsari.dolphin.api.auth.model.result.RefreshTokenResult;
import com.sabsari.dolphin.api.model.constants.AttributeValues;
import com.sabsari.dolphin.core.auth.domain.AccessToken;
import com.sabsari.dolphin.core.auth.domain.RefreshToken;
import com.sabsari.dolphin.core.auth.domain.code.GrantType;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.auth.exception.InvalidGrantTypeException;
import com.sabsari.dolphin.core.auth.exception.InvalidRequestException;
import com.sabsari.dolphin.core.auth.service.AuthorizationService;

@Controller
public class IssueToken {

	private static final Logger logger = LoggerFactory.getLogger(IssueToken.class);
	
	@Autowired
	private AuthorizationService  authorizationService;
	
	@Api(level=Role.TOKEN_CONSUMER)
	@RequestMapping(value={"/auth/token"}, method=RequestMethod.POST)
	@ResponseBody	
	public AbstractTokenResult issueToken(HttpServletRequest request, 
			@RequestParam Map<String, String> params) throws Exception {    	
		logger.debug("issueToken!");
		
		GrantType grantType = GrantType.search(params.get(PropertyValues.GRANT_TYPE));
		
		if (grantType == null) {
			logger.debug("invalid request: no grantType.");
			throw new InvalidRequestException();
		}
		
		AbstractTokenResult result = null;
		
		if (GrantType.CLIENT_CREDENTIALS == grantType) {
			result = issueTokenByclientCredentials(request, params);
		}
		else if (GrantType.PASSWORD == grantType) {
			result = issueTokenByPassword(request, params);
		}
		else if (GrantType.REFRESH_TOKEN == grantType) {
			result = issueTokenByRefreshToken(request, params);
		}
		else {
			logger.debug("Invalid grant type");
			throw new InvalidGrantTypeException();
		}
				    	
    	return result;
	}
	
	private AccessTokenResult issueTokenByclientCredentials (HttpServletRequest request,
			Map<String, String> params) throws Exception {
		
		String clientId = (String)request.getAttribute(AttributeValues.CLIENT_ID);
		
		AccessToken accessToken = authorizationService.issueAccessToken(clientId);		
		AccessTokenResult accessTokenResult = new AccessTokenResult(accessToken.getAccessToken(), accessToken.getRestLifeTime());
		
		return accessTokenResult;
	}
	
	private RefreshTokenResult issueTokenByPassword (HttpServletRequest request,
			Map<String, String> params) throws Exception {
		
		String clientId = (String)request.getAttribute(AttributeValues.CLIENT_ID);
		String username = params.get(PropertyValues.USERNAME);
		String password = params.get(PropertyValues.PASSWORD);
		
		if (username == null || password == null) {
			logger.debug("invalid request: no username or password.");
			throw new InvalidRequestException();
		}

		String userKey = authorizationService.verifyUserPassword(clientId, username, password);

		RefreshToken refreshToken = authorizationService.issueRefreshToken(userKey);				
		RefreshTokenResult refreshTokenResult = new RefreshTokenResult(refreshToken.getAccessTokenValue(),
				refreshToken.getAccessTokenRestLifeTime(), refreshToken.getRefreshToken());
		
		return refreshTokenResult;
	}
	
	private RefreshTokenResult issueTokenByRefreshToken (HttpServletRequest request, 
			Map<String, String> params) throws Exception {
		
		String refreshToken = params.get(PropertyValues.REFRESH_TOKEN);
		
		if (refreshToken == null) {
			logger.debug("invalid request: no refreshToken.");
			throw new InvalidRequestException();
		}

		RefreshToken newRefreshToken = authorizationService.refreshToken(refreshToken);				
		RefreshTokenResult refreshTokenResult = new RefreshTokenResult(newRefreshToken.getAccessTokenValue(),
				newRefreshToken.getAccessTokenRestLifeTime(), newRefreshToken.getRefreshToken());
		
		return refreshTokenResult;
	}
}
