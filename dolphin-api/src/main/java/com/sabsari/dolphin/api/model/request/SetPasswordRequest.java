package com.sabsari.dolphin.api.model.request;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.model.constants.PropertyValues;
import com.sabsari.dolphin.core.validator.annotation.Password;

@Data
public class SetPasswordRequest {	
	@JsonProperty(value=PropertyValues.NEW_PASSWORD, required=true)
	@Password(mandatory=true)
	private String newPassword;
}
