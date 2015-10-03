package com.sabsari.dolphin.api.model.request;

import lombok.Data;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.model.constants.PropertyValues;
import com.sabsari.dolphin.core.validator.annotation.Birthday;
import com.sabsari.dolphin.core.validator.annotation.Email;
import com.sabsari.dolphin.core.validator.annotation.Gender;
import com.sabsari.dolphin.core.validator.annotation.Name;
import com.sabsari.dolphin.core.validator.annotation.Password;
import com.sabsari.dolphin.core.validator.annotation.PhoneNo;

@Data
@ToString
public class CreateUserRequest {
	@JsonProperty(value=PropertyValues.EMAIL_ID, required=true)
	@Email(mandatory=true)
	private String emailId;
	
	@JsonProperty(value=PropertyValues.PASSWORD, required=true)
	@Password(mandatory=true)
	private String password;
	
	@JsonProperty(value=PropertyValues.FIRST_NAME)
	@Name
	private String firstName;

	@JsonProperty(value=PropertyValues.LAST_NAME)
	@Name
	private String lastName;

	@JsonProperty(value=PropertyValues.BIRTHDAY)
	@Birthday
	private String birthday;
	
	@JsonProperty(value=PropertyValues.PHONE)
	@PhoneNo
	private String phone;
	
	@JsonProperty(value=PropertyValues.GENDER)
	@Gender
	private String gender;
	
	public com.sabsari.dolphin.core.member.domain.code.Gender getGender() {
		return com.sabsari.dolphin.core.member.domain.code.Gender.search(this.gender);
	}
}
