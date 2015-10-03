package com.sabsari.dolphin.api.model.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.model.AbstractResultModel;
import com.sabsari.dolphin.api.model.constants.PropertyValues;

public class CheckEmailDuplicationResult extends AbstractResultModel {
	
	@JsonProperty(value=PropertyValues.IS_AVAILABLE)	
	private boolean isAvailable;
	
	public CheckEmailDuplicationResult() {
		
	}
	
	public CheckEmailDuplicationResult(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
}
