package com.sabsari.dolphin.api.auth.controller.token;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sabsari.dolphin.api.annotation.Api;
import com.sabsari.dolphin.api.auth.model.constants.PropertyValues;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.auth.domain.code.TokenTypeHint;
import com.sabsari.dolphin.core.auth.exception.InvalidRequestException;
import com.sabsari.dolphin.core.auth.service.AuthorizationService;

@Controller
public class RevokeToken {

	private static final Logger logger = LoggerFactory.getLogger(RevokeToken.class);
	
	@Autowired
	private AuthorizationService  authorizationService;
	
	@Api(level=Role.TOKEN_CONSUMER)
	@RequestMapping(value={"/auth/revoke"}, method=RequestMethod.POST)
	@ResponseBody	
	public void revokeToken(HttpServletRequest request,
			@RequestParam Map<String, String> params) throws Exception {    	
		logger.debug("revokeToken!");
		
		String token = params.get(PropertyValues.TOKEN);
		TokenTypeHint tokenTypeHint = TokenTypeHint.search(params.get(PropertyValues.TOKEN_TYPE_HINT));
		
		if (token == null || tokenTypeHint == null) {
			logger.debug("invalid request: no token or tokenTypeHint.");
			throw new InvalidRequestException();
		}
		
		authorizationService.revokeToken(tokenTypeHint, token);
	}
}
