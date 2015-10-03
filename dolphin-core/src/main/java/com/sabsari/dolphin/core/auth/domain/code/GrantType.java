package com.sabsari.dolphin.core.auth.domain.code;

public enum GrantType {
	CLIENT_CREDENTIALS("client_credentials", 1),
	PASSWORD("password", 2),		
	REFRESH_TOKEN("refresh_token", 3);
		
	private String type;
	private int code;
	
	GrantType(String type, int code){
		this.type = type;
		this.code = code;
	}

	public String getType() {
		return this.type;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public static GrantType search(String type) {
		for (GrantType gt : GrantType.values()) {
			if(gt.getType().equals(type))
				return gt;
		}
		return null;
	}
	
	public static GrantType search(int code) {
		for (GrantType type : GrantType.values()) {
			if(type.getCode() == code)
				return type;
		}
		return null;
	}
	
	public static boolean isValidType(String type) {
		if (search(type) == null) {
			return false;
		}
		else {
			return true;
		}
	}
}
