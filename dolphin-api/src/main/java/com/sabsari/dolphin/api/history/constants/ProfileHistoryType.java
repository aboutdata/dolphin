package com.sabsari.dolphin.api.history.constants;

import com.sabsari.dolphin.core.history.domain.code.UserHistoryReason;

public enum ProfileHistoryType {
	EMAIL("email", UserHistoryReason.MODIFY_EMAIL),
	PROFILE("profile", UserHistoryReason.MODIFY_PROFILE),
	ALL("all", null);
	
	private String value;
	private UserHistoryReason reason;

	ProfileHistoryType(String value, UserHistoryReason reason) {
		this.value = value;
		this.reason = reason;
	}

	public String getValue() {
		return this.value;
	}
	
	public UserHistoryReason getReason() {
		return this.reason;
	}

	public static ProfileHistoryType search(String value) {
		for (ProfileHistoryType type : ProfileHistoryType.values()) {
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
