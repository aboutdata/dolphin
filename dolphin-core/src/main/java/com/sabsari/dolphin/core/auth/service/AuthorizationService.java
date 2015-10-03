package com.sabsari.dolphin.core.auth.service;

import com.sabsari.dolphin.core.auth.domain.AccessToken;
import com.sabsari.dolphin.core.auth.domain.RefreshToken;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.auth.domain.code.TokenTypeHint;
import com.sabsari.dolphin.core.member.domain.Application;

public interface AuthorizationService {
	public boolean hasPermission(String userKey, String clientId);
	
	public String verifyUserPassword(String clientId, String emailId, String password);
	
	public Application verifyClientSecret(String clientId, String clientSecret);
	
	public AccessToken issueAdminToken(String clientId);
		
	public AccessToken issueAccessToken(String clientId);
	
	public AccessToken issueAccessToken(String clientId, Role role);
	
	public RefreshToken issueRefreshToken(String userKey);
	
	public RefreshToken issueRefreshToken(String userKey, Role role);
	
	public RefreshToken refreshToken(String refreshToken);
	
	public void revokeToken(TokenTypeHint tokenTypeHint, String token);
	
	public AccessToken verifyAccessToken(String accessToken);
}
