package com.sabsari.dolphin.api.config;

import com.sabsari.dolphin.core.common.CommonConstants;
import com.sabsari.dolphin.core.config.ContextConfig;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class ServletInitializer	extends AbstractAnnotationConfigDispatcherServletInitializer {	

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {ContextConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebMvcConfig.class};
	}
	
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding(CommonConstants.CHARSET_UTF8);		
		HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();		
		DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy("blockIpFilter");		
		return new Filter[]{encodingFilter, hiddenHttpMethodFilter, delegatingFilterProxy};
	}
}


