package com.sabsari.dolphin.utils.crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;

import com.sabsari.dolphin.utils.constants.CommonConstants;

import org.apache.commons.codec.binary.Base64;

/**
 * SHA256
 * 
 * @author	: sabsari
 * @Date	: 2013. 11. 27.
 */
public final class SHA256 {	
		
	private static final int DIGEST_COUNT = 1024;
	
	private static final String HASH_ALGORITHM = "SHA-256";
			
	public static String getHashString(String input) {
		return convertByteToString(getHash(input));
	}
	
	public static String getSaltedHashString(String input, String salt) {
		return convertByteToString(getHashWithSalt(DIGEST_COUNT, input, salt));
	}
	
	public static String getBase64HashString(String input) {
		return Base64.encodeBase64String(getHash(input)).trim();
	}
	
	public static String getBase64SaltedHashString(String input, String salt) {
		return Base64.encodeBase64String(getHashWithSalt(DIGEST_COUNT, input, salt)).trim();
	}
	
	public static String convertByteToString(byte[] input) {
		if (input == null || input.length <= 0)
			throw new IllegalArgumentException();
		
		StringBuffer hexString = new StringBuffer();
		
		for (int i = 0; i < input.length; i++) {
            String hex = Integer.toHexString(0xff & input[i]);
            if(hex.length() == 1)
         	   hexString.append('0');
            hexString.append(hex);
		}
		
		return hexString.toString();
	}
	
	public static byte[] getHash(String input) {
		if (StringUtils.isEmpty(input))
			throw new IllegalArgumentException();		
		
		MessageDigest digest = null;
		byte[] hash = null;
	       
		try {
			digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.reset();
			hash = digest.digest(input.getBytes(CommonConstants.DEFAULT_CHARSET));    	   
		}
		catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
		catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
	       
		return hash;
	}
	
	public static byte[] getHashWithSalt(int iterationCount, String input, String salt) {
		if (iterationCount < 0 || StringUtils.isEmpty(input) || StringUtils.isEmpty(salt))
			throw new IllegalArgumentException();
			
		MessageDigest digest = null;
		byte[] hash = null;
	       
		try {
			digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.reset();
			digest.update(salt.getBytes(CommonConstants.DEFAULT_CHARSET));
			hash = digest.digest(input.getBytes(CommonConstants.DEFAULT_CHARSET));
			
			for (int i = 0 ; i < iterationCount ; i++) {
				digest.reset();
				hash = digest.digest(hash);
			}			
		}
		catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
		catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
		
		return hash;
	}
}
