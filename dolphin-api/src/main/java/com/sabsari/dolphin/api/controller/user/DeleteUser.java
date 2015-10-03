package com.sabsari.dolphin.api.controller.user;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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
import com.sabsari.dolphin.api.model.constants.AttributeValues;
import com.sabsari.dolphin.api.model.constants.PathVariables;
import com.sabsari.dolphin.api.version.ApiVersion;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.member.service.UserService;

@Controller
public class DeleteUser {
	
	private static final Logger logger = LoggerFactory.getLogger(DeleteUser.class);
	
	@Autowired
	private UserService userService;
		
	@Autowired
	private ResultFactory resultFactory;
	
	@Api(level=Role.USER)
	@RequestMapping(value={"/" + ApiVersion.v1 + "/user/" + PathVariables.USER_KEY}, method=RequestMethod.DELETE)
	@ResponseBody	
	public ApiResult deleteUser(@PathVariable String userKey, Locale locale) throws Exception {
    	logger.debug("deleteUser!");
    	
    	userService.deleteUser(userKey);
    	
		return resultFactory.getSuccess(locale);
	}
	
	@Api(level=Role.CLIENT)
	@RequestMapping(value={"/" + ApiVersion.v1_1 + "/user/" + PathVariables.USER_KEY}, method=RequestMethod.DELETE)
	@ResponseBody	
	public ApiResult deleteUser_v1_1(HttpServletRequest request, 
			@PathVariable String userKey, Locale locale) throws Exception {
    	logger.debug("deleteUser_v1_1!");
    	
    	String clientId = (String)request.getAttribute(AttributeValues.CLIENT_ID);
    	userService.deleteUser_v1_1(userKey, clientId);
    	
		return resultFactory.getSuccess(locale);
	}	
}
