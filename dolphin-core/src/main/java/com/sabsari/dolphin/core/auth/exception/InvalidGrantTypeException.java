package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class InvalidGrantTypeException extends AuthException {

	private static final long serialVersionUID = -2418222708813325410L;

	public InvalidGrantTypeException() {
		super(ErrorMessage.INVALID_GRANT);
	}

}
