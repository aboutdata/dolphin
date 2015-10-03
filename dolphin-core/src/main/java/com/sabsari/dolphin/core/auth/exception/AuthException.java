package com.sabsari.dolphin.core.auth.exception;

import com.sabsari.dolphin.core.auth.error.ErrorMessage;

public abstract class AuthException extends RuntimeException {
	
	private static final long serialVersionUID = -7311622657759544303L;

	private ErrorMessage errorMessage;
	
	public AuthException(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return this.errorMessage.message();
	}
	
	public int getStatus() {
		return this.errorMessage.status();
	}
}