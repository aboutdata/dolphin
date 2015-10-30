package com.sabsari.dolphin.utils.crypto;

import com.sabsari.dolphin.utils.constants.CommonConstants;

public final class AES128 extends AES {
	
	private static AES128 instance = null;
	
	public static AES128 getInstance() {
		if (instance == null) {
			instance = new AES128();
		}
		
		return instance;
	}
	
	private AES128() {
		super(CommonConstants.AES128_KEY, CommonConstants.AES128_IV);
	}

	@Override
	public byte[] generateKey() {
		return generateAESKey(AESKeySize.KEY_128);
	}
}
