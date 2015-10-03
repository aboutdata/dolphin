package com.sabsari.dolphin.core.auth.exception.status401;

import com.sabsari.dolphin.core.auth.domain.code.HttpAuthenticationScheme;

public class ClientSecretVerificationFailException extends Status401Excepiton {
	
	private static final long serialVersionUID = -4929757536114068302L;
	
	public ClientSecretVerificationFailException(HttpAuthenticationScheme scheme) {
		super(scheme);
	}
}
