package com.sabsari.dolphin.api.model.result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.model.AbstractResultModel;
import com.sabsari.dolphin.api.model.constants.PropertyValues;
import com.sabsari.dolphin.core.history.domain.TokenHistory;

public class TokenHistoryResult extends AbstractResultModel {

	@JsonProperty(value=PropertyValues.HISTORY_ENTRIES)
	private List<TokenHistoryData> entries;
	
	public TokenHistoryResult() {
		
	}
	
	public TokenHistoryResult(List<TokenHistory> tokenHistory) {
		entries = new ArrayList<TokenHistoryData>();
		for (TokenHistory h : tokenHistory) {
			entries.add(new TokenHistoryData(h));
		}
	}
	
	public static class TokenHistoryData {
		
		@JsonProperty(value=PropertyValues.REASON)
		private String reason;
		
		@JsonProperty(value=PropertyValues.REGIST_DATE)
		private Date registDate;
		
		public TokenHistoryData() {
			
		}
		
		public TokenHistoryData(TokenHistory tokenHistory) {
			this.reason = tokenHistory.getReasonCode().getDesc();
			this.registDate = tokenHistory.getRegistDate();
		}
	}
}
