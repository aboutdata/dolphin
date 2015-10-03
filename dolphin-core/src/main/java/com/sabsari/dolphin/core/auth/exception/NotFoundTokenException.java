package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class NotFoundTokenException extends AuthException {
	
	private static final long serialVersionUID = 8385975764913472034L;

	public NotFoundTokenException() {
		super(ErrorMessage.NOT_FOUND_TOKEN);
	}

}
