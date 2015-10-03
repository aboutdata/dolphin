package com.sabsari.dolphin.core.member.service;

import com.sabsari.dolphin.core.member.domain.Application;
import com.sabsari.dolphin.core.member.domain.UserGroup;

public interface AdminService {
	public UserGroup getUserGroup(String groupKey);
	public Application getApplication(String clientId);
	
	public Application createAdmin();
	public String createUserGroup(String groupName);
	public Application registApplication(String groupKey, String appName);
	public String resetClientSecret(String clientId);
	
	public void deleteUserGroup(String groupKey);
	public void unregistApplication(String clientId);
}
