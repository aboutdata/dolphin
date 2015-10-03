package com.sabsari.dolphin.api.auth.model;

import com.sabsari.dolphin.core.auth.domain.code.Role;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiInfo {
	private Role level;
	private String requestMappingPattern;
	private boolean hasUserKeyInPath;
	
	public boolean hasUserKeyInPath() {
		return this.hasUserKeyInPath;
	}
}
