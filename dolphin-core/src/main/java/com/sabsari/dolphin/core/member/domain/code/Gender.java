package com.sabsari.dolphin.core.member.domain.code;

public enum Gender {
	MALE("M"),
	FEMALE("F");
	
	private String code;
	
	Gender(String code){
		this.code = code;		
	}

	public String getCode() {
		return code;
	}

	public static Gender search(String code) {
		for (Gender type : Gender.values()) {
			if(type.getCode().equals(code))
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
