package com.sabsari.dolphin.api.controller.password;

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
import com.sabsari.dolphin.api.model.result.ResetPasswordResult;
import com.sabsari.dolphin.api.version.ApiVersion;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.member.service.PasswordService;

@Controller
public class ResetPassword {
	
	private static final Logger logger = LoggerFactory.getLogger(ResetPassword.class);
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private ResultFactory resultFactory;

	@Api(level=Role.USER)
	@RequestMapping(value={"/" + ApiVersion.v1 + "/user/" + PathVariables.USER_KEY + "/password"}, method=RequestMethod.DELETE)
	@ResponseBody
	public ApiResult resetPassword(@PathVariable String userKey, Locale locale) throws Exception {
    	logger.debug("resetPassword!");
    	
    	String tempPassword = passwordService.resetPassword(userKey);
    	ResetPasswordResult result = new ResetPasswordResult(tempPassword);
    	
		return resultFactory.getSuccess(result, locale);	
	}
}
