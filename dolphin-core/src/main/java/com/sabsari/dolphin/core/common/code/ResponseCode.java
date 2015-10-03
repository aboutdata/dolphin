package com.sabsari.dolphin.core.common.code;

/**
 * responseCode 정의
 * 
 * @author sabsari
 * @date   2014. 2. 12.
 */
public enum ResponseCode {

	// success
	SUCCESS							("S001"),		// OK
		
	// business error : common
	FAILURE_NOT_FOUND_USER			("F001"),		// The User({0}) is not found.
	FAILURE_NOT_FOUND_GROUP			("F002"),		// The Group({0}) is not found.
	FAILURE_NOT_FOUND_CLIENT_ID		("F003"),		// The ClientId({0}) is not found.
	FAILURE_NOT_FOUND_HISTORY		("F004"),		// No history entries found.
	
	// business error : authentication
	FAILURE_DUP_EMAIL_ID					("F101"),	// The email emailId({0}) already exists.
	FAILURE_NOT_NOMAL_EMAIL_AUTH_STATUS		("F102"),	// The email auth status of the user({0}) is not normal({1}).
	FAILURE_EMAIL_AUTH_FAIL_COUNT_OVER		("F103"),	// The email auth failure count is over({0}).
	FAILURE_EMAIL_VERIFICATION				("F104"),	// The email verification has failed({0}).
	FAILURE_PASSWORD_VERIFICAITON			("F105"),	// The password of the user({0}) is incorrect.
	FAILURE_PASSWORD_UPDATE					("F106"),	// New password is same as existing one.
	

	// client side error
	ERROR_NOT_FOUND_API				("C001"),		// The API requested does not exist.
	ERROR_INVALID_REQUEST			("C002"),		// Invalid request({0}).
	ERROR_NO_MANDATORY_PARAMETER	("C003"),		// The parameter ({0}) is mandatory.
	ERROR_INVALID_REQUEST_PARAMETER	("C004"),		// The request parameters are invalid({0}).
	ERROR_UNKNOWN_PARAMETER			("C005"),		// The request parameter is unknown({0}).
	ERROR_EMAIL_ALREADY_VERIFIED	("C006"),		// The email of the user({}) is already verified.
	
	// server side error
	ERROR_UNKNOWN						("E001"),		// An unexpected error has occurred in the internal system.
	ERROR_NOT_FOUND_RESPONSE_MESSAGE	("E002"),		// An unexpected error has occurred in the internal system.
	ERROR_NOT_FOUND_EMAIL_VERIFICATION	("E003")		// An unexpected error has occurred in the internal system.
	;
	
	private String code;
	
	ResponseCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	public static ResponseCode search(String code) {
		for (ResponseCode resCode : ResponseCode.values()) {
			if (resCode.getCode().equals(code) )
				return resCode;
		}
		
		return ERROR_UNKNOWN;
	}
}
