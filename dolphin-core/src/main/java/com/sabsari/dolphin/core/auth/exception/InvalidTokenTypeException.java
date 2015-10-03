package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class InvalidTokenTypeException extends AuthException {

	private static final long serialVersionUID = 18697379953176363L;

	public InvalidTokenTypeException() {
		super(ErrorMessage.UNSUPPORTED_TOKEN_TYPE);
	}

}
