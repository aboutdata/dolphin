package com.sabsari.dolphin.core.member.service;

/**
 * Created by jungkeun.park@sk.com on 2014. 6. 12..
 */
public interface PasswordService {
	public String resetPassword(String userKey);
	public void updatePassword(String userKey, String existingPassword, String newPassword);
	public void updatePassword(String userKey, String newPassword);
}
