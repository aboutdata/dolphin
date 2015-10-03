package com.sabsari.dolphin.core.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sabsari.dolphin.core.member.domain.BlockIp;
import com.sabsari.dolphin.core.member.repository.BlockIpRepository;
import com.sabsari.dolphin.core.member.service.BlockIpService;
import com.sabsari.dolphin.core.validator.FormatValidator;

@Service
@Transactional
public class BlockIpServiceImpl implements BlockIpService {
	
	@Autowired
	private BlockIpRepository blockIpRepository; 

	@Override
	@Transactional(readOnly=true)
	public List<BlockIp> getBlockIpList() {		
		return blockIpRepository.findAll();
	}
	
	@Override
	public void registBlockIp(String ip) {
		if (!FormatValidator.isIpV4Addr(ip)) {
			return;
		}		
		blockIpRepository.save(new BlockIp(ip));
	}

	@Override
	public void removeBlockIp(String ip) {
		if (!FormatValidator.isIpV4Addr(ip)) {
			return;
		}		
		blockIpRepository.delete(ip);
	}

	@Override
	public void registBlockIp(List<String> ipList) {
		for (String ip : ipList) {
			registBlockIp(ip);
		}
	}

	@Override
	public void removeBlockIp(List<String> ipList) {
		for (String ip : ipList) {
			removeBlockIp(ip);
		}		
	}
	
	@Override
	public void clear() {
		blockIpRepository.deleteAllInBatch();		
	}
}
