package com.sabsari.dolphin.core.auth.domain.code;

public enum Role {
	ADMIN("ROLE_ADMIN", 0),
	CLIENT("ROLE_CLIENT", 3),
	USER("ROLE_USER", 5),
	TOKEN_CONSUMER("ROLE_TOKEN_CONSUMER", 98),
	ANONYMOUS("ROLE_ANONYMOUS", 99);
		
	private String role;
	private int level;
	
	Role(String role, int level){
		this.role = role;
		this.level = level;
	}

	public String getRole() {
		return role;
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean hasPermission(Role required) {		
		if (this.getLevel() <= required.getLevel())
			return true;
		else
			return false;
	}

	public static Role search(String role) {
		for (Role r : Role.values()) {
			if(r.getRole().equals(role))
				return r;
		}
		return null;
	}
	
	public static Role search(int level) {
		for (Role type : Role.values()) {
			if(type.getLevel() == level)
				return type;
		}
		return null;
	}
	
	public static boolean isValidRole(String role) {
		if (search(role) == null) {
			return false;
		}
		else {
			return true;
		}
	}
}
