package com.sabsari.dolphin.core.history.domain.code;

public enum TokenHistoryReason {
	ISSUE_TOKEN("H51", "issue token"),				// 토큰 발급
	REFRESH_TOKEN("H52", "refresh token"),			// 토큰 리프레쉬
	REVOKE_TOKEN("H53", "revoke token");			// 토큰 만료
	
	private String code;
	private String desc;

	TokenHistoryReason(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static TokenHistoryReason search(String code) {
		for (TokenHistoryReason type : TokenHistoryReason.values()) {
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
