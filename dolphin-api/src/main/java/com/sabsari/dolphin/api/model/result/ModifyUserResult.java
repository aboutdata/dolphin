package com.sabsari.dolphin.api.model.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.model.AbstractResultModel;
import com.sabsari.dolphin.api.model.constants.PropertyValues;
import com.sabsari.dolphin.core.member.domain.User;

import java.util.Date;

public class ModifyUserResult extends AbstractResultModel {

	@JsonProperty(value=PropertyValues.USER_KEY)
	private String userKey;
	
	@JsonProperty(value=PropertyValues.EMAIL_ID)
	private String emailId;
	
	@JsonProperty(value=PropertyValues.FIRST_NAME)
	private String firstName;
	
	@JsonProperty(value=PropertyValues.LAST_NAME)
	private String lastName;
	
	@JsonProperty(value=PropertyValues.BIRTHDAY)
	private String birthday;
	
	@JsonProperty(value=PropertyValues.PHONE)
	private String phone;
	
	@JsonProperty(value=PropertyValues.GENDER)
	private String gender;	
	
	@JsonProperty(value=PropertyValues.USER_STATUS)
	private String userStatus;
	
	@JsonProperty(value=PropertyValues.REGIST_DATE)
	private Date registDate;
	
	@JsonProperty(value=PropertyValues.MODIFY_DATE)
	private Date modifyDate;
	
	public ModifyUserResult() {}

	public ModifyUserResult(User user) {		
		this.userKey = user.getUserKey();
		this.emailId = user.getEmailId();
		this.firstName = user.getProfile().getFirstName();
        this.lastName = user.getProfile().getLastName();
        this.birthday = user.getProfile().getBirthday();
        this.gender = user.getProfile().getGender();
        this.phone = user.getProfile().getPhone();
        this.userStatus = user.getAuthentication().getAuthStatusCode().getCode();
		this.modifyDate = user.getModifyDate();
		this.registDate = user.getRegistDate();
	}
}
