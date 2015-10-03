package com.sabsari.dolphin.core.member.domain.code;

public enum YesNo {
	YES("Y"), 
	NO("N");
	
	private String code;
	
	YesNo(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static YesNo search(String code) {
		for (YesNo type : YesNo.values()) {
			if (type.getCode().equals(code))
				return type;
		}
		return null;
	}
	
	public static boolean isValidCode(String code) {
		if (search(code) == null) {
			return false;
		}
		else {
			return true;
		}
	}
}
