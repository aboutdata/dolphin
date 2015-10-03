package com.sabsari.dolphin.core.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.sabsari.dolphin.core.common.ISO8601;

public class FormatValidator {

	// email은 대문자, 소문자, 숫자 그리고 '.' '-' '_' 을 가질 수있지만 . 로 시작해서는 안된다.
	private static final String EMAIL_REGEX = "^[_a-zA-Z0-9-]+([._a-zA-Z0-9-]*)@[a-zA-Z0-9]+(\\.[a-zA-Z0-9-]+)+$";
	
	private static final String DATE_yyyyMMdd_REGEX = "^([0-9][0-9][0-9][0-9])(((01|03|05|07|08|10|12)(0[1-9]|[12][0-9]|3[01]))|((04|06|09|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[12][0-9])))$";
	
	// 이름은 알파벳 대소문자, 숫자, '.', '_', '-', 공백문자만 허용, 길이는 1~32
	private static final String NAME_REGEX = "^([._a-zA-Z0-9- ]){1,32}$";
	
	// 폰번호는 숫자 형식만 길이는 8~16
	private static final String PHONE_REGEX = "^(\\d){8,16}$";
	
	// 비번은 길이만 체크 6~24
	private static final String PASSWORD_REGEX = "^([~!@#$%^&*()-_=|{}:;\"'<>,./?a-zA-Z0-9\\+\\[\\]]){6,24}$";
	
	// ip v4 형식 확인
	private static final String IPV4_REGEX = "^([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])\\." +
											 "([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])\\." +
											 "([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])\\." +
											 "([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])$";
	
	public static boolean isEmail(String input) {
		if (StringUtils.isEmpty(input))
			return false;
		
		return Pattern.matches(EMAIL_REGEX, input);
	}
	
	public static boolean isISO8601DateTime(String input) {
		if (StringUtils.isEmpty(input))
			return false;
		
		DateFormat dateFormat = new SimpleDateFormat(ISO8601.DATETIME_PATTERN.format());
		
        try {
        	dateFormat.parse(input);
        } catch (ParseException e) {
            return false;
        }

        return true;
	}
	
	// Gregorian calendar 기준
	public static boolean isBirthday(String input) {
		if (StringUtils.isEmpty(input))
			return false;
		
		if (!Pattern.matches(DATE_yyyyMMdd_REGEX, input))
			return false;
		
		String monthDay = input.substring(4);
		int year = 0;
		
		if (monthDay.equals("0229")) {
			year = Integer.parseInt(input.substring(0, 4));
			
			if ((year % 400) == 0 || ((year % 100) != 0 && (year % 4) == 0))
				return true;
			else
				return false;
		}
		else
			return true;		
	}
		
	public static boolean isName(String input) {
		if (StringUtils.isEmpty(input))
			return false;
		
		return Pattern.matches(NAME_REGEX, input);
	}
	
	public static boolean isPhoneNo(String input) {
		if (StringUtils.isEmpty(input))
			return false;
		
		return Pattern.matches(PHONE_REGEX, input);
	}
	
	public static boolean isPassword(String input) {
		if (StringUtils.isEmpty(input))
			return false;
		
		return Pattern.matches(PASSWORD_REGEX, input);
	}
	
	public static boolean isIpV4Addr(String input) {
		if (StringUtils.isEmpty(input))
			return false;
		
		return Pattern.matches(IPV4_REGEX, input);
	}
}
