package com.sabsari.dolphin.utils.crypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.sabsari.dolphin.utils.constants.CommonConstants;

public abstract class AES {

	protected enum AESKeySize {
		KEY_128(128),
		KEY_256(256);
		
		private int keySize;
		
		AESKeySize(int keySize) {
			this.keySize = keySize;
		}
		
		public int getKeySize() {
			return this.keySize;
		}
	}
	
	private final String CRYPTO_ALGORITHM = "AES";
	private final String TRANSFORMATION	= "AES/CBC/PKCS5Padding";

	private SecretKey KEY = null;
	private IvParameterSpec IV = null;				// iv는 128 bit
	
	public abstract byte[] generateKey();
	
	// key와 iv는 base64 encode 되어 있는 String 형태
	protected AES(String key, String iv) {
		this.KEY = new SecretKeySpec(Base64.decodeBase64(key), CRYPTO_ALGORITHM);
		this.IV = new IvParameterSpec(Base64.decodeBase64(iv));
	}
	
	public String encrypt(String input) {
		return Base64.encodeBase64String(encrypt(input, KEY, IV)).trim();
	}

	public String decrypt(String input) {
		return decrypt(Base64.decodeBase64(input), KEY, IV);
	}
		
	protected byte[] generateAESKey(AESKeySize keySize) {
		KeyGenerator kgen;
		SecretKey key;
		
		try {
			kgen = KeyGenerator.getInstance(CRYPTO_ALGORITHM);
			kgen.init(keySize.getKeySize());
			key = kgen.generateKey();
		}
		catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
		
		return key.getEncoded();
	}
	
	private byte[] encrypt(String input, SecretKey key, IvParameterSpec iv) {
		if (StringUtils.isEmpty(input))
			throw new IllegalArgumentException();
		
		byte[] encrypted = null;
		Cipher c;
		
		try {
			c = Cipher.getInstance(TRANSFORMATION);
			c.init(Cipher.ENCRYPT_MODE, key, iv);
			encrypted = c.doFinal(input.getBytes(CommonConstants.DEFAULT_CHARSET));
		}
		catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
		catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
		catch (NoSuchPaddingException ex) {
			throw new RuntimeException(ex);	
		}
		catch (InvalidKeyException ex) {
			throw new RuntimeException(ex);
		}
		catch (InvalidAlgorithmParameterException ex) {
			throw new RuntimeException(ex);
		}
		catch (IllegalBlockSizeException ex) {
			throw new RuntimeException(ex);
		}
		catch (BadPaddingException ex) {
			throw new RuntimeException(ex);
		}
			   
		return encrypted;
	}
	
	private String decrypt(byte[] input, SecretKey key, IvParameterSpec iv) {
		if (input == null || input.length <= 0)
			throw new IllegalArgumentException();
		
		Cipher c;
		String result = null;
	 
		try {
			c = Cipher.getInstance(TRANSFORMATION);
			c.init(Cipher.DECRYPT_MODE, key, iv);
			result = new String(c.doFinal(input), CommonConstants.DEFAULT_CHARSET);
		}
		catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
		catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
		catch (NoSuchPaddingException ex) {
			throw new RuntimeException(ex);	
		}
		catch (InvalidKeyException ex) {
			throw new RuntimeException(ex);
		}
		catch (InvalidAlgorithmParameterException ex) {
			throw new RuntimeException(ex);
		}
		catch (IllegalBlockSizeException ex) {
			throw new RuntimeException(ex);
		}
		catch (BadPaddingException ex) {
			throw new RuntimeException(ex);
		}

		return result;
	}
}
