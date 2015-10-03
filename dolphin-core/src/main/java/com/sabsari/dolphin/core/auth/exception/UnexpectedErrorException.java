package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class UnexpectedErrorException extends AuthException {

	private static final long serialVersionUID = 2310484793552029897L;

	public UnexpectedErrorException() {
		super(ErrorMessage.UNEXPECTED);
	}

}
