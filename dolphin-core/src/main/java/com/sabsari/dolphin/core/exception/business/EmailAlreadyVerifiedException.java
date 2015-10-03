package com.sabsari.dolphin.core.exception.business;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.ClientException;

public class EmailAlreadyVerifiedException extends ClientException {

	private static final long serialVersionUID = 5044851338222248318L;

	public EmailAlreadyVerifiedException(String userKey) {
		super(ResponseCode.ERROR_EMAIL_ALREADY_VERIFIED, userKey);
	}

}
