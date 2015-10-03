package com.sabsari.dolphin.api.auth.component;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.sabsari.dolphin.api.auth.model.ApiInfo;
import com.sabsari.dolphin.api.model.constants.AttributeValues;
import com.sabsari.dolphin.core.auth.domain.code.GrantType;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.auth.exception.UnauthorizedClientException;
import com.sabsari.dolphin.core.auth.service.AuthorizationService;
import com.sabsari.dolphin.core.web.util.WebUtils;

@Component
public class AuthorizationHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthorizationHandler.class);
	
	@Autowired
	AuthorizationService authorizationService;
	
	private AntPathMatcher matcher = new AntPathMatcher();
	
	public void checkAuthorized(HttpServletRequest request, ApiInfo apiInfo) throws Exception {
		Role requiredLevel = apiInfo.getLevel();		
		Role grantedLevel = (Role)request.getAttribute(AttributeValues.ROLE);		
		if (!grantedLevel.hasPermission(requiredLevel)) {
			logger.debug("checkAuthorized : no permission granted:{}, but required:{}",
					grantedLevel.getRole(), requiredLevel.getRole());
			throw new UnauthorizedClientException();
		}
		
		if (Role.ADMIN == grantedLevel || !apiInfo.hasUserKeyInPath())
			return;
		
		GrantType grantType = (GrantType)request.getAttribute(AttributeValues.GRANT_TYPE);
		String requestUri = WebUtils.getRequestUri(request);
        String extractedUserKey = matcher.extractPathWithinPattern(apiInfo.getRequestMappingPattern(), requestUri);
		
		if (GrantType.CLIENT_CREDENTIALS == grantType) {
			String clientId = (String)request.getAttribute(AttributeValues.CLIENT_ID);
			boolean hasPermission = authorizationService.hasPermission(extractedUserKey, clientId);
			if (!hasPermission) {
				logger.debug("checkAuthorized : no permission info clientId:{}, userKey:{}",
						clientId, extractedUserKey);
	        	throw new UnauthorizedClientException();
			}			
		}
		else if (GrantType.PASSWORD == grantType) {
			String userKey = (String)request.getAttribute(AttributeValues.USER_KEY);	        	        
	        if (!userKey.equals(extractedUserKey)) {
	        	logger.debug("checkAuthorized : unauthorized Owned:{}, but requested:{}",
	        			userKey, extractedUserKey);
	        	throw new UnauthorizedClientException();
	        }	
		}			
	}	
}
