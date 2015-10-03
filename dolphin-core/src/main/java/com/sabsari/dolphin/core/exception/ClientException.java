package com.sabsari.dolphin.core.exception;

import com.sabsari.dolphin.core.common.code.ResponseCode;

public class ClientException extends ApiException {

	private static final long serialVersionUID = 8952152878530326791L;

	public ClientException(ResponseCode errorResponseCode) {
		super(errorResponseCode);
	}
	
	public ClientException(ResponseCode errorResponseCode, String... parameter) {
		super(errorResponseCode, parameter);
	}
}
