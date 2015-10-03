package com.sabsari.dolphin.api.controller.email;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sabsari.dolphin.api.annotation.Api;
import com.sabsari.dolphin.api.component.ResultFactory;
import com.sabsari.dolphin.api.model.ApiResult;
import com.sabsari.dolphin.api.model.constants.PathVariables;
import com.sabsari.dolphin.api.model.request.ModifyEmailRequest;
import com.sabsari.dolphin.api.version.ApiVersion;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.member.service.UserService;

@Controller
public class ModifyEmail {

	private static final Logger logger = LoggerFactory.getLogger(ModifyEmail.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ResultFactory resultFactory;
	
	@Api(level=Role.USER)
	@RequestMapping(value={"/" + ApiVersion.v1 + "/user/" + PathVariables.USER_KEY + "/email"}, method=RequestMethod.PUT)
	@ResponseBody	
	public ApiResult modifyEmail(@PathVariable String userKey, @RequestBody @Valid ModifyEmailRequest modifyEmailRequest, 
			BindingResult paramsResult, Locale locale) throws Exception {    	
		logger.debug("modifyEmail!");
		
		// params check
		List<ObjectError> paramErrors = paramsResult.getAllErrors();
		if (paramErrors.size() != 0) {
			String message = paramErrors.get(0).getDefaultMessage();
			logger.debug("Invalid parameter: " + message);
			throw new com.sabsari.dolphin.core.exception.business.InvalidParameterException(message);
		}
		
		userService.updateEmailId(userKey, modifyEmailRequest.getNewEmailId());
		
		// send confirm email, 2014-07-23 현재 불필요
//		mailSendService.sendVerificationEmail(userKey);
    	
		return resultFactory.getSuccess(locale);
	}
}
