package com.sabsari.dolphin.core.exception;

import com.sabsari.dolphin.core.common.code.ResponseCode;

public class SystemException extends ApiException {

	private static final long serialVersionUID = 2879176586070774266L;

	public SystemException(ResponseCode errorResponseCode) {
		super(errorResponseCode);
	}
	
	public SystemException(Throwable cause, ResponseCode errorResponseCode) {
		super(cause, errorResponseCode);
	}
}
