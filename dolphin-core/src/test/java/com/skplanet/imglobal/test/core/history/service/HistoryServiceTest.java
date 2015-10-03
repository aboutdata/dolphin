package com.sabsari.dolphin.test.core.history.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sabsari.dolphin.core.config.ContextConfig;
import com.sabsari.dolphin.core.history.domain.AuthenticationHistory;
import com.sabsari.dolphin.core.history.domain.DeleteUserHistory;
import com.sabsari.dolphin.core.history.domain.TokenHistory;
import com.sabsari.dolphin.core.history.domain.UserHistory;
import com.sabsari.dolphin.core.history.domain.code.AuthenticationHistoryReason;
import com.sabsari.dolphin.core.history.domain.code.TokenHistoryReason;
import com.sabsari.dolphin.core.history.domain.code.UserHistoryReason;
import com.sabsari.dolphin.core.history.service.HistoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class})
@ActiveProfiles(value = "dev")
public class HistoryServiceTest {

	@Autowired
	private HistoryService historyService;
	
	@Test
	public void _test() {
	}
	
//	@Test
	public void _히스토리삭제() {
		System.out.println(historyService.deleteUserHistory());
	}
	
//	@Test
	public void _클리어히스토리() {
		historyService.clearHistory();
	}
	
//	@Test
	public void _유저히스토리조회() {
		List<UserHistory> results = historyService.getUserHistory("db533a16-5c7c-4932-a2a7-1ef405062f49", null, 1, 30);
		
		for (int i=0; i < results.size(); i++) { 
			System.out.println(results.get(i).getContent());
		}
	}
	
//	@Test
	public void _유저히스토리사유조회() {
		List<UserHistory> results = historyService.getUserHistory("db533a16-5c7c-4932-a2a7-1ef405062f49", UserHistoryReason.MODIFY_PROFILE, 1, 30);
		
		for (int i=0; i < results.size(); i++) { 
			System.out.println(results.get(i).getContent());
		}
	}
	
//	@Test
	public void _인증히스토리조회() {
		List<AuthenticationHistory> results = historyService.getAuthenticationHistory("9c3a0086-a9af-4903-b361-8e3ae9a69420", null, 1, 30);
		
		for (int i=0; i < results.size(); i++) { 
			System.out.println(results.get(i).getContent());
		}
	}
	
//	@Test
	public void _인증히스토리사유조회() {
		List<AuthenticationHistory> results = historyService.getAuthenticationHistory("9c3a0086-a9af-4903-b361-8e3ae9a69420", AuthenticationHistoryReason.MODIFY_PASSWORD, 1, 30);
		
		for (int i=0; i < results.size(); i++) { 
			System.out.println(results.get(i).getContent());
		}
	}
	
//	@Test
	public void _토큰히스토리조회() {
		List<TokenHistory> results = historyService.getTokenHistory("f529635b-8f4b-4c55-887b-1722a040236d", null, 1, 30);
		
		for (int i=0; i < results.size(); i++) { 
			System.out.println(results.get(i).getRegistDate());
		}
	}
	
//	@Test
	public void _토큰히스토리사유조회() {
		List<TokenHistory> results = historyService.getTokenHistory("f529635b-8f4b-4c55-887b-1722a040236d", TokenHistoryReason.ISSUE_TOKEN, 1, 30);
		
		for (int i=0; i < results.size(); i++) { 
			System.out.println(results.get(i).getRegistDate());
		}
	}
	
//	@Test
	public void _삭제히스토리조회() {
		List<DeleteUserHistory> results = historyService.getDeleteUserHistory("f4e55ce7-9104-4617-ae6d-d68bbdfc647e", 1, 30);
		
		for (int i=0; i < results.size(); i++) { 
			System.out.println(results.get(i).getRegistDate());
		}
	}
	
//	@Test
	public void _삭제히스토리조회email() {
		List<DeleteUserHistory> results = historyService.getDeleteUserHistoryByEmail("f4e55ce7-9104-4617-ae6d-d68bbdfc647e", "sabsari@sk.com", 1, 30);
		
		for (int i=0; i < results.size(); i++) { 
			System.out.println(results.get(i).getRegistDate());
		}
	}
	
//	@Test
	public void _삭제히스토리조회userKey() {
		DeleteUserHistory result = historyService.getDeleteUserHistoryByUserKey("f4e55ce7-9104-4617-ae6d-d68bbdfc647e", "No. 18");		
		System.out.println(result.getRegistDate());		
	}
}
