package com.sabsari.dolphin.core.auth.exception.status401;

import com.sabsari.dolphin.core.auth.domain.code.HttpAuthenticationScheme;

public class NotFoundAccessTokenException extends Status401Excepiton {

	private static final long serialVersionUID = 410956162377690774L;
	
	public NotFoundAccessTokenException(HttpAuthenticationScheme scheme) {
		super(scheme);
	}
}
