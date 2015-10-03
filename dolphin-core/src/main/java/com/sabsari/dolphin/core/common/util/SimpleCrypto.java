package com.sabsari.dolphin.core.common.util;

import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.codec.binary.Base64;

import com.sabsari.dolphin.core.common.CommonConstants;

public class SimpleCrypto {
	
	public static long getCRC32(String value) {
		if (StringUtils.isEmpty(value)) {
			throw new IllegalArgumentException("Parameter 'value' must not be empty.");
		}
		
		Checksum crc = new CRC32();	
    	byte buffer[];
		try {
			buffer = value.getBytes(CommonConstants.CHARSET_UTF8);
			crc.update(buffer, 0, buffer.length);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Failed to get crc32 value.", e);
		}
    	
	    return crc.getValue();
	}
	
	public static String base64Encode(String input) {
		if (StringUtils.isEmpty(input)) {
			throw new IllegalArgumentException("Parameter 'input' must not be empty.");
		}
		
		byte buffer[];
		String result;		
		try {
			buffer = input.getBytes(CommonConstants.CHARSET_UTF8);
			result = Base64.encodeBase64String(buffer);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Failed to encode base64.", e);
		}
		
		return result;
	}
	
	public static String base64Decode(String input) {
		if (StringUtils.isEmpty(input)) {
			throw new IllegalArgumentException("Parameter 'input' must not be empty.");
		}
		
		byte buffer[];
		String result;		
		
		try {
			buffer = Base64.decodeBase64(input);
			result = new String(buffer, CommonConstants.CHARSET_UTF8);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Failed to decode base64.", e);
		}
		
		return result;
	}
}
