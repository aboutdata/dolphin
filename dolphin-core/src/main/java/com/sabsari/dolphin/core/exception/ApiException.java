package com.sabsari.dolphin.core.exception;

import com.sabsari.dolphin.core.common.code.ResponseCode;

public abstract class ApiException extends RuntimeException {

	private static final long serialVersionUID = -3047226600032758348L;

	private final ResponseCode errorResponseCode;
	private final String[] messageParameters;

	public ApiException(ResponseCode errorResponseCode) {
		this.errorResponseCode = errorResponseCode;
		this.messageParameters = null;
	}

	public ApiException(ResponseCode errorResponseCode, String... parameter) {
		this.errorResponseCode = errorResponseCode;
		this.messageParameters = parameter;
	}
		
	public ApiException(Throwable cause, ResponseCode errorResponseCode) {
		super(cause);
		this.errorResponseCode = errorResponseCode;
		this.messageParameters = null;
	}

	public ApiException(Throwable cause, ResponseCode errorResponseCode, String... parameter) {
		super(cause);
		this.errorResponseCode = errorResponseCode;
		this.messageParameters = parameter;
	}

	public ResponseCode getErrorResponseCode() {
		return errorResponseCode;
	}

	public String[] getMessageParameters() {
		return messageParameters;
	}
}
