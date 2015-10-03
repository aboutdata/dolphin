package com.sabsari.dolphin.core.exception.system;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.SystemException;

public class NotFoundResponseMessageException extends SystemException {

	private static final long serialVersionUID = 2221218482393834897L;

	public NotFoundResponseMessageException(Throwable cause) {
		super(cause, ResponseCode.ERROR_NOT_FOUND_RESPONSE_MESSAGE);
	}

}
