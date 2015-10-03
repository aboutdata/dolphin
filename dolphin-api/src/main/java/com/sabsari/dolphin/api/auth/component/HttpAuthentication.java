package com.sabsari.dolphin.api.auth.component;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sabsari.dolphin.api.model.constants.AttributeValues;
import com.sabsari.dolphin.core.auth.domain.AccessToken;
import com.sabsari.dolphin.core.auth.domain.code.GrantType;
import com.sabsari.dolphin.core.auth.domain.code.HttpAuthenticationScheme;
import com.sabsari.dolphin.core.auth.exception.InvalidAuthenticationInfoException;
import com.sabsari.dolphin.core.auth.exception.InvalidAuthenticationSchemeException;
import com.sabsari.dolphin.core.auth.exception.NoAuthenticationInfoException;
import com.sabsari.dolphin.core.auth.exception.UnexpectedErrorException;
import com.sabsari.dolphin.core.auth.service.AuthorizationService;
import com.sabsari.dolphin.core.member.domain.Application;

@Component
public class HttpAuthentication {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpAuthentication.class);
	
	@Autowired
	private AuthorizationService authorizationService;
	
	/**
	 * clienstId와 clientSecret 인증정보 검증
	 * accessToken을 요청할 때 사용
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void authenticateBasic(HttpServletRequest request) throws Exception {
		String[] authHeaderValue = parseAuthorizationHeader(request);
		
		if (!HttpAuthenticationScheme.BASIC.scheme().equals(authHeaderValue[0])) {
			logger.debug("invalid scheme : not Basic scheme!({})", authHeaderValue[0]);
			throw new InvalidAuthenticationSchemeException();
		}
		
		String[] userPass = null;
		String clientId = null;
		String clientSecret = null;
		try {
			userPass = authHeaderValue[1].split(HttpAuthenticationScheme.USER_PASSWORD_DELIMITER);
			clientId = userPass[0];
			clientSecret = userPass[1];
		}
		catch (Exception ex) {
			logger.debug("The requested basic info is invalid.");
			throw new InvalidAuthenticationInfoException();
		}		
		
		Application application = authorizationService.verifyClientSecret(clientId, clientSecret);		
		request.setAttribute(AttributeValues.CLIENT_ID, application.getClientId());
	}
	
	/**
	 * Api 호출을 위해 accessToken 정보 판별을 위해서 사용
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void authenticateBearer(HttpServletRequest request) throws Exception {
		String[] authHeaderValue = parseAuthorizationHeader(request);
		String scheme;
		String token;
		
		try {
			scheme = authHeaderValue[0];
			token = authHeaderValue[1];
		}
		catch (Exception ex) {
			logger.debug("The requested basic info is invalid.");
			throw new InvalidAuthenticationInfoException();
		}		
		
		if (!HttpAuthenticationScheme.BEARER.scheme().equals(scheme)) {
			logger.debug("Invalid scheme : not Bearer scheme!({})", scheme);
			throw new InvalidAuthenticationSchemeException();
		}
		
		AccessToken accessToken = authorizationService.verifyAccessToken(token);
		GrantType grantType = accessToken.getGrantType();
		if (GrantType.CLIENT_CREDENTIALS == grantType) {
			request.setAttribute(AttributeValues.CLIENT_ID, accessToken.getOwner());
		}
		else if (GrantType.PASSWORD == grantType) {
			request.setAttribute(AttributeValues.USER_KEY, accessToken.getOwner());
		}
		else {
			logger.debug("Invalid grant type from database. check the data.");
			throw new UnexpectedErrorException();
		}
		
		request.setAttribute(AttributeValues.GRANT_TYPE, grantType);
		request.setAttribute(AttributeValues.ROLE, accessToken.getRole());
	}
	
	private String[] parseAuthorizationHeader(HttpServletRequest request) throws Exception {
		String header = request.getHeader(HttpAuthenticationScheme.Authorization_HEADER);
		
		String[] values = HttpAuthenticationScheme.parseHeader(header);
		
		if (values == null) {
			logger.debug("Failed to parse authentication scheme!");
			throw new NoAuthenticationInfoException();
		}		
		
		return values;
	}
}
