package com.sabsari.dolphin.api.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sabsari.dolphin.api.annotation.Api;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.common.ISO8601;
import com.sabsari.dolphin.core.common.util.CommonUtils;
import com.sabsari.dolphin.core.web.util.WebUtils;

@Controller
public class TestApi {

	private final static Logger logger = LoggerFactory.getLogger(TestApi.class);
	
	@Api(level=Role.ANONYMOUS)
    @RequestMapping(value={"/test/isAlive"}, method=RequestMethod.GET)
	@ResponseBody	
	public String isAlive(HttpServletRequest request) {
		String remoteIp = WebUtils.getRemoteIp(request);
    	logger.debug("isAlive? Ok~! remoteIp: " + remoteIp);
		return "I'm alive! [remote ip: " + remoteIp + "], [server time: " + CommonUtils.getISO8601FormatDate(ISO8601.DATETIME_EXTENDED_PATTERN, new Date()) + "]";
	}
		
	/*	
	//테스트용 API	 
	@Api(level=Role.ANONYMOUS)
    @RequestMapping(value="/admin/init", method=RequestMethod.GET)
    @ResponseBody   
    public String createAdmin() throws Exception {
		logger.debug("createAdmin!");
    	String groupKey 	= null;
    	String clientId 	= null;
    	String clientSecret = null;
    	
		Application application = adminService.createAdmin();
		groupKey = application.getGroupKey();
		clientId = application.getClientId();
		clientSecret = application.getClientSecret();
		
		com.sabsari.dolphin.auth.domain.AccessToken accessToken = authorizationService.issueAdminToken(clientId);
		
		StringBuffer sb = new StringBuffer();
		sb.append("groupKey=").append(groupKey).append(", ");
		sb.append("clientId=").append(clientId).append(", ");
		sb.append("clientSecret=").append(clientSecret).append(", ");
		sb.append("accessToken=").append(accessToken.getAccessToken());
		
		return sb.toString();
    }*/
}
