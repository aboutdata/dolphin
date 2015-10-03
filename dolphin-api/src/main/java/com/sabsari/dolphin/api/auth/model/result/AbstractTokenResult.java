package com.sabsari.dolphin.api.auth.model.result;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.auth.model.constants.PropertyValues;
import com.sabsari.dolphin.core.auth.domain.code.TokenType;

@Data
public abstract class AbstractTokenResult {

	@JsonProperty(value=PropertyValues.ACCESS_TOKEN, required=true)
	private String accessToken;
	
	@JsonProperty(value=PropertyValues.TOKEN_TYPE, required=true)
	private String tokenType;
	
	@JsonProperty(value=PropertyValues.EXPIRES_IN, required=true)
	private int expiresId;
		
	public AbstractTokenResult(String accessToken, int tokenLifeTime) {
		this(accessToken, TokenType.BEARER, tokenLifeTime);
	}
	
	public AbstractTokenResult(String accessToken, TokenType tokenType, int tokenLifeTime) {
		this.accessToken = accessToken;
		this.tokenType = tokenType.getType();
		this.expiresId = tokenLifeTime;
	}
}
