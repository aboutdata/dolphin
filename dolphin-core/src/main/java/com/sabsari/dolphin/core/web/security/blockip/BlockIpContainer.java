package com.sabsari.dolphin.core.web.security.blockip;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.sabsari.dolphin.core.common.util.CommonUtils;
import com.sabsari.dolphin.core.common.util.SortedArrayList;
import com.sabsari.dolphin.core.member.domain.BlockIp;
import com.sabsari.dolphin.core.member.service.BlockIpService;

@Component
public class BlockIpContainer {
	private final static Logger logger = LoggerFactory.getLogger(BlockIpContainer.class);
	
	@Autowired
	private BlockIpService blockIpService;
	
	private SortedArrayList<Long> blockIpList;
	
	@PostConstruct
	private void init() {		
		List<BlockIp> list = blockIpService.getBlockIpList();
		blockIpList = new SortedArrayList<Long>();		
		
		int size = list.size();
		for (int i = 0; i < size; i++) {
			blockIpList.add(CommonUtils.ipToLong(list.get(i).getIp()));
		}
		
		logger.debug(blockIpList.size() + " block ip listed.");		
	}
	
	public boolean isBlockIp(String ip) {
		Long ipNo = CommonUtils.ipToLong(ip);
		return blockIpList.contains(ipNo);
	}
	
	@SuppressWarnings("unchecked")
	@Async
	public synchronized void add(List<String> ipList) {
		blockIpService.registBlockIp(ipList);
		
		SortedArrayList<Long> newList = (SortedArrayList<Long>) blockIpList.clone();		
		int size = ipList.size();
		for (int i = 0; i < size; i++) {
			newList.add(CommonUtils.ipToLong(ipList.get(i)));
		}		
		blockIpList = newList;		// 기존 리스트는 gc
		logger.debug(size + " block ip added asynchronously.");
	}
	
	@SuppressWarnings("unchecked")
	@Async
	public synchronized void remove(List<String> ipList) {
		blockIpService.removeBlockIp(ipList);
		
		SortedArrayList<Long> newList = (SortedArrayList<Long>) blockIpList.clone();		
		int size = ipList.size();
		for (int i = 0; i < size; i++) {
			newList.remove(CommonUtils.ipToLong(ipList.get(i)));
		}		
		blockIpList = newList;
		logger.debug(size + " block ip removed asynchronously.");
	}
}
