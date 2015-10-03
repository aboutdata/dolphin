package com.sabsari.dolphin.core.common.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sabsari.dolphin.core.common.util.SimpleCrypto;

public class SimpleCryptoTest {
	
	@Test
	public void _Base64EncodeAndDecode() {		

		String input = "i am handsome!";
		String encoded = null;
		String decoded = null;
		
		encoded = SimpleCrypto.base64Encode(input);
		decoded = SimpleCrypto.base64Decode(encoded);
		
		assertEquals(input, decoded);
	}
}
