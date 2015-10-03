package com.sabsari.dolphin.core.member.service;

import com.sabsari.dolphin.core.member.domain.Application;

/**
 * Created by jungkeun.park@sk.com on 2014. 4. 21..
 */
public interface ApplicationService {	
	public String getGroupKey(String clientId);
	public Application getApplication(String clientId);
	public boolean isExistApplication(String clientId);
	public String resetSecret(String clientId);
}
