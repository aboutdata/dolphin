package com.sabsari.dolphin.api.auth.controller.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sabsari.dolphin.api.auth.model.result.ErrorResult;
import com.sabsari.dolphin.core.auth.domain.code.HttpAuthenticationScheme;
import com.sabsari.dolphin.core.auth.error.ErrorMessage;
import com.sabsari.dolphin.core.auth.exception.AuthException;
import com.sabsari.dolphin.core.auth.exception.status401.Status401Excepiton;

@ControllerAdvice(basePackages={"com.sabsari.dolphin.api.auth.controller"})
public class TokenControllerExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenControllerExceptionHandler.class);

	@ExceptionHandler(Status401Excepiton.class)
	@ResponseBody
	public ErrorResult status401ExcepitonHandler(HttpServletRequest request,
			HttpServletResponse response, Status401Excepiton ex) {
		logger.debug("Status401Excepiton at TokenController!");
		
        response.setHeader(HttpAuthenticationScheme.WWWAuthenticate_HEADER, ex.getWWWAuthenticateHeader());
		response.setStatus(ex.getStatus());
		ErrorResult errorResult = new ErrorResult(ex.getErrorMessage());
		
		return errorResult;
	}
	
	@ExceptionHandler(AuthException.class)
	@ResponseBody
	public ErrorResult authExceptionHandler(HttpServletRequest request,
			HttpServletResponse response, AuthException ex) {
		logger.debug("AuthException at TokenController!");
		
		response.setStatus(ex.getStatus());
		ErrorResult errorResult = new ErrorResult(ex.getErrorMessage());
		
		return errorResult;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ErrorResult exceptionHandler(HttpServletRequest request,
			HttpServletResponse response, Exception ex) {
		logger.debug("Exception at TokenController! : " + ex);		
		response.setStatus(ErrorMessage.UNEXPECTED.status());
		ErrorResult errorResult = new ErrorResult(ErrorMessage.UNEXPECTED.message());
		
		return errorResult;
	}
}
