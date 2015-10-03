package com.sabsari.dolphin.api.controller.user;

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
import com.sabsari.dolphin.api.model.request.ModifyUserRequest;
import com.sabsari.dolphin.api.model.result.ModifyUserResult;
import com.sabsari.dolphin.api.version.ApiVersion;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.exception.business.InvalidParameterException;
import com.sabsari.dolphin.core.member.domain.User;
import com.sabsari.dolphin.core.member.service.UserService;

@Controller
public class ModifyUser {
	
	private static final Logger logger = LoggerFactory.getLogger(ModifyUser.class);
	
	@Autowired
	private UserService userService;
		
	@Autowired
	private ResultFactory resultFactory;

	@Api(level=Role.USER)
	@RequestMapping(value={"/" + ApiVersion.v1 + "/user/" + PathVariables.USER_KEY}, method=RequestMethod.PUT)
	@ResponseBody
	public ApiResult modifyUser(@PathVariable String userKey, @RequestBody @Valid ModifyUserRequest modifyUserRequest, 
			BindingResult paramsResult, Locale locale) throws Exception {
    	logger.debug("modifyUser!");
    	
    	List<ObjectError> paramErrors = paramsResult.getAllErrors();
		if (paramErrors.size() != 0) {
			String message = paramErrors.get(0).getDefaultMessage();
			logger.debug("Invalid parameter: " + message);
			throw new InvalidParameterException(message);
		}
		
		User user = userService.updateUser(userKey, modifyUserRequest.getFirstName(), modifyUserRequest.getLastName(),
				   modifyUserRequest.getBirthday(), modifyUserRequest.getPhone(), modifyUserRequest.getGender());
    			
		ModifyUserResult result = new ModifyUserResult(user);
    	
		return resultFactory.getSuccess(result, locale);
	}
}
