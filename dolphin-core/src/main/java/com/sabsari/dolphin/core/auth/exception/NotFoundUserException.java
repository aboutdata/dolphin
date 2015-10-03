package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class NotFoundUserException extends AuthException {

	private static final long serialVersionUID = 4646758216197174373L;

	public NotFoundUserException() {
		super(ErrorMessage.NOT_FOUND_USER);
	}

}
