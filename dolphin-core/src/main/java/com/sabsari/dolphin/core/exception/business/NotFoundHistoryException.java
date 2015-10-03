package com.sabsari.dolphin.core.exception.business;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.ClientException;

public class NotFoundHistoryException extends ClientException {

	private static final long serialVersionUID = 6715219744478450002L;

	public NotFoundHistoryException() {
		super(ResponseCode.FAILURE_NOT_FOUND_HISTORY);
	}

}
