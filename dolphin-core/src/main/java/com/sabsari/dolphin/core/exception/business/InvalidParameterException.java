package com.sabsari.dolphin.core.exception.business;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.ClientException;

public class InvalidParameterException extends ClientException {
	
	private static final long serialVersionUID = 2530320766520442547L;

	public InvalidParameterException(String message) {
		super(ResponseCode.ERROR_INVALID_REQUEST_PARAMETER, message);
	}

}
