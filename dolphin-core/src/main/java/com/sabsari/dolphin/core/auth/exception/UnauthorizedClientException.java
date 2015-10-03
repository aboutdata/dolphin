package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class UnauthorizedClientException extends AuthException {

	private static final long serialVersionUID = 1107682147156637948L;

	public UnauthorizedClientException() {
		super(ErrorMessage.UNAUTHORIZED_CLIENT);
	}

}
