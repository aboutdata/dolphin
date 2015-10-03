package com.sabsari.dolphin.core.auth.domain.code;

public enum TokenTypeHint {
	ACCESS_TOKEN("access_token"),
	REFRESH_TOKEN("refresh_token");
	
	private String hint;
	
	TokenTypeHint(String hint){
		this.hint = hint;
	}

	public String getHint() {
		return this.hint;
	}
	
	public static TokenTypeHint search(String hint) {
		for (TokenTypeHint typeHint : TokenTypeHint.values()) {
			if(typeHint.getHint().equals(hint))
				return typeHint;
		}
		return null;
	}
	
	public static boolean isValid(String hint) {
		if (search(hint) == null) {
			return false;
		}
		else {
			return true;
		}
	}
}
