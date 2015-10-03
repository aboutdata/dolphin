package com.sabsari.dolphin.core.history.domain.code;

public enum UserHistoryReason {
	MODIFY_EMAIL("H01", "modify email"),					// 이메일 변경
	MODIFY_PROFILE("H02", "modify profile");				// 프로파일 변경
	
	private String code;
	private String desc;

	UserHistoryReason(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static UserHistoryReason search(String code) {
		for (UserHistoryReason type : UserHistoryReason.values()) {
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
