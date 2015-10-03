package com.sabsari.dolphin.core.auth.exception.status401;

import com.sabsari.dolphin.core.auth.domain.code.HttpAuthenticationScheme;
import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class TokenExpiredException extends Status401Excepiton {

	private static final long serialVersionUID = -2511607630469338238L;

	public TokenExpiredException(HttpAuthenticationScheme scheme) {
		super(ErrorMessage.INVALID_TOKEN, scheme);
	}
}
