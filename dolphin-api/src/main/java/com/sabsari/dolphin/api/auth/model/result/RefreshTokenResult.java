package com.sabsari.dolphin.api.auth.model.result;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.auth.model.constants.PropertyValues;

public class RefreshTokenResult extends AbstractTokenResult {

	@JsonProperty(value=PropertyValues.REFRESH_TOKEN)
	@Getter @Setter private String refreshToken;
	
	public RefreshTokenResult(String accessToken, int tokenLifeTime, String refreshToken) {
		super(accessToken, tokenLifeTime);
		this.refreshToken = refreshToken;
	}
}
