package com.sabsari.dolphin.api.model;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.core.common.code.ResponseCode;

public class ApiResult {

	@JsonProperty(value="code")
	@Getter
	protected String code;
	
	@JsonProperty(value="message")
	@Getter
	protected String message;
		
	public ApiResult(ResponseCode responseCode, String message) {
		this.code = responseCode.getCode();
		this.message = message;
	}
}
