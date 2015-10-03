package com.sabsari.dolphin.core.member.service;

import com.sabsari.dolphin.core.member.domain.User;
import com.sabsari.dolphin.core.member.domain.code.AuthStatusCode;
import com.sabsari.dolphin.core.member.domain.code.Gender;
import com.sabsari.dolphin.core.member.domain.code.YesNo;

public interface UserService {
	
	public String getGroupKey(String userKey);

	public User getUser(String userKey);
	public User getUser(String clientId, String emailId);
	
	public boolean isEmailVerified(String userKey);
	public boolean isEmailIdAvailable(String clientId, String emailId);
	public void verifyEmail(String userKey);
	
	public boolean hasPermission(Long userSeq, String clientId);
	public boolean hasPermission(String userKey, String clientId);
	
	public User createUser(String clientId, String emailId, AuthStatusCode authStatusCode, YesNo emailVerified, String firstName, String lastName, String password, String phone, String birthday, Gender gender);
	public User createUser(String clientId, String emailId, String firstName, String lastName, String password, String phone, String birthday, Gender gender);
	
	public void deleteUser(String userKey);
	public void deleteUser_v1_1(String userKey, String clientId);
	public User updateUser(String userKey, String firstName, String lastName, String birthday, String phone, Gender gender);
	
	public void updateEmailId(String userKey, String updateEmailId, YesNo emailVerified);
	public void updateEmailId(String userKey, String updateEmailId);
}
