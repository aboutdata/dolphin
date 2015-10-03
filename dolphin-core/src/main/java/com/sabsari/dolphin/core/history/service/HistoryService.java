package com.sabsari.dolphin.core.history.service;

import java.util.List;

import com.sabsari.dolphin.core.history.domain.AuthenticationHistory;
import com.sabsari.dolphin.core.history.domain.DeleteUserHistory;
import com.sabsari.dolphin.core.history.domain.TokenHistory;
import com.sabsari.dolphin.core.history.domain.UserHistory;
import com.sabsari.dolphin.core.history.domain.code.AuthenticationHistoryReason;
import com.sabsari.dolphin.core.history.domain.code.TokenHistoryReason;
import com.sabsari.dolphin.core.history.domain.code.UserHistoryReason;

public interface HistoryService {
	public List<UserHistory> getUserHistory(String userKey, UserHistoryReason reasonCode, int pageNumber, int pageSize);
	
	public List<AuthenticationHistory> getAuthenticationHistory(String userKey, AuthenticationHistoryReason reasonCode, int pageNumber, int pageSize);
	
	public List<TokenHistory> getTokenHistory(String owner, TokenHistoryReason reasonCode, int pageNumber, int pageSize);
	
	public DeleteUserHistory getDeleteUserHistoryByUserKey(String clientId, String userKey);
	public List<DeleteUserHistory> getDeleteUserHistoryByEmail(String clientId, String emailId, int pageNumber, int pageSize);	
	public List<DeleteUserHistory> getDeleteUserHistory(String clientId, int pageNumber, int pageSize);
	
	public void clearHistory();
	
	public int deleteDeleteUserHistory();
	public int deleteUserHistory();
	public int deleteTokenHistory();
	public int deleteAuthenticationHistory();	
}
