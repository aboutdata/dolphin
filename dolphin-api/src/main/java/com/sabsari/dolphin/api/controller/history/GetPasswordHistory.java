package com.sabsari.dolphin.api.controller.history;

import java.util.List;
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
import com.sabsari.dolphin.api.history.constants.HistoryConstants;
import com.sabsari.dolphin.api.history.constants.PasswordHistoryType;
import com.sabsari.dolphin.api.model.ApiResult;
import com.sabsari.dolphin.api.model.constants.PathVariables;
import com.sabsari.dolphin.api.model.result.PasswordHistoryResult;
import com.sabsari.dolphin.api.version.ApiVersion;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.exception.business.InvalidParameterException;
import com.sabsari.dolphin.core.history.domain.AuthenticationHistory;
import com.sabsari.dolphin.core.history.service.HistoryService;

@Controller
@RequestMapping(value={"/" + ApiVersion.v1 + "/history/" + PathVariables.USER_KEY + "/password/" + PathVariables.PASSWORD_HISTORY_TYPE + "/*"})
public class GetPasswordHistory {

	private static final Logger logger = LoggerFactory.getLogger(GetPasswordHistory.class);
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private ResultFactory resultFactory;
	
	@Api(level=Role.USER)
	@RequestMapping(value={"latest"}, method=RequestMethod.GET)
	@ResponseBody	
	public ApiResult getPasswordHistoryLatest(@PathVariable String userKey,
			@PathVariable String passwordHistoryType, Locale locale) throws Exception {
    	logger.debug("getPasswordHistoryLatest!");
    	
    	if (!PasswordHistoryType.isValidType(passwordHistoryType)) {
    		logger.debug("Invalid parameter: wrong type");
			throw new InvalidParameterException("wrong type");
    	}
    	
    	PasswordHistoryType type = PasswordHistoryType.search(passwordHistoryType);
    	List<AuthenticationHistory> list = historyService.getAuthenticationHistory(userKey, type.getReason(),
    			HistoryConstants.LATEST_PAGE_NUMBER, HistoryConstants.LATEST_PAGE_SIZE);
    	
    	PasswordHistoryResult result = new PasswordHistoryResult(list);
		return resultFactory.getSuccess(result, locale);
	}
	
	@Api(level=Role.USER)
	@RequestMapping(value={PathVariables.PAGE_NUMBER + "/" + PathVariables.PAGE_SIZE}, method=RequestMethod.GET)
	@ResponseBody	
	public ApiResult getPasswordHistory(@PathVariable String userKey, @PathVariable String passwordHistoryType,
			@PathVariable int pageNumber, @PathVariable int pageSize, Locale locale) throws Exception {
    	logger.debug("getPasswordHistory!");
    	
    	if (!PasswordHistoryType.isValidType(passwordHistoryType)) {
    		logger.debug("Invalid parameter: wrong type");
			throw new InvalidParameterException("wrong type");
    	}
    	
		if (pageNumber <= 0 || pageSize <= 0 || pageSize > HistoryConstants.MAX_PAGE_SIZE) {
			logger.debug("Invalid parameter: wrong page request");
			throw new InvalidParameterException("wrong page request");
		}
		
		PasswordHistoryType type = PasswordHistoryType.search(passwordHistoryType);
    	List<AuthenticationHistory> list = historyService.getAuthenticationHistory(userKey, type.getReason(),
				pageNumber, pageSize);
    	
    	PasswordHistoryResult result = new PasswordHistoryResult(list);
		return resultFactory.getSuccess(result, locale);
	}
}
