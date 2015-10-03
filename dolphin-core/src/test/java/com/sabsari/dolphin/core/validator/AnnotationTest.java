package com.sabsari.dolphin.core.validator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import lombok.Data;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sabsari.dolphin.core.common.util.CommonUtils;
import com.sabsari.dolphin.core.validator.annotation.Birthday;
import com.sabsari.dolphin.core.validator.annotation.DateTime;
import com.sabsari.dolphin.core.validator.annotation.Email;
import com.sabsari.dolphin.core.validator.annotation.Gender;
import com.sabsari.dolphin.core.validator.annotation.IpAddress;
import com.sabsari.dolphin.core.validator.annotation.IpAddresses;
import com.sabsari.dolphin.core.validator.annotation.Name;
import com.sabsari.dolphin.core.validator.annotation.Password;
import com.sabsari.dolphin.core.validator.annotation.PhoneNo;

public class AnnotationTest {
	private static Validator validator;
	
	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void _유효성검사() {
		TestClass testClass = new TestClass();
		testClass.setEmailId("sanbsa@sk.com");
		testClass.setPassword("11111223");
		testClass.setBirthday("19990209");
		testClass.setName("park");
		testClass.setPhone("01066227777");
		testClass.setGender("f");
		testClass.setDatetime("20130723T160908+0900");
		
		List<String> list = new ArrayList<String>();
		list.add(CommonUtils.getRandomIp());
		list.add(CommonUtils.getRandomIp());
		list.add(CommonUtils.getRandomIp());
		list.add(CommonUtils.getRandomIp());
		list.add(CommonUtils.getRandomIp());		
		testClass.setIps(list);
		
		testClass.setIp(CommonUtils.getRandomIp());
		
		Set<ConstraintViolation<TestClass>> constraintViolations = validator.validate(testClass);
		assertEquals(1, constraintViolations.size());
		assertEquals("Invalid gender value", constraintViolations.iterator().next().getMessage());
	}
		
	@Data
	static class TestClass {
		
		@Email
		private String emailId;
		
		@Password
		private String password;		

		@Birthday
		private String birthday;
		
		@Name
		private String name;
		
		@PhoneNo
		private String phone;
		
		@Gender
		private String gender;
		
		@DateTime
		private String datetime;
		
		@IpAddresses
		private List<String> ips;
		
		@IpAddress
		private String ip;
	}
}
