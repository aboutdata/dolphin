package com.sabsari.dolphin.test.core;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.AntPathMatcher;

import com.sabsari.dolphin.core.auth.domain.code.HttpAuthenticationScheme;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.common.CommonConstants;
import com.sabsari.dolphin.core.common.ISO8601;
import com.sabsari.dolphin.core.common.util.CommonUtils;
import com.sabsari.dolphin.core.value.ValueGenerator;
import com.sabsari.dolphin.core.value.ValueGeneratorImpl;

public class SimpleTest {
		
	@Test
	public void _유저키생성() {
		String key = UUID.randomUUID().toString();
		System.out.println(key);
	}
	
	@Test
	public void _이눔테스트() {
		boolean result = Role.isValidRole("ROLE_ADMN");
		System.out.println(result);
	}
	
	@Test
	public void _타입확인() {
		String obj = null;
		boolean result = obj instanceof String;
		System.out.println(result);
	}
	
	@Test
	public void _시간연산() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.SECOND, -100);		
		int expiresIn = 102;		
		System.out.println("시간연산: " + CommonUtils.isExpired(expiresIn, c.getTime()));
	}
	
	@Test
	public void _남은시간() {
		Calendar c = Calendar.getInstance();
//		c.add(Calendar.SECOND, -100);
		c.set(2014, 7, 13, 14, 30);
		int expiresIn = 3600;		
		System.out.println("남은시간: " + CommonUtils.getRestTime(expiresIn, c.getTime()));
	}
	
	@Test
	public void _uri파싱() {
		String pathName = "userKey";
		String pathPattern = "{"+ pathName + "}";
		String temp = "asdfij4oweifjn";
		
		AntPathMatcher matcher = new AntPathMatcher();		
		
		boolean result = matcher.match("/v1/user/" + pathPattern, "/v1/user/" + temp);
		String pathValue = matcher.extractPathWithinPattern("/v1/user/*", "/v1/user/" + temp);
		Map<String, String> pathValueMap = matcher.extractUriTemplateVariables("/v1/user/" + pathPattern, "/v1/user/" + temp);
		
		assertTrue(result);
		assertEquals(temp, pathValue);
		assertEquals(temp, pathValueMap.get(pathName));
	}
	
	@Test
	public void _401응답헤더생성() {
		System.out.println(HttpAuthenticationScheme.getWWWAuthenticate(HttpAuthenticationScheme.BASIC));
	}
	
	@Test
	public void _becrypt인코딩테스트() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		
		String tempPassword = "aaaa23-iu2iud2d-ji4-23489f-3498fnjknfj";
		
		int counter = 10;
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < counter ; i++) {
			passwordEncoder.encode(tempPassword);
		}
		long end = System.currentTimeMillis();
		long elapsed = end - start;
		System.out.println(counter + " 건의 인코딩 소요시간: " + elapsed + "ms");
	}
		
	@Test
	public void _becrypt디코딩테스트() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		
		String tempPassword = "aaaa23-iu2iud2d-ji4-23489f-3498fnjknfj";
		
		int counter = 10;
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < counter ; i++) {
			passwordEncoder.matches(tempPassword, "$2a$10$6PB8fvHtqa70EEguZhe3nufU6PCP6Y8ZHAxj2CdtXQgOkZk5jzYZG");
		}
		long end = System.currentTimeMillis();
		long elapsed = end - start;
		System.out.println(counter + " 건의 디코딩 소요시간: " + elapsed + "ms");
	}
	
	@Test
	public void _standard인코딩테스트() {
		PasswordEncoder passwordEncoder = new StandardPasswordEncoder(CommonConstants.PASSWORD_ENCODE_SECRET);
		
		String tempPassword = "aaaa23-iu2iud2d-ji4-23489f-3498fnjknfj";
		
		int counter = 10;
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < counter ; i++) {
			passwordEncoder.encode(tempPassword);
		}
		long end = System.currentTimeMillis();
		long elapsed = end - start;
		System.out.println(counter + " 건의 인코딩 소요시간: " + elapsed + "ms");
	}
	
	@Test
	public void _standard디코딩테스트() {
		PasswordEncoder passwordEncoder = new StandardPasswordEncoder(CommonConstants.PASSWORD_ENCODE_SECRET);
		
		String tempPassword = "aaaa23-iu2iud2d-ji4-23489f-3498fnjknfj";
		
		int counter = 10;
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < counter ; i++) {
			passwordEncoder.matches(tempPassword, "f2b64cea6665778e8e73dea41bbcec9f0a8fa82ef62fa37371dd9fb544bd731f3fd5d849e0d92b92");
		}
		long end = System.currentTimeMillis();
		long elapsed = end - start;
		System.out.println(counter + " 건의 디코딩 소요시간: " + elapsed + "ms");
	}
	
	@Test
	public void _랜덤스트링테스트() {
		ValueGenerator gen = new ValueGeneratorImpl();
		
		int counter = 10;
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < counter ; i++) {
			gen.generateEmailVerificationValue();
		}
		long end = System.currentTimeMillis();
		long elapsed = end - start;
		System.out.println(counter + " 건의 랜덤 스트링 생성 소요시간: " + elapsed + "ms");
	}
	
	@Test
	public void _ip변환테스트() {
		int counter = 100000;
		long start = System.currentTimeMillis();
		
		for (int i = 0; i < counter; i++) {
			CommonUtils.ipToLong(CommonUtils.getRandomIp());	
		}
		
		long end = System.currentTimeMillis();
		long elapsed = end - start;
		System.out.println(counter + " 건의 ip변환 소요시간: " + elapsed + "ms");
	}
	
	@Test
	public void _날짜연산테스트() {		
		System.out.println(CommonUtils.getISO8601FormatDate(ISO8601.DATE_EXTENDED_PATTERN, CommonUtils.getTimeAgo(15)));
	}
}
		