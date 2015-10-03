package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class InvalidAuthenticationInfoException extends AuthException {

	private static final long serialVersionUID = 5886905234675233703L;
	
	public InvalidAuthenticationInfoException() {
		super(ErrorMessage.INVALID_AUTHENTICATION_INFO);
	}

	

}
