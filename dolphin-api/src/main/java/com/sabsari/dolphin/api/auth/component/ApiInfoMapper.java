package com.sabsari.dolphin.api.auth.component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

import com.sabsari.dolphin.api.annotation.Api;
import com.sabsari.dolphin.api.auth.model.ApiInfo;
import com.sabsari.dolphin.api.model.constants.PathVariables;
import com.sabsari.dolphin.core.auth.exception.UnexpectedErrorException;

@Component
public class ApiInfoMapper implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(ApiInfoMapper.class);
	
	private final String WILD_CARD_CHAR = "*";
	
	@Autowired
	ApplicationContext applicationContext;
	
	private ConcurrentHashMap<String, ApiInfo> apiInfoCache;

	@Override
	public void afterPropertiesSet() throws Exception {		
		initApiInfo();
	}
	
	public ApiInfo getApiInfo(HandlerMethod method) throws Exception {
		ApiInfo result = this.apiInfoCache.get(getKey(method));
		
		if (result == null) {
			result = this.addApiInfo(method);
		}
		
		return result;
	}
	
	private ApiInfo addApiInfo(HandlerMethod method) throws Exception {
		String key = getKey(method);
		ApiInfo apiInfo = new ApiInfo();

		if (method.getMethodAnnotation(Api.class) != null) {
			RequestMapping requestMapping = method.getMethodAnnotation(RequestMapping.class);
			String pattern = requestMapping.value()[0];
			
			if (pattern.contains(PathVariables.USER_KEY)) {
				apiInfo.setHasUserKeyInPath(true);
				pattern = pattern.replace(PathVariables.USER_KEY, WILD_CARD_CHAR);
			}
			else {
				apiInfo.setHasUserKeyInPath(false);
			}

			apiInfo.setLevel(method.getMethodAnnotation(Api.class).level());
			apiInfo.setRequestMappingPattern(pattern);			
		}
		else {
			logger.debug("Invalid api identity!");
			throw new UnexpectedErrorException();
		}
		
		this.apiInfoCache.put(key, apiInfo);
		
		logger.debug("ApiInfoMapper.addApiInfo: key=" + key);
		logger.debug("ApiInfoMapper.addApiInfo: apiInfo=" + apiInfo.toString());
		return apiInfo;
	}
		
	private String getKey(HandlerMethod method) {
		return method.getMethod().getName();
	}
	
	private void initApiInfo() throws Exception {
		int apiCounter = 0;
		String[] beanNames = applicationContext.getBeanNamesForAnnotation(Controller.class);
		
		if (beanNames == null) {
			logger.info("No controller bean found. stop server and fix it.");
            throw new Exception("No controller bean found. stop server and fix it.");
		}
		
		Map<String, Object> checkMap = new HashMap<String, Object>();
		for(String beanName : beanNames) {
			logger.info("Controller bean found: " + beanName);
			Class<?> beanType = applicationContext.getType(beanName);
			
			Method[] methods = beanType.getMethods();			
            for(Method method : methods) {
            	if (method.getAnnotation(Api.class) != null && method.getAnnotation(RequestMapping.class) != null) {
            		logger.info("Controller Api Method found: " + method.getName());
	            	if(checkMap.get(method.getName()) != null) {
	            		logger.info("Controller method name is duplicated! stop server and fix it.");
	                    throw new Exception("Controller method name is duplicated! stop server and fix it.");
	            	}                	
	            	checkMap.put(method.getName(), new Object());
	            	apiCounter++;
            	}
            }
		}
		logger.info("Total {} api listed.", apiCounter);
		this.apiInfoCache = new ConcurrentHashMap<String, ApiInfo>();
	}	
}
