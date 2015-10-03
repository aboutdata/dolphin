package com.sabsari.dolphin.core.message;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import com.sabsari.dolphin.core.common.CommonConstants;
import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.system.NotFoundResponseMessageException;

@Component
public class MessageGenerator {

	@Autowired
	private MessageSource messageSource;

	public String getResponseMessage(ResponseCode responseCode, Locale locale, String... args) 
			throws NotFoundResponseMessageException {		
		return getResponseMessage(responseCode.getCode(), locale, args);
	}

	public String getResponseMessage(ResponseCode responseCode, Locale locale) 
			throws NotFoundResponseMessageException {
		return getResponseMessage(responseCode.getCode(), locale, (String[])null);
	}
	
	public String getResponseMessage(ResponseCode responseCode, String... args) 
			throws NotFoundResponseMessageException {
		return getResponseMessage(responseCode.getCode(), CommonConstants.DEFAULT_LOCALE, args);
	}
	
	public String getResponseMessage(ResponseCode responseCode)
			throws NotFoundResponseMessageException {
		return getResponseMessage(responseCode.getCode(), CommonConstants.DEFAULT_LOCALE, (String[])null);
	}
	
	private String getResponseMessage(String code, Locale locale, String... args) {
		String message;
		
		try {
			message = messageSource.getMessage(code, args, locale);
		}
		catch (NoSuchMessageException ex) {
			throw new NotFoundResponseMessageException(ex);
		}
		
		return message;
	}
}
