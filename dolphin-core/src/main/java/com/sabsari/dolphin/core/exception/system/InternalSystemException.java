package com.sabsari.dolphin.core.exception.system;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.SystemException;

public class InternalSystemException extends SystemException {

	private static final long serialVersionUID = 2673138484029456360L;
	
	public InternalSystemException(ResponseCode responseCode) {
		super(responseCode);
	}
	
	public InternalSystemException() {
		super(ResponseCode.ERROR_UNKNOWN);
	}
}
