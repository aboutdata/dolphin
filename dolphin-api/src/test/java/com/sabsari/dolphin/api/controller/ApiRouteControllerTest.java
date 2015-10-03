package com.sabsari.dolphin.api.controller;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sabsari.dolphin.config.ContextConfig;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by jungkeun.park on 2014. 1. 12..
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {ContextConfig.class})
////@ContextConfiguration
//@WebAppConfiguration
//@ActiveProfiles(value = "standalone")
public class ApiRouteControllerTest {
	/*
	@Autowired
	WebApplicationContext webApplicationContext;
	MockMvc mockMvc;

	//@Autowired
	//IdmApiClient.TransactionNoGenerator

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.alwaysDo(MockMvcResultHandlers.print())
				//.alwaysExpect(status().isOk())
				.build();
	}

	@Autowired
	MessageSource messageSource;


	@Test
	public void testProccessPostUserApi() throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		Map<String, String> userParam = new HashMap<String, String>();
		userParam.put("emailId", "testid");


		mockMvc.perform(
				post("/v1/user")
						.param("trNo", "000000")
						.param("sst", "00001")
						.content(mapper.writeValueAsString(userParam))
		)
//				.andExpect(handler().handlerType(ApiRouteController.class))
				.andExpect(jsonPath("code").value("S001"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProcessGetIsAliveApi() throws Exception {

		mockMvc.perform(
				get("/v1/isAlive")
						.param("trNo", "000000")
						.param("sst", "00001")

		)
				.andExpect(jsonPath("code").value("S001"))
				.andExpect(jsonPath("data").value("OK"))
		;


	}
	*/
}
