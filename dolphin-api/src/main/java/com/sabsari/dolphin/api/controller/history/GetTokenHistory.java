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
import com.sabsari.dolphin.api.history.constants.TokenHistoryType;
import com.sabsari.dolphin.api.model.ApiResult;
import com.sabsari.dolphin.api.model.constants.PathVariables;
import com.sabsari.dolphin.api.model.result.TokenHistoryResult;
import com.sabsari.dolphin.api.version.ApiVersion;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.exception.business.InvalidParameterException;
import com.sabsari.dolphin.core.history.domain.TokenHistory;
import com.sabsari.dolphin.core.history.service.HistoryService;

@Controller
@RequestMapping(value={"/" + ApiVersion.v1 + "/history/" + PathVariables.USER_KEY + "/token/" + PathVariables.TOKEN_HISTORY_TYPE + "/*"})
public class GetTokenHistory {

	private static final Logger logger = LoggerFactory.getLogger(GetTokenHistory.class);
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private ResultFactory resultFactory;
		
	@Api(level=Role.USER)
	@RequestMapping(value={"latest"}, method=RequestMethod.GET)
	@ResponseBody	
	public ApiResult getTokenHistoryLatest(@PathVariable String userKey,
			@PathVariable String tokenHistoryType, Locale locale) throws Exception {
    	logger.debug("getTokenHistoryLatest!");
    	
    	if (!TokenHistoryType.isValidType(tokenHistoryType)) {
			logger.debug("Invalid parameter: wrong type");
			throw new InvalidParameterException("wrong type");
		}
    	
    	TokenHistoryType type = TokenHistoryType.search(tokenHistoryType);
    	List<TokenHistory> list = historyService.getTokenHistory(userKey, type.getReason(),
    			HistoryConstants.LATEST_PAGE_NUMBER, HistoryConstants.LATEST_PAGE_SIZE);
    	
    	TokenHistoryResult result = new TokenHistoryResult(list);
		return resultFactory.getSuccess(result, locale);
	}
	
	@Api(level=Role.USER)
	@RequestMapping(value={PathVariables.PAGE_NUMBER + "/" + PathVariables.PAGE_SIZE}, method=RequestMethod.GET)
	@ResponseBody	
	public ApiResult getTokenHistory(@PathVariable String userKey, @PathVariable String tokenHistoryType,
			@PathVariable int pageNumber, @PathVariable int pageSize, Locale locale) throws Exception {
    	logger.debug("getTokenHistory!");
    	
    	if (!TokenHistoryType.isValidType(tokenHistoryType)) {
			logger.debug("Invalid parameter: wrong type");
			throw new InvalidParameterException("wrong type");
		}
    	
		if (pageNumber <= 0 || pageSize <= 0 || pageSize > HistoryConstants.MAX_PAGE_SIZE) {
			logger.debug("Invalid parameter: wrong page request");
			throw new InvalidParameterException("wrong page request");
		}
    	
		TokenHistoryType type = TokenHistoryType.search(tokenHistoryType);
		List<TokenHistory> list = historyService.getTokenHistory(userKey, type.getReason(),
    			pageNumber, pageSize);
    	
    	TokenHistoryResult result = new TokenHistoryResult(list);
		return resultFactory.getSuccess(result, locale);
	}
}
