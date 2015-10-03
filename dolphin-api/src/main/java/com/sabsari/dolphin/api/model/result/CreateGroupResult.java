package com.sabsari.dolphin.api.model.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.model.AbstractResultModel;
import com.sabsari.dolphin.api.model.constants.PropertyValues;

public class CreateGroupResult extends AbstractResultModel {

	@JsonProperty(value=PropertyValues.GROUP_KEY)
	private String groupKey;
	
	public CreateGroupResult() {}
	
	public CreateGroupResult(String groupKey) {
		this.groupKey = groupKey;
	}
}
