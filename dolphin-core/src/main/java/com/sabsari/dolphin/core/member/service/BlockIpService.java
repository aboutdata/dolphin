package com.sabsari.dolphin.core.member.service;

import java.util.List;

import com.sabsari.dolphin.core.member.domain.BlockIp;

public interface BlockIpService {
	public List<BlockIp> getBlockIpList();
	public void registBlockIp(String ip);
	public void removeBlockIp(String ip);
	public void registBlockIp(List<String> ipList);
	public void removeBlockIp(List<String> ipList);
	public void clear();
}
