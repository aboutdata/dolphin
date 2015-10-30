package com.sabsari.dolphin.utils.crypto;

import com.sabsari.dolphin.utils.constants.CommonConstants;

public final class AES256 extends AES {

	private static AES256 instance = null;
	
	public static AES256 getInstance() {
		if (instance == null) {
			instance = new AES256();
		}
		
		return instance;
	}
	
	private AES256() {
		super(CommonConstants.AES256_KEY, CommonConstants.AES256_IV);
	}

	@Override
	public byte[] generateKey() {
		return generateAESKey(AESKeySize.KEY_256);
	}
}
