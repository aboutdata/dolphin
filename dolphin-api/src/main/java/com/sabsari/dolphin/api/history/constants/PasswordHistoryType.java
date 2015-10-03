package com.sabsari.dolphin.api.history.constants;

import com.sabsari.dolphin.core.history.domain.code.AuthenticationHistoryReason;

public enum PasswordHistoryType {
	RESET("reset", AuthenticationHistoryReason.RESET_PASSWORD),
	SET("set", AuthenticationHistoryReason.RESET_PASSWORD),
	MODIFY("modify", AuthenticationHistoryReason.RESET_PASSWORD),
	ALL("all", null);
	
	private String value;
	private AuthenticationHistoryReason reason;

	PasswordHistoryType(String value, AuthenticationHistoryReason reason) {
		this.value = value;
		this.reason = reason;
	}

	public String getValue() {
		return this.value;
	}
	
	public AuthenticationHistoryReason getReason() {
		return this.reason;
	}

	public static PasswordHistoryType search(String value) {
		for (PasswordHistoryType type : PasswordHistoryType.values()) {
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
