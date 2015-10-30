package com.sabsari.dolphin.core.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.sabsari.dolphin.core.common.ISO8601;
import com.sabsari.dolphin.core.validator.FormatValidator;

public final class CommonUtils {
	
	public static boolean isExpired(int expiresIn, Date startDate) {
		return getRestTime(expiresIn, startDate) <= 0;
	}
	
	public static int getRestTime(int expiresIn, Date startDate) {
		long end = (long)(expiresIn * 1000) + startDate.getTime();		
		long rest = end - new Date().getTime();
		return (int)(rest / 1000);
	}
	
	public static Date getTimeAgo(int daysAgo) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -daysAgo);		
		return c.getTime();
	}
	
	/**
	 * input의 앞 3자리만 노출
	 */
	public static String masking(String input) {
		if (StringUtils.isEmpty(input))
			throw new IllegalArgumentException();
		
		String[] part;
		String account;
		String domain;
		
		if (FormatValidator.isEmail(input)) {
			part = input.split("@");
			account = part[0];
			domain = "@" + part[1];
		}
		else {
			account = input;
			domain = "";
		}		
		
		char[] c = account.toCharArray();
		
		if (c.length == 1) {
			c[0] = '*';
		}
		else if (c.length == 2) {
			c[1] = '*';
		}
		else if (c.length == 3) {
			c[0] = '*';
			c[1] = '*';
		}
		else if (c.length > 3) {
			for (int i = 3 ; i < c.length ; i++) {
				c[i] = '*';
			}
		}
		
		return String.valueOf(c) + domain;
	}
	
	public static String getISO8601FormatDate(ISO8601 iso8601, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(iso8601.format());		
		return 	sdf.format(date);	
	}
	
	public static long ipToLong(String ip) {		 
		long result = 0;	 
		String[] ipAddressInArray = ip.split("\\.");
	 
		for (int i = 3; i >= 0; i--) {	 
			long num = Long.parseLong(ipAddressInArray[3 - i]);
			result |= num << (i * 8);	 
		}
	 
		return result;
	}
	
	/**
	 * for test
	 */
	public static String getRandomIp() {
		Random random = new Random();
		return random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256);
	}
}