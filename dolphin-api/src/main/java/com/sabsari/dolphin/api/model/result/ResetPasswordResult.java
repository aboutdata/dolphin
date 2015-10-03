package com.sabsari.dolphin.api.model.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.model.AbstractResultModel;
import com.sabsari.dolphin.api.model.constants.PropertyValues;

public class ResetPasswordResult extends AbstractResultModel {
	
	@JsonProperty(value=PropertyValues.TEMP_PASSWORD)
	private String tempPassword;
	
	public ResetPasswordResult() {
		
	}
	
	public ResetPasswordResult(String tempPassword) {
		this.tempPassword = tempPassword;
	}
}
