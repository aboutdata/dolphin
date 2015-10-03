package com.sabsari.dolphin.core.exception.system;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.SystemException;

public class NotFoundEmailVerificationException extends SystemException {

	private static final long serialVersionUID = 7947304527576198904L;

	public NotFoundEmailVerificationException() {
		super(ResponseCode.ERROR_NOT_FOUND_EMAIL_VERIFICATION);
	}

}
