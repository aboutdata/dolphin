package com.sabsari.dolphin.core.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sabsari.dolphin.core.common.code.ResponseCode;
import com.sabsari.dolphin.core.config.ContextConfig;
import com.sabsari.dolphin.core.message.MessageGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class})
@WebAppConfiguration
@ActiveProfiles(value="dev")
public class MessageGeneratorTest {

	@Autowired
	private MessageGenerator messageGenerator;
	
	@Test 
	public void _응답메시지생성() {
		String responseMessage = messageGenerator.getResponseMessage(ResponseCode.ERROR_UNKNOWN);
		System.out.println(responseMessage);
		assertEquals(responseMessage, "An unexpected error has occurred in the internal system.");
	}	
}
