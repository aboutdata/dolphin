package com.sabsari.dolphin.core.history.domain.code;

public enum AuthenticationHistoryReason {	
	MODIFY_PASSWORD("H31", "modify password"),				// 비번 변경
	RESET_PASSWORD("H32", "reset password"),				// 비번 초기화
	SET_PASSWORD("H33", "set password"),					// 비번 초기화
	MODIFY_AUTH_STATUS("H34", "modify authentication status");	// 상태 변경
	
	private String code;
	private String desc;

	AuthenticationHistoryReason(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static AuthenticationHistoryReason search(String code) {
		for (AuthenticationHistoryReason type : AuthenticationHistoryReason.values()) {
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
