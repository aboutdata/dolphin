package com.sabsari.dolphin.api.controller.admin;

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
import com.sabsari.dolphin.api.model.request.RegistApplicationRequest;
import com.sabsari.dolphin.api.model.result.CreateApplicationResult;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.exception.business.InvalidParameterException;
import com.sabsari.dolphin.core.member.domain.Application;
import com.sabsari.dolphin.core.member.service.AdminService;

@Controller
public class RegistApplication {
	
	private final static Logger logger = LoggerFactory.getLogger(RegistApplication.class);
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ResultFactory resultFactory;


	@Api(level=Role.ADMIN)
    @RequestMapping(value="/admin/group/" + PathVariables.GROUP_KEY + "/application", method=RequestMethod.POST)
    @ResponseBody    
    public ApiResult registApplication(@PathVariable String groupKey, @RequestBody @Valid RegistApplicationRequest registApplicationRequest,
    		BindingResult paramsResult, Locale locale) throws Exception {
		logger.debug("registApplication!");
		
		// params check
		List<ObjectError> paramErrors = paramsResult.getAllErrors();
		if (paramErrors.size() != 0) {
			String message = paramErrors.get(0).getDefaultMessage();
			logger.debug("Invalid parameter: " + message);
			throw new InvalidParameterException(message);
		}
		
		Application application = adminService.registApplication(groupKey, registApplicationRequest.getAppName());
		CreateApplicationResult result = new CreateApplicationResult(application);
		
		return resultFactory.getSuccess(result, locale);
    }
}
