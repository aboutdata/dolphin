package com.sabsari.dolphin.api.model.result;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.model.AbstractResultModel;
import com.sabsari.dolphin.api.model.constants.PropertyValues;
import com.sabsari.dolphin.core.member.domain.Application;

public class CreateApplicationResult extends AbstractResultModel {

	@JsonProperty(value=PropertyValues.CLIENT_ID)
	private String clientId;
	
	@JsonProperty(value=PropertyValues.CLIENT_SECRET)
	private String clientSecret;
	
	@JsonProperty(value=PropertyValues.GROUP_KEY)
	private String groupKey;
	
	@JsonProperty(value=PropertyValues.APP_NAME)
	private String appName;
	
	@JsonProperty(value=PropertyValues.REGIST_DATE)
	private Date registDate;
	
	@JsonProperty(value=PropertyValues.MODIFY_DATE)
	private Date modifyDate;
	
	public CreateApplicationResult() {}
	
	public CreateApplicationResult(Application application) {
		this.clientId = application.getClientId();
		this.clientSecret = application.getClientSecret();
		this.groupKey = application.getGroupKey();
		this.appName = application.getAppName();
		this.modifyDate = application.getModifyDate();
		this.registDate = application.getRegistDate();
	}
}
