package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class InvalidStatusException extends AuthException {

	private static final long serialVersionUID = 7683191889549216874L;

	public InvalidStatusException() {
		super(ErrorMessage.INVALID_STATUS);
	}

}
