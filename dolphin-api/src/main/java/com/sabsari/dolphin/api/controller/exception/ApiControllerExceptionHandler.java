package com.sabsari.dolphin.api.controller.exception;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import com.sabsari.dolphin.api.component.ResultFactory;
import com.sabsari.dolphin.api.model.ApiResult;
import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.exception.ClientException;
import com.sabsari.dolphin.core.exception.SystemException;
import com.sabsari.dolphin.core.exception.business.NotFoundApiException;

@ControllerAdvice(basePackages={"com.sabsari.dolphin.api.controller"})
public class ApiControllerExceptionHandler {
	
	@Autowired
	private ResultFactory resultFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(ApiControllerExceptionHandler.class);
	
	@ExceptionHandler(JsonMappingException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResult JsonMappingExceptionHandler(JsonMappingException ex, Locale locale) {		
		logger.debug("JsonMappingException at ApiController! : " + ex.getMessage());	
		
		return resultFactory.getFailure(ResponseCode.ERROR_INVALID_REQUEST_PARAMETER, locale, ex.getMessage());
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResult HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex, Locale locale) {		
		logger.debug("HttpMessageNotReadableException at ApiController! : " + ex.getMessage());	
		
		return resultFactory.getFailure(ResponseCode.ERROR_INVALID_REQUEST_PARAMETER, locale, ex.getRootCause().getMessage());
	}
	
	@ExceptionHandler(JsonParseException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResult JsonParseExceptionHandler(JsonParseException ex, Locale locale) {		
		logger.debug("JsonParseException at ApiController! : " + ex.getMessage());	
		
		return resultFactory.getFailure(ResponseCode.ERROR_INVALID_REQUEST_PARAMETER, locale, ex.getMessage());
	}
	
	@ExceptionHandler(UnrecognizedPropertyException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResult UnrecognizedPropertyExceptionHandler(UnrecognizedPropertyException ex, Locale locale) {		
		logger.debug("UnrecognizedPropertyException at ApiController! : " + ex.getMessage());	
		
		return resultFactory.getFailure(ResponseCode.ERROR_UNKNOWN_PARAMETER, locale, ex.getPropertyName());
	}
		
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(value=HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ResponseBody
	public ApiResult HttpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException ex, Locale locale) {		
		logger.debug("HttpMediaTypeNotSupportedException at ApiController! : " + ex.getMessage());
		
		return resultFactory.getFailure(ResponseCode.ERROR_INVALID_REQUEST, locale, ex.getMessage());
	}
	
	@ExceptionHandler(NotFoundApiException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ResponseBody
	public ApiResult NotFoundApiExceptionHandler(NotFoundApiException ex, Locale locale) {		
		logger.debug("NotFoundApiException at ApiController!");
		
		return resultFactory.getFailure(ex.getErrorResponseCode(), locale, (String[])null);
	}	
	
	@ExceptionHandler(ClientException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResult ClientExceptionHandler(ClientException ex, Locale locale) {		
		logger.debug("ClientException at ApiController!");
		
		return resultFactory.getFailure(ex.getErrorResponseCode(), locale, ex.getMessageParameters());
	}
	
	@ExceptionHandler(SystemException.class)
	@ResponseStatus(value=HttpStatus.SERVICE_UNAVAILABLE)
	@ResponseBody
	public ApiResult SystemExceptionHandler(SystemException ex, Locale locale) {		
		logger.debug("SystemException at ApiController!");
		
		return resultFactory.getFailure(ex.getErrorResponseCode(), locale, ex.getMessageParameters());
	}
		
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ApiResult commonExceptionHandler(Exception ex, Locale locale) {		
		logger.debug("Exception at ApiController! : " + ex);
		
		return resultFactory.getFailure(ResponseCode.ERROR_UNKNOWN, locale, (String[])null);
	}
}
