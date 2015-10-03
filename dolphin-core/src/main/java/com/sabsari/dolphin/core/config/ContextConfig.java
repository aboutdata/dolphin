package com.sabsari.dolphin.core.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import com.sabsari.dolphin.core.common.CommonConstants;

@Configuration
@ComponentScan(basePackages="com.sabsari.dolphin.core")
@PropertySource(value={"classpath:properties/core-env.xml"})
@Import({
	DataConfig.class,
	AsyncConfig.class,
	MailConfig.class
})
public class ContextConfig {	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:i18n/message/responseMessage");
		messageSource.setCacheSeconds(-1);		// reload안함
		messageSource.setDefaultEncoding(CommonConstants.CHARSET_UTF8);
		return messageSource;
	}
	
	@Bean public PasswordEncoder passwordEncoder() {
		// return new BCryptPasswordEncoder(10);
		return new StandardPasswordEncoder(CommonConstants.PASSWORD_ENCODE_SECRET);
	}
	
	@Configurable
	@Profile({"dev"})
	public static class ProfileDevPropertiesConfig {
		@Bean
		public static PropertySourcesPlaceholderConfigurer profilePropertySourcesPlaceholderConfigurer() {
			return propertyConfigurer(new ClassPathResource("properties/dev/profile-env.xml"));
		}
	}

	@Configurable
	@Profile({"awsdev"})
	public static class ProfileAwsDevPropertiesConfig {
		@Bean
		public static PropertySourcesPlaceholderConfigurer profilePropertySourcesPlaceholderConfigurer() {
			return propertyConfigurer(new ClassPathResource("properties/awsdev/profile-env.xml"));
		}
	}

	@Configurable
	@Profile({"real"})
	public static class ProfileRealPropertiesConfig {
		@Bean
	    public static PropertySourcesPlaceholderConfigurer profilePropertySourcesPlaceholderConfigurer() {
		    return propertyConfigurer(new ClassPathResource("properties/real/profile-env.xml"));
	    }
	}
	
	private static PropertySourcesPlaceholderConfigurer propertyConfigurer(Resource... locations) {
		if(locations == null || locations.length == 0)
			return null;
		
		PropertySourcesPlaceholderConfigurer configurer =  new PropertySourcesPlaceholderConfigurer();
		configurer.setLocations(locations);
		return configurer;
	}
}