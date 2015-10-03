package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class InvalidRequestException extends AuthException {

	private static final long serialVersionUID = 1499980627509812903L;

	public InvalidRequestException() {
		super(ErrorMessage.INVALID_REQUEST);
	}

}
