package com.sabsari.dolphin.core.exception.business;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.ClientException;

public class EmailAuthFailCountOverException extends ClientException {

	private static final long serialVersionUID = 3250123107986617314L;
	
	public EmailAuthFailCountOverException(String id) {
		super(ResponseCode.FAILURE_EMAIL_AUTH_FAIL_COUNT_OVER, id);
	}
}
