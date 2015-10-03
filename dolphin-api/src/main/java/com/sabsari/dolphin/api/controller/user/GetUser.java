package com.sabsari.dolphin.api.controller.user;

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
import com.sabsari.dolphin.api.model.result.GetUserResult;
import com.sabsari.dolphin.api.version.ApiVersion;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.member.domain.User;
import com.sabsari.dolphin.core.member.service.UserService;

@Controller
public class GetUser {
	
	private static final Logger logger = LoggerFactory.getLogger(GetUser.class);

	@Autowired
	private UserService userService;
		
	@Autowired
	private ResultFactory resultFactory;

	@Api(level=Role.USER)
	@RequestMapping(value={"/" + ApiVersion.v1 + "/user/" + PathVariables.USER_KEY}, method=RequestMethod.GET)
	@ResponseBody
	public ApiResult getUser(@PathVariable String userKey, Locale locale) throws Exception {
    	logger.debug("getUser!");
    	
    	User user = userService.getUser(userKey);
		
		GetUserResult result = new GetUserResult(user);
		
		return resultFactory.getSuccess(result, locale);
	}
}
