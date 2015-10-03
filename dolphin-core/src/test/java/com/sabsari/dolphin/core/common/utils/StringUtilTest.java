package com.sabsari.dolphin.core.common.utils;

import java.util.regex.Pattern;

import org.junit.Test;

import com.sabsari.dolphin.core.common.util.CommonUtils;

public class StringUtilTest {

	private static final String INDEX_REGEX = "^\\{(0|[1-9][0-9]*)(,[-+][1-9][0-9]*)?\\}$";
	
//	@Test
	public void _정규식테스트(){
		String input = "{0,+9}";
		System.out.println(Pattern.matches(INDEX_REGEX, input));
	}
	
	@Test
	public void _성능테스트() {
		int counter = 1;
		long start = System.currentTimeMillis();

		for (int i=0 ; i < counter ; i++) {
//			System.out.println(String.format("asdf%s, 1234%d, ㄱㄴㄷㄹ%s", "asdf", 234, "asdfasdf"));
			
			System.out.println(CommonUtils.format("asdf{0,+10}, 1234{1,-10}, ㄱㄴㄷㄹ{2,+20}", "asdf", 234, "asdfasdf"));
		}
		
		long end = System.currentTimeMillis();
		long elapsed = end - start;
		System.out.println(counter + " 건 소요시간: " + elapsed + "ms");
	}
}
