package com.sabsari.dolphin.api.controller.email;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sabsari.dolphin.api.annotation.Api;
import com.sabsari.dolphin.api.component.ResultFactory;
import com.sabsari.dolphin.api.model.ApiResult;
import com.sabsari.dolphin.api.model.constants.PathVariables;
import com.sabsari.dolphin.api.version.ApiVersion;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.member.service.UserService;

@Controller
public class VerifyEmail {

	private static final Logger logger = LoggerFactory.getLogger(VerifyEmail.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ResultFactory resultFactory;
	
	@Api(level=Role.CLIENT)
	@RequestMapping(value={"/" + ApiVersion.v1 + "/user/" + PathVariables.USER_KEY + "/verify/email"}, method=RequestMethod.PUT)
	@ResponseBody
	public ApiResult verifyEmail(@PathVariable String userKey, Locale locale) throws Exception {
    	logger.debug("verifyEmail!");
    	
    	userService.verifyEmail(userKey);
    	
    	return resultFactory.getSuccess(locale);
	}
}
