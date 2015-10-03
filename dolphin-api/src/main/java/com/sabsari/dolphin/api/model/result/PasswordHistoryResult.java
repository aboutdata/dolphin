package com.sabsari.dolphin.api.model.result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.model.AbstractResultModel;
import com.sabsari.dolphin.api.model.constants.PropertyValues;
import com.sabsari.dolphin.core.history.domain.AuthenticationHistory;

public class PasswordHistoryResult extends AbstractResultModel {

	@JsonProperty(value=PropertyValues.HISTORY_ENTRIES)
	private List<PasswordHistoryData> entries;
	
	public PasswordHistoryResult() {
		
	}
	
	public PasswordHistoryResult(List<AuthenticationHistory> authenticationHistory) {
		entries = new ArrayList<PasswordHistoryData>();
		for (AuthenticationHistory h : authenticationHistory) {
			entries.add(new PasswordHistoryData(h));
		}
	}
	
	public static class PasswordHistoryData {
		
		@JsonProperty(value=PropertyValues.REASON)
		private String reason;
		
		@JsonProperty(value=PropertyValues.REGIST_DATE)
		private Date registDate;
		
		public PasswordHistoryData() {
			
		}
		
		public PasswordHistoryData(AuthenticationHistory authenticationHistory) {
			this.reason = authenticationHistory.getReasonCode().getDesc();
			this.registDate = authenticationHistory.getRegistDate();
		}
	}
}
