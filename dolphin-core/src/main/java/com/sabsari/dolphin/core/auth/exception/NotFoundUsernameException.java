package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class NotFoundUsernameException extends AuthException {

	private static final long serialVersionUID = 2931028891716166795L;

	public NotFoundUsernameException() {
		super(ErrorMessage.NOT_FOUND_USERNAME);
	}

}
