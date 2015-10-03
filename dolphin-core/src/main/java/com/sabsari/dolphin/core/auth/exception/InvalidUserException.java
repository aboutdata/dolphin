package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class InvalidUserException extends AuthException {

	private static final long serialVersionUID = 8712215097919373277L;

	public InvalidUserException() {
		super(ErrorMessage.INVALID_USER);
	}

}
