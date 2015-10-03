package com.sabsari.dolphin.core.exception.business;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.ClientException;

public class PasswordVerificationFailException extends ClientException {

	private static final long serialVersionUID = 1814231481629832249L;
	
	public PasswordVerificationFailException(String id) {
		super(ResponseCode.FAILURE_PASSWORD_VERIFICAITON, id);
	}
}
