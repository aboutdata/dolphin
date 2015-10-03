package com.sabsari.dolphin.core.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * {index[,alignment]} e.g) {0,+16}
	 * index : start with 0 index.
	 * alignment : (optional) signed decimal number. The rest of length will be padded by white space(+:right align, -:left align).
	 */	
	public static String format(String format, Object... args) {
		if (format == null)
			throw new IllegalArgumentException("The argument 'format' should not be null.");
		
		StringBuilder sb = new StringBuilder();
		
        Pattern pattern = Pattern.compile("\\{(0|[1-9][0-9]*)(,[-+][1-9][0-9]*)?\\}");
        Matcher matcher = pattern.matcher(format);
        
        String[] index;        
        int groupBeginIndex = 0;
        int replacePartBeginIndex = 0;
        int replacePartEndIndex = 0;
        int replacementCounter = 0;
        
        while(matcher.find()) {
        	replacePartBeginIndex = matcher.start();
        	replacePartEndIndex = matcher.end();
        	
        	index = format.substring(replacePartBeginIndex + 1, replacePartEndIndex - 1).split(",");	
        	
        	if (Integer.parseInt(index[0]) == replacementCounter) {
        		if (args == null || replacementCounter >= args.length) {
					throw new IllegalArgumentException("The index {" + replacementCounter + "} exceeds the number of args.");
				}
        		
        		sb.append(format.substring(groupBeginIndex, replacePartBeginIndex));
        		if (index.length == 2)
        			formatting(sb, index[1], args[replacementCounter++]);
        		else
        			sb.append(args[replacementCounter++]);
        		groupBeginIndex = replacePartEndIndex;
        	}
        	else {
        		throw new IllegalArgumentException("The order of the index {" + index[0] + "} is wrong." );
        	}
        }
        
        if (args != null) {
        	if (replacementCounter < args.length) {
        		throw new IllegalArgumentException("The number of args(" + args.length + ") exceeds the range of the replacement index.");
        	}
        }
        else {
        	if (sb.length() == 0) {
        		sb.append(format);
        	}
        }

        return sb.toString();        
	}
	
	private static void formatting(StringBuilder sb, String alignment, Object arg) {
		String s = String.valueOf(arg);
		int padding = Integer.parseInt(alignment.substring(1)) - s.length();
		if (padding < 0) padding = 0;
				
		if (alignment.charAt(0) == '-') {
			sb.append(s);
			for (int i = 0; i < padding; i++)
				sb.append(" ");
		}
		else {			
			for (int i = 0; i < padding; i++)
				sb.append(" ");
			sb.append(s);
		}
	}
	
	/**
	 * for test
	 */
	public static String getRandomIp() {
		Random random = new Random();
		return random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256);
	}
}