package com.sabsari.dolphin.core.member.service.impl;

import com.sabsari.dolphin.core.exception.business.NotFoundUserException;
import com.sabsari.dolphin.core.exception.business.PasswordUpdateFailException;
import com.sabsari.dolphin.core.exception.business.PasswordVerificationFailException;
import com.sabsari.dolphin.core.history.domain.AuthenticationHistory;
import com.sabsari.dolphin.core.history.domain.code.AuthenticationHistoryReason;
import com.sabsari.dolphin.core.history.repository.AuthenticationHistoryRepository;
import com.sabsari.dolphin.core.member.domain.Authentication;
import com.sabsari.dolphin.core.member.domain.User;
import com.sabsari.dolphin.core.member.domain.code.AuthStatusCode;
import com.sabsari.dolphin.core.member.repository.AuthenticationRepository;
import com.sabsari.dolphin.core.member.repository.UserRepository;
import com.sabsari.dolphin.core.member.service.PasswordService;
import com.sabsari.dolphin.core.value.ValueGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by jungkeun.park@sk.com on 2014. 6. 12..
 */
@Service
@Transactional
public class PasswordServiceImpl implements PasswordService {
	
	private final static Logger logger = LoggerFactory.getLogger(PasswordServiceImpl.class);

	@Autowired
	UserRepository userRepository;	

	@Autowired
	AuthenticationRepository authenticationRepository;
	
	@Autowired
	AuthenticationHistoryRepository authenticationHistoryRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	ValueGenerator valueGenerator;

	@Override
	public String resetPassword(String userKey) {
		User user = userRepository.findByUserKey(userKey);
		if (user == null) {
            logger.debug("The User({}) is not found.", userKey);
            throw new NotFoundUserException(userKey);
        }		
		
		Authentication authentication = user.getAuthentication();

		String newPassword = valueGenerator.generateRandomPassword();
		String encryptPassword = passwordEncoder.encode(newPassword);

		authentication.setPassword(encryptPassword);
		authentication.setAuthStatusCode(AuthStatusCode.TEMP_PASSWORD);
		authentication.setModifyDate(new Date());

		authenticationRepository.save(authentication);
		authenticationHistoryRepository.save(new AuthenticationHistory(userKey, null, AuthenticationHistoryReason.RESET_PASSWORD));
		return newPassword;
	}
	
	@Override
	public void updatePassword(String userKey, String existingPassword, String newPassword) {
		User user = userRepository.findByUserKey(userKey);
		if (user == null) {
            logger.debug("The User({}) is not found.", userKey);
            throw new NotFoundUserException(userKey);
        }
		
		Authentication authentication = user.getAuthentication();		
		String target = authentication.getPassword();
		
		// 기존 패스워드 검증
		if (!passwordEncoder.matches(existingPassword, target)) {
			logger.debug("The password of the user({}) is incorrect.", userKey);
			throw new PasswordVerificationFailException(userKey);
		}
		
		// 새 패스워드 확인
		if (existingPassword.equals(newPassword)) {
			logger.debug("New password is same as existing one.");
            throw new PasswordUpdateFailException();
		}
		
		AuthStatusCode statusCode = authentication.getAuthStatusCode();
		String encodedPassword = passwordEncoder.encode(newPassword);
		authentication.setPassword(encodedPassword);
		if (statusCode == AuthStatusCode.TEMP_PASSWORD || statusCode == AuthStatusCode.LOCKED) {
			authentication.setAuthStatusCode(AuthStatusCode.NORMAL);
		}
		authentication.setModifyDate(new Date());
		authenticationRepository.save(authentication);
		authenticationHistoryRepository.save(new AuthenticationHistory(userKey, null, AuthenticationHistoryReason.MODIFY_PASSWORD));
	}
	
	@Override
	public void updatePassword(String userKey, String newPassword) {
		User user = userRepository.findByUserKey(userKey);
		if (user == null) {
            logger.debug("The User({}) is not found.", userKey);
            throw new NotFoundUserException(userKey);
        }
		
		Authentication authentication = user.getAuthentication();
		AuthStatusCode statusCode = authentication.getAuthStatusCode();
		String encodedPassword = passwordEncoder.encode(newPassword);
		authentication.setPassword(encodedPassword);
		if (statusCode == AuthStatusCode.TEMP_PASSWORD || statusCode == AuthStatusCode.LOCKED) {
			authentication.setAuthStatusCode(AuthStatusCode.NORMAL);
		}
		authentication.setModifyDate(new Date());
		authenticationRepository.save(authentication);
		authenticationHistoryRepository.save(new AuthenticationHistory(userKey, null, AuthenticationHistoryReason.SET_PASSWORD));
	}
}
