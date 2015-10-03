package com.sabsari.dolphin.api.component;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sabsari.dolphin.api.model.AbstractResultModel;
import com.sabsari.dolphin.api.model.ApiDataResult;
import com.sabsari.dolphin.api.model.ApiResult;
import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.message.MessageGenerator;

@Component
public class ResultFactory {
	
	@Autowired
	private MessageGenerator messageGenerator;
	
	public ApiResult getSuccess(Locale locale) throws Exception {
		return this.getResult(null, ResponseCode.SUCCESS, locale);
	}
	
	public ApiResult getSuccess(AbstractResultModel result, Locale locale) throws Exception {
		return this.getResult(result, ResponseCode.SUCCESS, locale);
	}
	
	public ApiResult getResult(ResponseCode responseCode, Locale locale) throws Exception {
		return this.getResult(null, responseCode, locale);
	}

	public ApiResult getResult(AbstractResultModel result, ResponseCode responseCode, Locale locale) throws Exception {
				
		String responseMessage = messageGenerator.getResponseMessage(responseCode, locale);
		
		if (result == null) {
			return new ApiResult(responseCode, responseMessage);
		}
		else
			return new ApiDataResult(result, responseCode, responseMessage);
	}
	
	public ApiResult getFailure(ResponseCode responseCode, Locale locale, String... args) {
		String responseMessage = messageGenerator.getResponseMessage(responseCode, locale, args);
		
		ApiResult apiResult = new ApiResult(responseCode, responseMessage);
		return apiResult;
	}
}
