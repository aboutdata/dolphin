package com.sabsari.dolphin.core.common.utils;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sabsari.dolphin.core.common.util.CommonUtils;
import com.sabsari.dolphin.core.common.util.SortedArrayList;

public class SortedListTest {
	
	private static int counter = 10000;
	private static String[] ips = new String[counter];
	private static SortedArrayList<Long> baseList;
	
	@BeforeClass
	public static void before() {
		for (int i = 0; i < counter ; i++ ) {
			ips[i] = CommonUtils.getRandomIp();
		}
		
		baseList = new SortedArrayList<Long>();		
		
		for (int i = 0; i < counter; i++) {
			baseList.add(CommonUtils.ipToLong(ips[i]));
		}
		
		assertEquals(counter, baseList.size());
	}
	
	@Test
	public void _배열변환테스트() {
		Object[] arr = baseList.toArray();
		
		assertEquals(counter, baseList.size());
		assertEquals(counter, arr.length);
	}
		
	@Test
	public void _반복자테스트() {
		Iterator<Long> itr = baseList.iterator();
		int i = 0;
		while (itr.hasNext()) {
			itr.next();
			i++;
		}
		
		assertEquals(counter, baseList.size());
		assertEquals(counter, i);
	}
	
	@Test
	public void _조회성능테스트() {		
		long start = System.currentTimeMillis();

		for (int i=0 ; i < counter ; i++) {
			assertTrue(baseList.contains(CommonUtils.ipToLong(ips[i])));
		}
		
		long end = System.currentTimeMillis();
		long elapsed = end - start;
		System.out.println(counter + " 건 조회 소요시간: " + elapsed + "ms");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void _복제테스트() {		
		long start = System.currentTimeMillis();

		SortedArrayList<Long> cloneList = (SortedArrayList<Long>) baseList.clone();
		
		long end = System.currentTimeMillis();
		long elapsed = end - start;
		System.out.println(counter + " 건 복제 소요시간: " + elapsed + "ms");
		
		assertNotEquals(baseList, cloneList);
		assertEquals(baseList.size(), cloneList.size());
		for (int i = 0; i < baseList.size(); i++) {
			assertEquals(baseList.get(i), cloneList.get(i));
		}
	}
}
