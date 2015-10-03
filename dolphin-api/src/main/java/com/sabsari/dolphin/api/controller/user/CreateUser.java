package com.sabsari.dolphin.api.controller.user;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sabsari.dolphin.api.annotation.Api;
import com.sabsari.dolphin.api.component.ResultFactory;
import com.sabsari.dolphin.api.model.ApiResult;
import com.sabsari.dolphin.api.model.constants.AttributeValues;
import com.sabsari.dolphin.api.model.request.CreateUserRequest;
import com.sabsari.dolphin.api.model.result.CreateUserResult;
import com.sabsari.dolphin.api.version.ApiVersion;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.exception.business.InvalidParameterException;
import com.sabsari.dolphin.core.member.domain.User;
import com.sabsari.dolphin.core.member.service.UserService;

@Controller
public class CreateUser {
	
	private static final Logger logger = LoggerFactory.getLogger(CreateUser.class);
	
	@Autowired
	private UserService userService;
		
	@Autowired
	private ResultFactory resultFactory;
		
	@Api(level=Role.CLIENT)
	@RequestMapping(value={"/" + ApiVersion.v1 + "/user"}, method=RequestMethod.POST)
	@ResponseBody	
	public ApiResult createUser(HttpServletRequest request, @RequestBody @Valid CreateUserRequest createUserRequest, 
			BindingResult paramsResult, Locale locale) throws Exception {    	
		logger.debug("createUser!");
		
		// params check
		List<ObjectError> paramErrors = paramsResult.getAllErrors();
		if (paramErrors.size() != 0) {
			String message = paramErrors.get(0).getDefaultMessage();
			logger.debug("Invalid parameter: " + message);
			throw new InvalidParameterException(message);
		}
						
		String clientId = (String)request.getAttribute(AttributeValues.CLIENT_ID);
		
		User user = userService.createUser(clientId, createUserRequest.getEmailId(), createUserRequest.getFirstName(),
					createUserRequest.getLastName(), createUserRequest.getPassword(), createUserRequest.getPhone(),
					createUserRequest.getBirthday(), createUserRequest.getGender());
		
		CreateUserResult result = new CreateUserResult(user);
		
		// send confirm email
//		mailSendService.sendVerificationEmail(userKey);
		
		return resultFactory.getSuccess(result, locale);
	}
}
