package com.sabsari.dolphin.core.exception.business;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.ClientException;

public class NotFoundApiException extends ClientException {	

	private static final long serialVersionUID = 3337935268306146347L;	

	public NotFoundApiException() {
		super(ResponseCode.ERROR_NOT_FOUND_API);
	}
}
