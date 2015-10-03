package com.sabsari.dolphin.api.auth.model.result;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.auth.model.constants.PropertyValues;

@Data
public class ErrorResult {
	
	@JsonProperty(value=PropertyValues.ERROR, required=true)
	private String error;
	
//	@JsonProperty(value=PropertyValues.ERROR_DESCRIPTION)
//	private String error_description;
//	
//	@JsonProperty(value=PropertyValues.ERROR_URI)
//	private String error_uri;
	
	public ErrorResult() {
		
	}
	
	public ErrorResult(String error) {
		this.error = error;
	}
}
