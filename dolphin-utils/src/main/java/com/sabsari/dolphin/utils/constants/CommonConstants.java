package com.sabsari.dolphin.utils.constants;

/**
 * Common constants
 * 
 * @author sabsari
 * @date   2014. 2. 13.
 */
public final class CommonConstants {
	
	// Biz logic constants	
	public static final int MAX_LOGIN_FAIL_COUNT = 5;					// 5 회
	public static final int AUTH_TOKEN_EXPIRATION_PERIOD = 60;			// 60 일
	public static final int EMAIL_CERTIFICATION_EXPIRATION_PERIOD = 1;	// 1 일
	
	// aes256 key
	public static final String AES256_KEY	= "x0YDdpOsGlmmayljH5Bk6rN/1XZpy24jz5L3j0vZu48=";	// 32 byte
	public static final String AES256_IV	= "di78kYUOAAYyJnrLKSNZ5A==";						// 16 byte
	
	// aes128 key
	public static final String AES128_KEY	= "cZgvAUbqC5MNDI3vBKdaEw==";	// 16 byte
	public static final String AES128_IV	= "dUfy3zeP2FT7eQMuVExfxQ==";	// 16 byte
		
	// character set
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	// default TimeZone (한국표준시)
	public static final String DEFAULT_TIMEZONE = "Asia/Seoul";
	
	// birthday pattern as iso8601 format
	public static final String BIRTHDAY_PATTERN	= ISO8601Format.DATE_PATTERN.getFormat();
	
	// regular expressions
	// email은 대문자, 소문자, 숫자 그리고 '.' '-' '_' 을 가질 수있지만 . 로 시작해서는 안된다.
	public static final String EMAIL_REGEX = "^[_a-zA-Z0-9-]+([._a-zA-Z0-9-]*)@[a-zA-Z0-9]+(\\.[a-zA-Z0-9-]+)+$";
}
