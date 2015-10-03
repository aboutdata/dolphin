package com.sabsari.dolphin.core.history.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sabsari.dolphin.core.common.util.CommonUtils;
import com.sabsari.dolphin.core.config.ContextConfig;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class})
@ActiveProfiles(value = "dev")
public class HistoryRepositoryTest {
	
	@Autowired
	AuthenticationHistoryRepository authenticationHistoryRepository;
	
	@Autowired
	DeleteUserHistoryRepository deleteUserHistoryRepository;
	
	@Autowired
	TokenHistoryRepository tokenHistoryRepository;
	
	@Autowired
	UserHistoryRepository userHistoryRepository;
	
	private String userKey = UUID.randomUUID().toString();
	private String clientId = UUID.randomUUID().toString();
	private String emailId = "sabsari@sk.com";
	private int count = 20;
	
	@Test
	public void _test() {
		
	}
	
//	@Test
	@Transactional
	@Rollback(false)
	public void _커스텀쿼리() {
		int c = authenticationHistoryRepository.delete(CommonUtils.getTimeAgo(0));
		System.out.println(c);
	}
	
//	@Test
	@Transactional
	@Rollback(false)
	public void _유저히스토리생성() throws InterruptedException {
		for (int i = 0; i < count ; i++) {
			UserHistory userHistory = new UserHistory(userKey, "No. " + i, UserHistoryReason.MODIFY_PROFILE);
			userHistoryRepository.save(userHistory);
			Thread.sleep(1400);
		}
	}
	
//	@Test
	@Transactional
	@Rollback(false)
	public void _인증히스토리생성() throws InterruptedException {
		for (int i = 0; i < count ; i++) {
			AuthenticationHistory authenticationHistory = new AuthenticationHistory(userKey, "No. " + i, AuthenticationHistoryReason.MODIFY_PASSWORD);
			authenticationHistoryRepository.save(authenticationHistory);
			Thread.sleep(1400);
		}
	}
	
//	@Test
	@Transactional
	@Rollback(false)
	public void _삭제히스토리생성() throws InterruptedException {
		for (int i = 0; i < count ; i++) {
			DeleteUserHistory deleteUserHistory = new DeleteUserHistory(emailId, "No. " + i, clientId);
			deleteUserHistoryRepository.save(deleteUserHistory);
			Thread.sleep(1400);
		}
	}
	
//	@Test
	@Transactional
	@Rollback(false)
	public void _토큰히스토리생성() throws InterruptedException {
		for (int i = 0; i < count ; i++) {
			TokenHistory tokenHistory = new TokenHistory(userKey, TokenHistoryReason.ISSUE_TOKEN);
			tokenHistoryRepository.save(tokenHistory);
			Thread.sleep(1400);
		}
	}
}
