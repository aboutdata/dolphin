package com.sabsari.dolphin.core.exception.business;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.ClientException;

public class NotFoundUserException extends ClientException {

	private static final long serialVersionUID = -6748089402676370385L;

	public NotFoundUserException(String userKey) {
		super(ResponseCode.FAILURE_NOT_FOUND_USER, userKey);
	}
	
	public NotFoundUserException(String groupKey, String emailId) {
		super(ResponseCode.FAILURE_NOT_FOUND_USER, "Group:" + groupKey + " : " + emailId);
	}
}
