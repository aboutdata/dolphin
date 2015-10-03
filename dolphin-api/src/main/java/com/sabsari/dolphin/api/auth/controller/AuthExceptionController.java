package com.sabsari.dolphin.api.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sabsari.dolphin.api.annotation.Api;
import com.sabsari.dolphin.api.auth.model.constants.ExceptionConstants;
import com.sabsari.dolphin.core.auth.domain.code.Role;

@Controller
public class AuthExceptionController {

	private static final Logger logger = LoggerFactory.getLogger(AuthExceptionController.class);
		
	@Api(level=Role.ANONYMOUS)
	@RequestMapping(value={ExceptionConstants.AUTH_EXCEPTION_URI_PATH})
	@ResponseBody
	public void authException(HttpServletRequest request, HttpServletResponse response) throws Exception {    	
		logger.debug("authException!");
		
		Object ex = request.getAttribute(ExceptionConstants.EXCEPTION);		
		if (ex == null) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return;
		}
		
		throw (Exception)ex;
	}
}
