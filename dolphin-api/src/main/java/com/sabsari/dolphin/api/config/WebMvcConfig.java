package com.sabsari.dolphin.api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan(basePackages={"com.sabsari.dolphin.api"})
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	 
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true)			// http://...../abc.xml or abc.json
				  .favorParameter(true)
				  .parameterName("mediaType")		// http://...../abc?mediaType=json
				  .ignoreAcceptHeader(true)
				  .useJaf(false)
				  .defaultContentType(MediaType.APPLICATION_JSON)
				  .mediaType("json", MediaType.APPLICATION_JSON)
				  .mediaType("xml", MediaType.APPLICATION_XML);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jackson2HttpMessageConverter.setPrettyPrint(true);
		converters.add(jackson2HttpMessageConverter);
	}

	@Bean
	public HandlerInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");			// request 에서 "lang"이라는 파라미터 값을 가져와서 Locale을 적용
		return interceptor;
	}	

	@Bean
	public LocaleResolver localeResolver() {
		// User Agent 측이 애플리케이션에 전송하는 Accept-Lanague 헤더를 기반으로 로케일을 반환.(이 값이 없다면 서버의 기본 값을 사용)
		AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
		return resolver;
	}
	
	@Autowired
	private HandlerInterceptor authInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(authInterceptor);
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
