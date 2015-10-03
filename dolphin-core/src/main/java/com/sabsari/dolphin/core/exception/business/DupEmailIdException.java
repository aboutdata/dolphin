package com.sabsari.dolphin.core.exception.business;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.ClientException;

public class DupEmailIdException extends ClientException {

	private static final long serialVersionUID = -7795752837315684802L;
	
	public DupEmailIdException(String emailId) {
		super(ResponseCode.FAILURE_DUP_EMAIL_ID, emailId);
	}

}
