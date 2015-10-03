package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public class NotFoundClientException extends AuthException {

	private static final long serialVersionUID = -9155401829447193136L;

	public NotFoundClientException() {
		super(ErrorMessage.NOT_FOUND_CLIENT);
	}

}
