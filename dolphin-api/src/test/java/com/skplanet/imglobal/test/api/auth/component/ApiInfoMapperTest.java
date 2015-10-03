package com.sabsari.dolphin.test.api.auth.component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sabsari.dolphin.api.annotation.Api;
import com.sabsari.dolphin.api.config.WebMvcConfig;
import com.sabsari.dolphin.core.config.ContextConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class,WebMvcConfig.class})
@WebAppConfiguration
@ActiveProfiles(value="dev")
public class ApiInfoMapperTest {
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Test
	public void _API정보확인테스트() throws Exception  {
		String[] beanNames = applicationContext.getBeanNamesForAnnotation(Controller.class);
		
		if (beanNames == null) {
			System.out.println("No controller bean found. stop server and fix it.");
            throw new Exception("No controller bean found. stop server and fix it.");
		}
		
		Map<String, Object> checkMap = new HashMap<String, Object>();
		for(String beanName : beanNames) {
			System.out.println("Controller bean found: " + beanName);
			Class<?> beanType = applicationContext.getType(beanName);
			
			Method[] methods = beanType.getMethods();			
            for(Method method : methods) {
            	if (method.getAnnotation(Api.class) != null && method.getAnnotation(RequestMapping.class) != null) {
            		System.out.println("Controller Api Method found: " + method.getName());
	            	if(checkMap.get(method.getName()) != null) {
	            		System.out.println("Controller method name is duplicated! stop server and fix it.");
	                    throw new Exception("Controller method name is duplicated! stop server and fix it.");
	            	}                	
	            	checkMap.put(method.getName(), new Object());
            	}
            }
		}
		
		System.out.println("Ok~!");
	}	
}
