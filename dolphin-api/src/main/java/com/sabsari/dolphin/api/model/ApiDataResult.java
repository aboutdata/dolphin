package com.sabsari.dolphin.api.model;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.core.common.code.ResponseCode;

public class ApiDataResult extends ApiResult {
	
	@JsonProperty(value="result")
	@Getter
	private AbstractResultModel result;
		
	public ApiDataResult(AbstractResultModel result, ResponseCode responseCode, String message) {
		super(responseCode, message);
		this.result = result;
	}
}
