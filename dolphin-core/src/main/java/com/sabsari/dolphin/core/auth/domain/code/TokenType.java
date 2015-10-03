package com.sabsari.dolphin.core.auth.domain.code;

public enum TokenType {
	BEARER("Bearer");
	
//	@Deprecated
//	MAC("mac");

	private String type;
	
	TokenType(String type){
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
	
	public static TokenType search(String type) {
		for (TokenType tt : TokenType.values()) {
			if(tt.getType().equals(type))
				return tt;
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
