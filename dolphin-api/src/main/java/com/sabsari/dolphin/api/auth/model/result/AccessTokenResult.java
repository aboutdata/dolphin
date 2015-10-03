package com.sabsari.dolphin.api.auth.model.result;

public class AccessTokenResult extends AbstractTokenResult {

	public AccessTokenResult(String accessToken, int tokenLifeTime) {
		super(accessToken, tokenLifeTime);
	}
}
