package com.sabsari.dolphin.api.controller.etc;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sabsari.dolphin.api.annotation.Api;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.exception.business.NotFoundApiException;
import com.sabsari.dolphin.core.web.util.WebUtils;

@Controller
public class None {

private static final Logger logger = LoggerFactory.getLogger(None.class);
	
	@Api(level=Role.ANONYMOUS)
	@RequestMapping(value={"/**"})
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ResponseBody	
	public String none(HttpServletRequest request) throws Exception {
		String remoteIp = WebUtils.getRemoteIp(request);		
		logger.debug("none! remoteIp: " + remoteIp);
		
		throw new NotFoundApiException();
	}
}
