package com.sabsari.dolphin.core.auth.exception.status401;

import com.sabsari.dolphin.core.auth.domain.code.HttpAuthenticationScheme;
import com.sabsari.dolphin.core.auth.error.ErrorMessage;
import com.sabsari.dolphin.core.auth.exception.AuthException;

public abstract class Status401Excepiton extends AuthException {

	private static final long serialVersionUID = -7673445170280208100L;
	
	private String WWWAuthenticateHeader;
		
	public String getWWWAuthenticateHeader() {
		return this.WWWAuthenticateHeader;
	}

	public Status401Excepiton(HttpAuthenticationScheme scheme) {
		super(ErrorMessage.INVALID_CLIENT);
		this.WWWAuthenticateHeader = HttpAuthenticationScheme.getWWWAuthenticate(scheme);
	}
	
	public Status401Excepiton(ErrorMessage errorMessage, HttpAuthenticationScheme scheme) {
		super(errorMessage);
		this.WWWAuthenticateHeader = HttpAuthenticationScheme.getWWWAuthenticate(scheme);
	}
}
