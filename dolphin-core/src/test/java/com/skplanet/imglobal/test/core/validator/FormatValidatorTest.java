package com.sabsari.dolphin.test.core.validator;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sabsari.dolphin.core.validator.FormatValidator;

public class FormatValidatorTest {

	@Test
	public void _이름확인테스트() {		
		String input0 = " kljoi-oe_wr- ijfeo2.33.498r39k.";		
		assertTrue(FormatValidator.isName(input0));
	}
	
	@Test
	public void _이메일확인테스트() {
		String input = "sabsari@sk.com";		
		assertTrue(FormatValidator.isEmail(input));
	}
	
	@Test
	public void _날짜형식확인테스트() {
		String input = "19990328";		
		assertTrue(FormatValidator.isBirthday(input));
	}
	
	@Test
	public void _ISO8601확인테스트() {
		String input = "19820318T090000+0900";		
		assertTrue(FormatValidator.isISO8601DateTime(input));
	}
	
	@Test
	public void _폰번호확인테스트() {		
		String input = "1234567890123456";		
		assertTrue(FormatValidator.isPhoneNo(input));
	}
	
	@Test
	public void _비번테스트() {		
		String input = "12{}3\"/sdf239'?+*-_[]|";		
		assertTrue(FormatValidator.isPassword(input));
	}
	
	@Test
	public void _IPV4테스트() {		
		String input = "24.25.101.255";		
		assertTrue(FormatValidator.isIpV4Addr(input));
	}
}
