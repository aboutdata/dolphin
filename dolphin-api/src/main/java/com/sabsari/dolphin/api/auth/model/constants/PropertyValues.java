package com.sabsari.dolphin.api.auth.model.constants;

public class PropertyValues {
	
	// token request
	public static final String GRANT_TYPE	= "grant_type";
	public static final String USERNAME		= "username";
	public static final String PASSWORD		= "password";
	@Deprecated public static final String SCOPE = "scope";
	
	// revoke token request
	public static final String TOKEN		= "token";
	public static final String TOKEN_TYPE_HINT	= "token_type_hint";
	
	// response
	public static final String ACCESS_TOKEN	= "access_token";
	public static final String TOKEN_TYPE	= "token_type";
	public static final String EXPIRES_IN	= "expires_in";
	public static final String REFRESH_TOKEN = "refresh_token";
	
	// error
	public static final String ERROR = "error";
	@Deprecated public static final String ERROR_DESCRIPTION = "error_description";
	@Deprecated public static final String ERROR_URI		 = "error_uri";	
}
