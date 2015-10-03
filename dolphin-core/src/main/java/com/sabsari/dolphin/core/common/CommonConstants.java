package com.sabsari.dolphin.core.common;

import java.util.Locale;

import com.sabsari.dolphin.core.auth.domain.code.TokenLifeTime;

/**
 * Common constants
 * 
 * @author sabsari
 * @date   2014. 2. 13.
 */
public final class CommonConstants {
	
	// Biz logic constants	
	public static final int MAX_LOGIN_FAIL_COUNT = 10;			// 10 회
	
	// history data keeping period
	public static final int HISTORY_KEEPING_PERIOD = 100;		// 100 일
		
	// character set
	public static final String CHARSET_UTF8 = "UTF-8";
	
	// default TimeZone (한국표준시)
	public static final String DEFAULT_TIMEZONE = "Asia/Seoul";
	
	// birthday pattern as iso8601 format
	public static final String BIRTHDAY_PATTERN	= ISO8601.DATE_PATTERN.format();
	
	// default locale
	public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
	
	public static final String PASSWORD_ENCODE_SECRET = "ZHAxj2CdtXQgOkZk5j";
	
	public static final TokenLifeTime ACCESSTOKEN_DEFAULT_LIFETIME = TokenLifeTime.HOUR;
	public static final TokenLifeTime REFRESHTOKEN_DEFAULT_LIFETIME = TokenLifeTime.TWO_WEEKS;
}
