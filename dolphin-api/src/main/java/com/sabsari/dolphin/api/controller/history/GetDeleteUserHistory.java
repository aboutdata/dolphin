package com.sabsari.dolphin.api.controller.history;

import java.util.List;
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
import com.sabsari.dolphin.api.history.constants.HistoryConstants;
import com.sabsari.dolphin.api.model.ApiResult;
import com.sabsari.dolphin.api.model.constants.AttributeValues;
import com.sabsari.dolphin.api.model.constants.PathVariables;
import com.sabsari.dolphin.api.model.result.DeleteUserHistoryResult;
import com.sabsari.dolphin.api.version.ApiVersion;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.exception.business.InvalidParameterException;
import com.sabsari.dolphin.core.history.domain.DeleteUserHistory;
import com.sabsari.dolphin.core.history.service.HistoryService;

@Controller
@RequestMapping(value={"/" + ApiVersion.v1 + "/history/delete/user/*"})
public class GetDeleteUserHistory {

	private static final Logger logger = LoggerFactory.getLogger(GetDeleteUserHistory.class);
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private ResultFactory resultFactory;
		
	@Api(level=Role.CLIENT)
	@RequestMapping(value={"latest"}, method=RequestMethod.GET)
	@ResponseBody	
	public ApiResult getDeleteUserHistoryLatest(HttpServletRequest request, Locale locale) throws Exception {
    	logger.debug("deleteUserHistoryLatest!");
    	
    	String clientId = (String)request.getAttribute(AttributeValues.CLIENT_ID);    	
    	List<DeleteUserHistory> list = historyService.getDeleteUserHistory(clientId, 
    			HistoryConstants.LATEST_PAGE_NUMBER, HistoryConstants.LATEST_PAGE_SIZE);
    	
    	DeleteUserHistoryResult result = new DeleteUserHistoryResult(list);
		return resultFactory.getSuccess(result, locale);
	}
	
	@Api(level=Role.CLIENT)
	@RequestMapping(value={PathVariables.PAGE_NUMBER + "/" + PathVariables.PAGE_SIZE}, method=RequestMethod.GET)
	@ResponseBody	
	public ApiResult getDeleteUserHistory(HttpServletRequest request, 
			@PathVariable int pageNumber, @PathVariable int pageSize, Locale locale) throws Exception {
    	logger.debug("deleteUserHistory!");
    	
		if (pageNumber <= 0 || pageSize <= 0 || pageSize > HistoryConstants.MAX_PAGE_SIZE) {
			logger.debug("Invalid parameter: wrong page request");
			throw new InvalidParameterException("wrong page request");
		}
    	
    	String clientId = (String)request.getAttribute(AttributeValues.CLIENT_ID);    	
    	List<DeleteUserHistory> list = historyService.getDeleteUserHistory(clientId, pageNumber, pageSize);
    	
    	DeleteUserHistoryResult result = new DeleteUserHistoryResult(list);
		return resultFactory.getSuccess(result, locale);
	}	
}
