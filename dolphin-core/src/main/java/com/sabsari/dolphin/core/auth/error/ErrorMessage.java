package com.sabsari.dolphin.core.auth.error;

import org.springframework.http.HttpStatus;

public enum ErrorMessage {
	INVALID_REQUEST("invalid_request", HttpStatus.BAD_REQUEST.value()),
	INVALID_CLIENT("invalid_client", HttpStatus.UNAUTHORIZED.value()),	// response header에 WWW-Authenticate 포함시킬 것 client, user, admin
	INVALID_GRANT("invalid_grant", HttpStatus.NOT_ACCEPTABLE.value()),
	UNAUTHORIZED_CLIENT("unauthorized_client", HttpStatus.UNAUTHORIZED.value()),	// WWW-Authenticate 불필요
	UNSUPPORTED_GRANT_TYPE("unsupported_grant_type", HttpStatus.BAD_REQUEST.value()),	
	UNSUPPORTED_TOKEN_TYPE("unsupported_token_type", HttpStatus.BAD_REQUEST.value()),
	INVALID_SCOPE("invalid_scope", HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.value()),
	
	INVALID_STATUS("invalid_status", HttpStatus.LOCKED.value()),
	INVALID_TOKEN("token_expired", HttpStatus.UNAUTHORIZED.value()),
	INVALID_USER("invalid_user", HttpStatus.BAD_REQUEST.value()),
	NOT_FOUND_USER("not_found_user", HttpStatus.BAD_REQUEST.value()),
	NOT_FOUND_USERNAME("not_found_username", HttpStatus.BAD_REQUEST.value()),
	NOT_FOUND_CLIENT("not_found_client", HttpStatus.BAD_REQUEST.value()),
	NOT_FOUND_TOKEN("not_found_token", HttpStatus.BAD_REQUEST.value()), 
	INVALID_AUTHENTICATION_INFO("invalid_authentication_info", HttpStatus.BAD_REQUEST.value()),
	INVALID_AUTHENTICATION_SCHEME("invalid_authentication_scheme", HttpStatus.BAD_REQUEST.value()),
	NO_AUTHENTICATION_INFO("no_authentication_header", HttpStatus.BAD_REQUEST.value()),
	UNEXPECTED("unexpected_error", HttpStatus.INTERNAL_SERVER_ERROR.value());

	private String message;
	private int status;
	
	private ErrorMessage(String message, int status){
		this.message = message;
		this.status = status;
	}

	public String message() {
		return this.message;
	}
	
	public int status() {
		return this.status;
	}
}
