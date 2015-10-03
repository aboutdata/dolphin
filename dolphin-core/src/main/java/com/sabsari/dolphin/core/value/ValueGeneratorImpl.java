package com.sabsari.dolphin.core.value;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class ValueGeneratorImpl implements ValueGenerator {
	
	private final static int EMAIL_VERIFICATION_VALUE_LENGTH = 64;
	private final static int CLIENT_SECRET_LENGTH = 32;
    private final static int ACCESS_TOKEN_LENGTH = 24;
    private final static int REFRESH_TOKEN_LENGTH = 24;
    private final static int RANDOM_PASSWORD_LENGTH = 12;
	
	private final static char[] RANDOM_POOL = new char[] 
		   {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
		   ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
		   ,'0','1','2','3','4','5','6','7','8','9'/* ,'+','/' */}; // 62개의 pool: '+', '/' 두개 문제 제외
		
	@Override
	public String generateGroupKey() {
		return UUID.randomUUID().toString();
	}

	@Override
	public String generateClientId() {
		return UUID.randomUUID().toString();
	}

	@Override
	public String generateUserKey() {
		return UUID.randomUUID().toString();
	}

	@Override
	public String generateEmailVerificationValue() {
		return getRandomString(EMAIL_VERIFICATION_VALUE_LENGTH);
	}

	@Override
	public String generateAccessToken() {
		return getRandomString(ACCESS_TOKEN_LENGTH);
	}
	
	@Override
	public String generateRefreshToken() {
		return getRandomString(REFRESH_TOKEN_LENGTH);
	}

	@Override
	public String generateClientSecret() {
		return getRandomString(CLIENT_SECRET_LENGTH);
	}

	@Override
	public String generateRandomPassword() {
		return getRandomString(RANDOM_PASSWORD_LENGTH);
	}
	
	private String getRandomString(int length) {
		if (length <= 0)
			throw new IllegalArgumentException("The parameter 'length' should be a positive integer");
		
		char[] result = new char[length];
		int pick = 0;
		Random random = new Random();
	
		for (int i = 0 ; i < length ; i++) {
			pick = random.nextInt(RANDOM_POOL.length);
			result[i] = RANDOM_POOL[pick];
		}
	
		return String.valueOf(result);
	}
}
