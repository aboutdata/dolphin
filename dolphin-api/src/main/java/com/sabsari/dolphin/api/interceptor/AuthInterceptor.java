package com.sabsari.dolphin.api.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sabsari.dolphin.api.auth.component.ApiInfoMapper;
import com.sabsari.dolphin.api.auth.component.AuthorizationHandler;
import com.sabsari.dolphin.api.auth.component.HttpAuthentication;
import com.sabsari.dolphin.api.auth.model.ApiInfo;
import com.sabsari.dolphin.api.auth.model.constants.ExceptionConstants;
import com.sabsari.dolphin.core.auth.domain.code.Role;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Autowired
	private HttpAuthentication httpAuthentication;
	
	@Autowired
	private AuthorizationHandler authorizationHandler;
	
	@Autowired
	private ApiInfoMapper apiInfoMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		
		try {
			ApiInfo apiInfo = apiInfoMapper.getApiInfo((HandlerMethod)handler);

			// allowed any one
			if (Role.ANONYMOUS == apiInfo.getLevel()) {
				return true;
			}
						
			// token
			if (Role.TOKEN_CONSUMER == apiInfo.getLevel()) {
				httpAuthentication.authenticateBasic(request);		// http basic authentication for token
				return true;
			}
			
			// api authentication
			httpAuthentication.authenticateBearer(request);			// http bearer authentication for api
			
			authorizationHandler.checkAuthorized(request, apiInfo);
		}
		catch (Exception ex) {
			logger.debug("Exception at preHandle! : " + ex.getCause());
			request.setAttribute(ExceptionConstants.EXCEPTION, ex);
			RequestDispatcher rd = request.getRequestDispatcher(ExceptionConstants.AUTH_EXCEPTION_URI_PATH);
			rd.forward(request, response);			
			return false;
		}
		
		return true;
	}
}