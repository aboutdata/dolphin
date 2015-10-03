package com.sabsari.dolphin.api.model.result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.model.AbstractResultModel;
import com.sabsari.dolphin.api.model.constants.PropertyValues;
import com.sabsari.dolphin.core.history.domain.UserHistory;

public class ProfileHistoryResult extends AbstractResultModel {

	@JsonProperty(value=PropertyValues.HISTORY_ENTRIES)
	private List<ProfileHistoryData> entries;
	
	public ProfileHistoryResult() {
		
	}
	
	public ProfileHistoryResult(List<UserHistory> userHistory) {
		entries = new ArrayList<ProfileHistoryData>();
		for (UserHistory h : userHistory) {
			entries.add(new ProfileHistoryData(h));
		}
	}
	
	public static class ProfileHistoryData {
		
		@JsonProperty(value=PropertyValues.REASON)
		private String reason;
		
		@JsonProperty(value=PropertyValues.REGIST_DATE)
		private Date registDate;
		
		public ProfileHistoryData() {
			
		}
		
		public ProfileHistoryData(UserHistory userHistory) {
			this.reason = userHistory.getReasonCode().getDesc();
			this.registDate = userHistory.getRegistDate();
		}
	}
}
