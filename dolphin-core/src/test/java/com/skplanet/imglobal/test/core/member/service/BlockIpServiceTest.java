package com.sabsari.dolphin.test.core.member.service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sabsari.dolphin.core.common.util.CommonUtils;
import com.sabsari.dolphin.core.config.ContextConfig;
import com.sabsari.dolphin.core.member.domain.BlockIp;
import com.sabsari.dolphin.core.member.service.BlockIpService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class})
@ActiveProfiles(value = "dev")
public class BlockIpServiceTest {
	
	@Autowired
	private BlockIpService blockIpService;
	
	@Before
	public void _준비() {
		blockIpService.clear();
	}
	
	@Test
	public void _삭제테스트() {
		String ip = CommonUtils.getRandomIp();
		blockIpService.registBlockIp(ip);
		List<BlockIp> list = blockIpService.getBlockIpList();
		assertEquals(1, list.size());
		
		blockIpService.removeBlockIp(ip);
		List<BlockIp> list0 = blockIpService.getBlockIpList();
		assertEquals(0, list0.size());
	}
	
	@Test
	public void _조회테스트() {
		int counter = 5;
		for (int i = 0; i < counter; i++) {
			blockIpService.registBlockIp(CommonUtils.getRandomIp());
		}
		
		List<BlockIp> list = blockIpService.getBlockIpList();
		assertEquals(counter, list.size());
		
		for (int i = 0; i < counter; i++) {
			blockIpService.removeBlockIp(list.get(i).getIp());
		}
		
		List<BlockIp> list0 = blockIpService.getBlockIpList();
		assertEquals(0, list0.size());
	}
	
	@Test
	public void _대량테스트() {
		int counter = 5;
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < counter; i++) {
			list.add(CommonUtils.getRandomIp());
		}
		
		blockIpService.registBlockIp(list);		
		List<BlockIp> list0 = blockIpService.getBlockIpList();
		assertEquals(counter, list0.size());
	
		blockIpService.removeBlockIp(list);
		List<BlockIp> list1 = blockIpService.getBlockIpList();
		assertEquals(0, list1.size());
	}
	
//	@Test
	public void _테스트데이터적재() {
		int counter = 1000;
		for (int i = 0; i < counter; i++) {
			blockIpService.registBlockIp(CommonUtils.getRandomIp());
		}
	}
}
