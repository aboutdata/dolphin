package com.sabsari.dolphin.core.exception.business;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.ClientException;

public class PasswordUpdateFailException extends ClientException {
	
	private static final long serialVersionUID = -7049982698641156300L;

	public PasswordUpdateFailException() {
		super(ResponseCode.FAILURE_PASSWORD_UPDATE);
	}
}
