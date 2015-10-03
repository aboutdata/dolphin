package com.sabsari.dolphin.api.controller;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sabsari.dolphin.config.ContextConfig;
//import org.junit.Before;
//import org.junit.Ignore;
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
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by jungkeun.park on 2014. 1. 12..
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {ContextConfig.class})
////@ContextConfiguration
//@WebAppConfiguration
//@ActiveProfiles(value = "dev")
public class GlobalIdControllerTest {
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
    public void 로그인확인() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> loginParam = new HashMap<String,String>();
        loginParam.put("emailId","shong@sk.com");
        loginParam.put("password","ddd");

        mockMvc.perform(
                post("/v1/login")
                        .header("x-skponeid-appkey", "bd6d280e-d3de-499c-855a-17b69693124b")
                        .content(mapper.writeValueAsString(loginParam))
        )
//                .andExpect(handler().handlerType(ApiRouteController.class))
                .andExpect(jsonPath("code").value("S001"))
                .andExpect(jsonPath("message").value("OK"))
                .andExpect(jsonPath("data.userKey").value("1000000008"))
                .andDo(MockMvcResultHandlers.print())
        ;

    }

    @Test
    @Ignore
    public void 사용자추가() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> userParam = new HashMap<String,String>();
        userParam.put("emailId","hsb7410@gmail.com");
        userParam.put("password","testUser");
        userParam.put("name","GLB");
        userParam.put("birthday","20140419");
        userParam.put("phone","010-1234-5678");
        userParam.put("gender","M");

        mockMvc.perform(
                post("/v1/users")
                        .header("x-skponeid-appkey", "bd6d280e-d3de-499c-855a-17b69693124b")
                        .content(mapper.writeValueAsString(userParam))
        )
                .andExpect(jsonPath("code").value("S001"))
                .andExpect(jsonPath("message").value("OK"))
                .andDo(MockMvcResultHandlers.print())
        ;
    }

    @Test
    @Ignore
    public void 사용자정보변경() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> userParam = new HashMap<String,String>();
        userParam.put("name","GLB");
        userParam.put("birthday","2014-04-20");
        userParam.put("gender","F");

        mockMvc.perform(
                put("/v1/users/1000000008")
                        .header("x-skponeid-appkey", "bd6d280e-d3de-499c-855a-17b69693124b")
                        .content(mapper.writeValueAsString(userParam))
        )
                .andExpect(jsonPath("code").value("S001"))
                .andExpect(jsonPath("message").value("OK"))
                .andDo(MockMvcResultHandlers.print())
        ;
    }

    @Test
    @Ignore
    public void 사용자정보변경_원복() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> userParam = new HashMap<String,String>();
        userParam.put("name","GLB");
        userParam.put("birthday","2014-04-19");
        userParam.put("gender","M");

        mockMvc.perform(
                put("/v1/users/1000000008")
                        .header("x-skponeid-appkey", "bd6d280e-d3de-499c-855a-17b69693124b")
                        .content(mapper.writeValueAsString(userParam))
        )
                .andExpect(jsonPath("code").value("S001"))
                .andExpect(jsonPath("message").value("OK"))
                .andDo(MockMvcResultHandlers.print())
        ;
    }

    @Test
    @Ignore
    public void 사용자삭제() throws Exception {
        mockMvc.perform(
                delete("/v1/users/1000000013")
                        .header("x-skponeid-appkey", "bd6d280e-d3de-499c-855a-17b69693124b")
        )
                .andExpect(jsonPath("code").value("S001"))
                .andExpect(jsonPath("message").value("OK"))
                .andDo(MockMvcResultHandlers.print())
        ;
    }

    @Test
    @Ignore
    public void Email후인증() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> userParam = new HashMap<String,String>();
        userParam.put("name","GLB");
        userParam.put("birthday","2014-04-19");
        userParam.put("gender","M");

        mockMvc.perform(
                put("/v1/mailVerified/1000000013")
                        .header("x-skponeid-appkey", "bd6d280e-d3de-499c-855a-17b69693124b")
                        .content(mapper.writeValueAsString(userParam))
        )
                .andExpect(jsonPath("code").value("S001"))
                .andExpect(jsonPath("message").value("OK"))
                .andDo(MockMvcResultHandlers.print())
        ;
    }
*/
}
