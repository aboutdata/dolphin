package com.sabsari.dolphin.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.model.constants.PropertyValues;
import com.sabsari.dolphin.core.validator.annotation.Name;

import lombok.Data;

@Data
public class RegistApplicationRequest {

	@JsonProperty(value=PropertyValues.APP_NAME, required=true)
	@Name(mandatory=true)
	private String appName;
}
