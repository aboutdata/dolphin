package com.sabsari.dolphin.core.member.service.impl;

import java.util.Date;

import com.sabsari.dolphin.core.exception.business.NotFoundClientIdException;
import com.sabsari.dolphin.core.member.domain.Application;
import com.sabsari.dolphin.core.member.repository.ApplicationRepository;
import com.sabsari.dolphin.core.member.service.ApplicationService;
import com.sabsari.dolphin.core.value.ValueGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jungkeun.park@sk.com on 2014. 4. 21..
 */
@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

	private final static Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);
	
	@Autowired
	ApplicationRepository applicationRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	ValueGenerator valueGenerator;
	
	@Override
	@Transactional(readOnly=true)
	public String getGroupKey(String clientId) {
		Application application = applicationRepository.findOne(clientId);
		if (application == null) {
			logger.debug("The application({}) is not found.", clientId);
			throw new NotFoundClientIdException(clientId);
		}
		
		return application.getGroupKey();
	}

	@Override
	@Transactional(readOnly=true)
	public Application getApplication(String clientId) {
		Application application = applicationRepository.findOne(clientId);
		if (application == null) {
			logger.debug("The application({}) is not found.", clientId);
			throw new NotFoundClientIdException(clientId);
		}
		
		return application;
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isExistApplication(String clientId) {
		Application application = applicationRepository.findOne(clientId);
		if(application == null)
			return false;
		else 
			return true;
	}

	@Override
	public String resetSecret(String clientId) {
		Application application = applicationRepository.findOne(clientId);
		if (application == null) {
			logger.debug("The application({}) is not found.", clientId);
			throw new NotFoundClientIdException(clientId);
		}
		
		String newSecret = valueGenerator.generateClientSecret();
		String encodedNewSecret = passwordEncoder.encode(newSecret);
		
		application.setClientSecret(encodedNewSecret);
		application.setModifyDate(new Date());
		applicationRepository.save(application);
		
		return newSecret;
	}
}
