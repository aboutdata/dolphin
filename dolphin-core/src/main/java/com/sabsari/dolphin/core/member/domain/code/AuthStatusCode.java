package com.sabsari.dolphin.core.member.domain.code;

public enum AuthStatusCode {
	NORMAL("00", "Normal"), 							// 정상
	TEMP_PASSWORD("01", "Temporary password"),			// 임시비번 상태
	LOCKED("05", "Login locked"),						// 연속 인증실패 잠금
	LOGIN_IMPOSSIBLE("99", "Login impossible");			// 로그인 불가 상태
	
	private String code;
	private String desc;

	AuthStatusCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static AuthStatusCode search(String code) {
		for (AuthStatusCode type : AuthStatusCode.values()) {
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
