package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class NoAuthenticationInfoException extends AuthException {

	private static final long serialVersionUID = 7349910675989251395L;

	public NoAuthenticationInfoException() {
		super(ErrorMessage.NO_AUTHENTICATION_INFO);
	}

}
