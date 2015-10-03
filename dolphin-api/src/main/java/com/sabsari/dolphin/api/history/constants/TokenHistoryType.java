package com.sabsari.dolphin.api.history.constants;

import com.sabsari.dolphin.core.history.domain.code.TokenHistoryReason;

public enum TokenHistoryType {
	ISSUE("issue", TokenHistoryReason.ISSUE_TOKEN),				// 토큰 발급
	REFRESH("refresh", TokenHistoryReason.REFRESH_TOKEN),			// 토큰 리프레쉬
	REVOKE("revoke", TokenHistoryReason.REVOKE_TOKEN),			// 토큰 만료
	ALL("all", null);
	
	private String value;
	private TokenHistoryReason reason;

	TokenHistoryType(String value, TokenHistoryReason reason) {
		this.value = value;
		this.reason = reason;
	}

	public String getValue() {
		return this.value;
	}
	
	public TokenHistoryReason getReason() {
		return this.reason;
	}

	public static TokenHistoryType search(String value) {
		for (TokenHistoryType type : TokenHistoryType.values()) {
			if (type.getValue().equals(value))
				return type;
		}
		return null;
	}
	
	public static boolean isValidType(String value) {
		if (search(value) == null) {
			return false;
		}
		else {
			return true;
		}
	}
}
