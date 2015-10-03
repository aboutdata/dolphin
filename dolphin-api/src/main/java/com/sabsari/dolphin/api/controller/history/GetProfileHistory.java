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
import com.sabsari.dolphin.api.history.constants.ProfileHistoryType;
import com.sabsari.dolphin.api.model.ApiResult;
import com.sabsari.dolphin.api.model.constants.PathVariables;
import com.sabsari.dolphin.api.model.result.ProfileHistoryResult;
import com.sabsari.dolphin.api.version.ApiVersion;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.exception.business.InvalidParameterException;
import com.sabsari.dolphin.core.history.domain.UserHistory;
import com.sabsari.dolphin.core.history.service.HistoryService;

@Controller
@RequestMapping(value={"/" + ApiVersion.v1 + "/history/" + PathVariables.USER_KEY + "/profile/" + PathVariables.PROFILE_HISTORY_TYPE + "/*"})
public class GetProfileHistory {

	private static final Logger logger = LoggerFactory.getLogger(GetProfileHistory.class);
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private ResultFactory resultFactory;
	
	@Api(level=Role.USER)
	@RequestMapping(value={"latest"}, method=RequestMethod.GET)
	@ResponseBody	
	public ApiResult getProfileHistoryLatest(@PathVariable String userKey,
			@PathVariable String profileHistoryType, Locale locale) throws Exception {
    	logger.debug("getProfileHistoryLatest!");
    	    	
    	if (!ProfileHistoryType.isValidType(profileHistoryType)) {    		
    		logger.debug("Invalid parameter: wrong type");
			throw new InvalidParameterException("wrong type");
		}
    	
    	ProfileHistoryType type = ProfileHistoryType.search(profileHistoryType);    	
    	List<UserHistory> list = historyService.getUserHistory(userKey, type.getReason(),
				HistoryConstants.LATEST_PAGE_NUMBER, HistoryConstants.LATEST_PAGE_SIZE);
    	
    	ProfileHistoryResult result = new ProfileHistoryResult(list);
		return resultFactory.getSuccess(result, locale);
	}
	
	@Api(level=Role.USER)
	@RequestMapping(value={PathVariables.PAGE_NUMBER + "/" + PathVariables.PAGE_SIZE}, method=RequestMethod.GET)
	@ResponseBody	
	public ApiResult getProfileHistory(@PathVariable String userKey, @PathVariable String profileHistoryType,
			@PathVariable int pageNumber, @PathVariable int pageSize, Locale locale) throws Exception {
    	logger.debug("getProfileHistory!");
    	
    	if (!ProfileHistoryType.isValidType(profileHistoryType)) {    		
    		logger.debug("Invalid parameter: wrong type");
			throw new InvalidParameterException("wrong type");
		}
    	
		if (pageNumber <= 0 || pageSize <= 0 || pageSize > HistoryConstants.MAX_PAGE_SIZE) {
			logger.debug("Invalid parameter: wrong page request");
			throw new InvalidParameterException("wrong page request");
		}
		
		ProfileHistoryType type = ProfileHistoryType.search(profileHistoryType);
		List<UserHistory> list = historyService.getUserHistory(userKey, type.getReason(),
				pageNumber, pageSize);
    	    	
    	ProfileHistoryResult result = new ProfileHistoryResult(list);
		return resultFactory.getSuccess(result, locale);
	}
}
