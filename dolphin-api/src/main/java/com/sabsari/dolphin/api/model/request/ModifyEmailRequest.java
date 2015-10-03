package com.sabsari.dolphin.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.model.constants.PropertyValues;
import com.sabsari.dolphin.core.validator.annotation.Email;

import lombok.Data;
import lombok.ToString;

/**
 * Created by jungkeun.park@sk.com on 2014. 7. 3..
 */
@Data
@ToString
public class ModifyEmailRequest {

	@JsonProperty(value=PropertyValues.NEW_EMAIL_ID, required=true)
	@Email(mandatory=true)
	private String newEmailId;
}
