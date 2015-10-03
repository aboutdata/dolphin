package com.sabsari.dolphin.core.value;

import org.junit.Test;

import com.sabsari.dolphin.core.value.ValueGenerator;
import com.sabsari.dolphin.core.value.ValueGeneratorImpl;

public class ValueGeneratorTest {

	ValueGenerator valueGenerator = new ValueGeneratorImpl();
	
	@Test
	public void _그룹키생성() {
		String result = valueGenerator.generateGroupKey();
		System.out.println("_그룹키생성: " + result);
	}
	
	@Test
	public void _클라이언트아이디생성() {
		String result = valueGenerator.generateClientId();
		System.out.println("_클라이언트아이디생성: " + result);
	}
	
	@Test
	public void _유저키생성() {
		String result = valueGenerator.generateUserKey();
		System.out.println("_유저키생성: " + result);
	}
	
	@Test
	public void _이메일검증값생성() {
		String result = valueGenerator.generateEmailVerificationValue();
		System.out.println("_이메일검증값생성: " + result);
	}
	
	@Test
	public void _억세스토큰생성() {
		String result = valueGenerator.generateAccessToken();
		System.out.println("_억세스토큰생성: " + result);
	}
	
	@Test
	public void _클라이언트시크릿생성() {
		String result = valueGenerator.generateClientSecret();
		System.out.println("_클라이언트시크릿생성: " + result);
	}
	
	@Test
	public void _랜덤패스워드생성() {
		String result = valueGenerator.generateRandomPassword();
		System.out.println("_랜덤패스워드생성: " + result);
	}
}
