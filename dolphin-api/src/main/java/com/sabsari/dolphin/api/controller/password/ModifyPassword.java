package com.sabsari.dolphin.api.controller.password;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
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
import com.sabsari.dolphin.api.model.request.ModifyPasswordRequest;
import com.sabsari.dolphin.api.version.ApiVersion;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.exception.business.InvalidParameterException;
import com.sabsari.dolphin.core.member.service.PasswordService;

@Controller
public class ModifyPassword {
	
	private static final Logger logger = LoggerFactory.getLogger(ModifyPassword.class);
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private ResultFactory resultFactory;
	
	@Api(level=Role.USER)
	@RequestMapping(value={"/" + ApiVersion.v1 + "/user/" + PathVariables.USER_KEY + "/password"}, method=RequestMethod.POST)
	@ResponseBody
	public ApiResult modifyPassword(HttpServletRequest request, @PathVariable String userKey,
			@RequestBody @Valid ModifyPasswordRequest modifyPasswordRequest,
			BindingResult result, Locale locale) throws Exception {
    	logger.debug("modifyPassword!");
    	
    	List<ObjectError> paramErrors = result.getAllErrors();
		if (paramErrors.size() != 0) {
			String message = paramErrors.get(0).getDefaultMessage();
			logger.debug("Invalid parameter: " + message);
			throw new InvalidParameterException(message);
		}
		
		passwordService.updatePassword(userKey, modifyPasswordRequest.getExistingPassword(),
				modifyPasswordRequest.getNewPassword());
    	    	
		return resultFactory.getSuccess(locale);
	}
}
