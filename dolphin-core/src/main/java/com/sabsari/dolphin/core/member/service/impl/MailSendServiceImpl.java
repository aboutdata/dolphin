package com.sabsari.dolphin.core.member.service.impl;

import java.util.Date;

import com.sabsari.dolphin.core.common.util.SimpleCrypto;
import com.sabsari.dolphin.core.exception.business.EmailVerificationFailException;
import com.sabsari.dolphin.core.exception.business.NotFoundUserException;
import com.sabsari.dolphin.core.member.domain.EmailVerification;
import com.sabsari.dolphin.core.member.domain.User;
import com.sabsari.dolphin.core.member.repository.EmailVerificationRepository;
import com.sabsari.dolphin.core.member.repository.UserRepository;
import com.sabsari.dolphin.core.member.service.MailSendService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jungkeun.park on 2014. 1. 10..
 */
@Service
@Transactional
public class MailSendServiceImpl implements MailSendService {

    private final static Logger logger = LoggerFactory.getLogger(MailSendServiceImpl.class);

    @Autowired
    private MailSender mailSender;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${mail.emailHost}") private String emailHost;
    @Value("${mail.emailPath}") private String emailPath;
    @Value("${mail.domain}") private String emailDomain;
    @Value("${mail.Subject}") private String emailSubject;
    
    private final String MAIL_LINK = "http://" + emailHost + emailPath;
    
    @Override
    @Async
    public void sendVerificationEmail(String userKey) {
    	User user = userRepository.findByUserKey(userKey);
		if (user == null) {
            logger.debug("The User({}) is not found.", userKey);
            throw new NotFoundUserException(userKey);
        }
		
		EmailVerification emailVerification = emailVerificationRepository.findOne(user.getUserSeq());    	
    	String link = MAIL_LINK + emailVerification.getVerificationValue();
    	
    	SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailDomain);
        message.setTo(user.getEmailId());
        message.setSubject("Sk Planet ID 인증메일");
        message.setText(link);

        mailSender.send(message);
    }
    
    @Override
	public String verifyEmail(String verificationValue) {
    	User user = null;
    	Long crc32 = SimpleCrypto.getCRC32(verificationValue);

    	EmailVerification emailVerification = emailVerificationRepository.findByVerificationValueAndCrc32(verificationValue, crc32);
    	if (emailVerification == null) {
    		logger.debug("Verification fail");
    		throw new EmailVerificationFailException("crc32 check fail");
    	}
    	
    	user = userRepository.findOne(emailVerification.getUserSeq());
    	
    	if (user == null) {
    		logger.debug("No user info");
			throw new EmailVerificationFailException("Verification value fail");
    	}
    	
    	emailVerification.setVerifiedDate(new Date());
    	emailVerificationRepository.save(emailVerification);
    	
    	return user.getUserKey();
	}
    
    @Override
    @Async
    public void sendMail(String from, String to, String subject, String body) {
    	SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    } 
}
