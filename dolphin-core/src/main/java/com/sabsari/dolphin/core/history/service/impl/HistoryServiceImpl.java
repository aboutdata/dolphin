package com.sabsari.dolphin.core.history.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sabsari.dolphin.core.common.CommonConstants;
import com.sabsari.dolphin.core.common.util.CommonUtils;
import com.sabsari.dolphin.core.exception.business.NotFoundHistoryException;
import com.sabsari.dolphin.core.history.domain.AuthenticationHistory;
import com.sabsari.dolphin.core.history.domain.DeleteUserHistory;
import com.sabsari.dolphin.core.history.domain.TokenHistory;
import com.sabsari.dolphin.core.history.domain.UserHistory;
import com.sabsari.dolphin.core.history.domain.code.AuthenticationHistoryReason;
import com.sabsari.dolphin.core.history.domain.code.TokenHistoryReason;
import com.sabsari.dolphin.core.history.domain.code.UserHistoryReason;
import com.sabsari.dolphin.core.history.repository.AuthenticationHistoryRepository;
import com.sabsari.dolphin.core.history.repository.DeleteUserHistoryRepository;
import com.sabsari.dolphin.core.history.repository.TokenHistoryRepository;
import com.sabsari.dolphin.core.history.repository.UserHistoryRepository;
import com.sabsari.dolphin.core.history.service.HistoryService;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {
	
	private final static Logger logger = LoggerFactory.getLogger(HistoryServiceImpl.class);
	
	private static final String SORT_PROPERTY = "registDate";
	
	@Autowired
	private UserHistoryRepository userHistoryRepository;
	
	@Autowired
	private AuthenticationHistoryRepository authenticationHistoryRepository;
	
	@Autowired
	private TokenHistoryRepository tokenHistoryRepository;
	
	@Autowired
	private DeleteUserHistoryRepository deleteUserHistoryRepository;
		
	@Override
	@Transactional(readOnly=true)
	public List<UserHistory> getUserHistory(String userKey, 
			UserHistoryReason reasonCode, int pageNumber, int pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, SORT_PROPERTY);
		Page<UserHistory> results;
		
		if (reasonCode == null)
			results = userHistoryRepository.findByUserKey(userKey, pageRequest);
		else
			results = userHistoryRepository.findByUserKeyAndReasonCode(userKey, reasonCode.getCode(), pageRequest);			
		
		List<UserHistory> resultList = results.getContent();
		if (resultList.size() == 0) {
			logger.debug("No history entries found.");
			throw new NotFoundHistoryException();
		}
		
		return resultList;
	}

	@Override
	@Transactional(readOnly=true)
	public List<AuthenticationHistory> getAuthenticationHistory(String userKey,
			AuthenticationHistoryReason reasonCode, int pageNumber, int pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, SORT_PROPERTY);
		Page<AuthenticationHistory> results;
		
		if (reasonCode == null)
			results = authenticationHistoryRepository.findByUserKey(userKey, pageRequest);
		else
			results = authenticationHistoryRepository.findByUserKeyAndReasonCode(userKey, reasonCode.getCode(), pageRequest);
				
		List<AuthenticationHistory> resultList = results.getContent();
		if (resultList.size() == 0) {
			logger.debug("No history entries found.");
			throw new NotFoundHistoryException();
		}
		
		return resultList;
	}

	@Override
	@Transactional(readOnly=true)
	public List<TokenHistory> getTokenHistory(String owner,
			TokenHistoryReason reasonCode, int pageNumber, int pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, SORT_PROPERTY);
		Page<TokenHistory> results;
		
		if (reasonCode == null)
			results = tokenHistoryRepository.findByOwner(owner, pageRequest);
		else
			results = tokenHistoryRepository.findByOwnerAndReasonCode(owner, reasonCode.getCode(), pageRequest);
		
		List<TokenHistory> resultList = results.getContent();
		if (resultList.size() == 0) {
			logger.debug("No history entries found.");
			throw new NotFoundHistoryException();
		}
		
		return resultList;
	}
	
	@Override
	@Transactional(readOnly=true)
	public DeleteUserHistory getDeleteUserHistoryByUserKey(String clientId, String userKey) {
		DeleteUserHistory deleteUserHistory = deleteUserHistoryRepository.findByClientIdAndUserKey(clientId, userKey);
		if (deleteUserHistory == null) {
			logger.debug("No history entries found.");
			throw new NotFoundHistoryException();
		}
		
		return deleteUserHistory;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<DeleteUserHistory> getDeleteUserHistoryByEmail(String clientId, String emailId, int pageNumber, int pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, SORT_PROPERTY);
		Page<DeleteUserHistory> results = deleteUserHistoryRepository.findByClientIdAndEmailId(clientId, emailId, pageRequest);
		
		List<DeleteUserHistory> resultList = results.getContent();
		if (resultList.size() == 0) {
			logger.debug("No history entries found.");
			throw new NotFoundHistoryException();
		}
		
		return resultList;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<DeleteUserHistory> getDeleteUserHistory(String clientId, int pageNumber, int pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, SORT_PROPERTY);
		Page<DeleteUserHistory> results = deleteUserHistoryRepository.findByClientId(clientId, pageRequest);
		
		List<DeleteUserHistory> resultList = results.getContent();
		if (resultList.size() == 0) {
			logger.debug("No history entries found.");
			throw new NotFoundHistoryException();
		}
		
		return resultList;
	}
	
	@Override
	public void clearHistory() {
		userHistoryRepository.deleteAllInBatch();
		authenticationHistoryRepository.deleteAllInBatch();
		tokenHistoryRepository.deleteAllInBatch();
		deleteUserHistoryRepository.deleteAllInBatch();
	}

	@Override
	public int deleteDeleteUserHistory() {
		return deleteUserHistoryRepository.delete(CommonUtils.getTimeAgo(CommonConstants.HISTORY_KEEPING_PERIOD));
	}

	@Override
	public int deleteUserHistory() {
		return userHistoryRepository.delete(CommonUtils.getTimeAgo(CommonConstants.HISTORY_KEEPING_PERIOD));		
	}

	@Override
	public int deleteTokenHistory() {
		return tokenHistoryRepository.delete(CommonUtils.getTimeAgo(CommonConstants.HISTORY_KEEPING_PERIOD));		
	}

	@Override
	public int deleteAuthenticationHistory() {
		return authenticationHistoryRepository.delete(CommonUtils.getTimeAgo(CommonConstants.HISTORY_KEEPING_PERIOD));		
	}
}
