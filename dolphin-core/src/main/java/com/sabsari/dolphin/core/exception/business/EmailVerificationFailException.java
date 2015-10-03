package com.sabsari.dolphin.core.exception.business;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.ClientException;

public class EmailVerificationFailException extends ClientException {

	private static final long serialVersionUID = -8091117936805631586L;
	
	public EmailVerificationFailException(String message) {
		super(ResponseCode.FAILURE_EMAIL_VERIFICATION, message);
	}
}
