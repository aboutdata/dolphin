package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class InvalidAuthenticationSchemeException extends AuthException {

	private static final long serialVersionUID = -5850829392637611821L;

	public InvalidAuthenticationSchemeException() {
		super(ErrorMessage.INVALID_AUTHENTICATION_SCHEME);
	}

}
