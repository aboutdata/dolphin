package com.sabsari.dolphin.api.controller.email;

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
import com.sabsari.dolphin.api.model.result.CheckEmailDuplicationResult;
import com.sabsari.dolphin.api.version.ApiVersion;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.exception.business.InvalidParameterException;
import com.sabsari.dolphin.core.member.service.UserService;
import com.sabsari.dolphin.core.validator.FormatValidator;

@Controller
public class CheckEmailDuplication {

	private static final Logger logger = LoggerFactory.getLogger(CheckEmailDuplication.class);
	
	@Autowired
	private UserService userService;
		
	@Autowired
	private ResultFactory resultFactory;
	
	@Api(level=Role.CLIENT)
	@RequestMapping(value={"/" + ApiVersion.v1 + "/email/" + PathVariables.EMAIL}, method=RequestMethod.GET)
	@ResponseBody	
	public ApiResult checkEmailDuplication(HttpServletRequest request, @PathVariable String email, 
			Locale locale) throws Exception {
    	logger.debug("checkEmailDuplication! : " + email);
    	
    	// params check
    	if (!FormatValidator.isEmail(email)) {
    		logger.debug("Invalid email: " + email);
			throw new InvalidParameterException("Invalid email");
    	}
 	
    	String clientId = (String)request.getAttribute(AttributeValues.CLIENT_ID);    	
    	boolean isAvailable = userService.isEmailIdAvailable(clientId, email);
    	CheckEmailDuplicationResult result = new CheckEmailDuplicationResult(isAvailable);
    	
		return resultFactory.getSuccess(result, locale);
	}	
}
