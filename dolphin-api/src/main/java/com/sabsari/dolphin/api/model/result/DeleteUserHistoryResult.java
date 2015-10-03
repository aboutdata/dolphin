package com.sabsari.dolphin.api.model.result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.model.AbstractResultModel;
import com.sabsari.dolphin.api.model.constants.PropertyValues;
import com.sabsari.dolphin.core.history.domain.DeleteUserHistory;

public class DeleteUserHistoryResult extends AbstractResultModel {

	@JsonProperty(value=PropertyValues.HISTORY_ENTRIES)
	private List<DeleteUserData> entries;
	
	public DeleteUserHistoryResult() {
		
	}
	
	public DeleteUserHistoryResult(List<DeleteUserHistory> deleteUserHistory) {
		entries = new ArrayList<DeleteUserData>();
		
		for (DeleteUserHistory h : deleteUserHistory) {
			entries.add(new DeleteUserData(h));
		}
	}	
	
	public static class DeleteUserData {
		
		@JsonProperty(value=PropertyValues.EMAIL_ID)
		private String emailId;
		
		@JsonProperty(value=PropertyValues.USER_KEY)
		private String userKey;	
		
		@JsonProperty(value=PropertyValues.FIRST_NAME)
		private String firstName;
		
		@JsonProperty(value=PropertyValues.REGIST_DATE)
		private Date registDate;
		
		public DeleteUserData() {
			
		}
		
		public DeleteUserData(DeleteUserHistory deleteUserHistory) {
			this.emailId = deleteUserHistory.getEmailId();
			this.userKey = deleteUserHistory.getUserKey();
			this.firstName = deleteUserHistory.getFirstName();
			this.registDate = deleteUserHistory.getRegistDate();
		}
	}
}
