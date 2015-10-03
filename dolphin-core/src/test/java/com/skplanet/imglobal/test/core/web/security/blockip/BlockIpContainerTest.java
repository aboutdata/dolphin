package com.sabsari.dolphin.test.core.web.security.blockip;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sabsari.dolphin.core.common.util.CommonUtils;
import com.sabsari.dolphin.core.config.ContextConfig;
import com.sabsari.dolphin.core.web.security.blockip.BlockIpContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class})
@WebAppConfiguration
@ActiveProfiles(value="dev")
public class BlockIpContainerTest {
	
	@Autowired
	private BlockIpContainer blockIpContainer;
	
	private static int counter = 1000;
	private static List<String> ips;
	private static String testIp = "100.100.100.100";
		
	@BeforeClass
	public static void preSet() {
		ips = new ArrayList<String>();
		for (int i = 0; i < counter-1 ; i++ ) {
			ips.add(CommonUtils.getRandomIp());
		}
		ips.add(testIp);
	}
	
	@Test
	public void _삽입삭제() {
		String ip = "100.100.100.100";	
		assertFalse(blockIpContainer.isBlockIp(ip));
		
//		blockIpContainer.add(ip);
//		assertTrue(blockIpContainer.isBlockIp(ip));
//				
//		blockIpContainer.remove(ip);
//		assertFalse(blockIpContainer.isBlockIp(ip));
	}
	
//	@Test
	public void _대량삽입삭제() {
		blockIpContainer.add(ips);
		assertTrue(blockIpContainer.isBlockIp(ips.get(0)));
		assertTrue(blockIpContainer.isBlockIp(ips.get(counter-1)));			
		
		blockIpContainer.remove(ips);
		assertFalse(blockIpContainer.isBlockIp(ips.get(0)));
		assertFalse(blockIpContainer.isBlockIp(ips.get(counter-1)));
	}
		
//	@Test
	public void _성능측정() {
		long start = System.currentTimeMillis();		
		blockIpContainer.add(ips);		
		long end = System.currentTimeMillis();
		long elapsed = end - start;
		System.out.println(counter + "건 삽입삭제 소요시간: " + elapsed + "ms");
				
		assertTrue(blockIpContainer.isBlockIp(ips.get(0)));
		assertTrue(blockIpContainer.isBlockIp(ips.get(counter-1)));
		
		start = System.currentTimeMillis();		
		for (int i = 0; i < counter; i++) {
			assertTrue(blockIpContainer.isBlockIp(ips.get(i)));
		}
		end = System.currentTimeMillis();
		elapsed = end - start;
		System.out.println(counter + "건 조회 소요시간: " + elapsed + "ms");
	}
}
