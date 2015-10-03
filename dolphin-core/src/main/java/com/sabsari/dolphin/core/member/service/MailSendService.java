package com.sabsari.dolphin.core.member.service;


/**
 * Created by jungkeun.park on 2014. 1. 10..
 */
public interface MailSendService {
	public void sendVerificationEmail(String userKey);
	public String verifyEmail(String verificationValue);
	public void sendMail(String from, String to, String subject, String body);
}
