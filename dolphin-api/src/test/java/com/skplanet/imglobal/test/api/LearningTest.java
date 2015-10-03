package com.sabsari.dolphin.test.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabsari.dolphin.api.model.ApiDataResult;
import com.sabsari.dolphin.api.model.result.DeleteUserHistoryResult;
import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.history.domain.DeleteUserHistory;

public class LearningTest {
	
	@Test
	public void _제이슨리스트매핑테스트() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		List<DeleteUserHistory> list = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			list.add(new DeleteUserHistory("email" + i, "useKey" + i, "clientId" + i));
		}
				
		DeleteUserHistoryResult historyResult = new DeleteUserHistoryResult(list);
		
		ApiDataResult result = new ApiDataResult(historyResult, ResponseCode.SUCCESS, "test");
		
		System.out.println(mapper.writeValueAsString(result));
	}
}
