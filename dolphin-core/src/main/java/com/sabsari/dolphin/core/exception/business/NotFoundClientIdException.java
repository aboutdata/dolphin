package com.sabsari.dolphin.core.exception.business;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.ClientException;

public class NotFoundClientIdException extends ClientException {

	private static final long serialVersionUID = 1488992332621613897L;
	
	public NotFoundClientIdException(String clientId) {
		super(ResponseCode.FAILURE_NOT_FOUND_CLIENT_ID, clientId);
	}

}
