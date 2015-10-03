package com.sabsari.dolphin.core.exception.business;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.ClientException;

public class NotFoundGroupException extends ClientException {

	private static final long serialVersionUID = -4606287699445178992L;

	public NotFoundGroupException(String groupKey) {
		super(ResponseCode.FAILURE_NOT_FOUND_GROUP, groupKey);
	}
}
