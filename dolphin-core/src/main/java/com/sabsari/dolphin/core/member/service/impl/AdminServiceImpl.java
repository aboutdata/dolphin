package com.sabsari.dolphin.core.member.service.impl;

import com.sabsari.dolphin.core.exception.business.NotFoundClientIdException;
import com.sabsari.dolphin.core.exception.business.NotFoundGroupException;
import com.sabsari.dolphin.core.member.domain.Application;
import com.sabsari.dolphin.core.member.domain.UserGroup;
import com.sabsari.dolphin.core.member.repository.ApplicationRepository;
import com.sabsari.dolphin.core.member.repository.UserGroupRepository;
import com.sabsari.dolphin.core.member.service.AdminService;
import com.sabsari.dolphin.core.value.ValueGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	private final static Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private UserGroupRepository userGroupRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;
		
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	ValueGenerator valueGenerator;
	
	private static final String ADMIN_GROUPKEY = "11e61fea-e649-41b5-a290-efa3e0255d67";
	private static final String ADMIN_CLIENTID = "feae12c1-80c9-4b56-a160-83c87aa56b6a";
	private static final String ADMIN_CLIENT_SECRET = "HKxyG6hiETcnfQjrUbQcJ7rqP0DBJITj";
	
	@Override
	@Transactional(readOnly=true)
	public UserGroup getUserGroup(String groupKey) {
		UserGroup userGroup = userGroupRepository.findOne(groupKey);
		if (userGroup == null) {
			logger.debug("The Group({}) is not found.", groupKey);
			throw new NotFoundGroupException(groupKey);
		}
		
		return userGroup;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Application getApplication(String clientId) {
		Application application = applicationRepository.findOne(clientId);
		if (application == null) {
			logger.debug("The ClientId({}) is not found.", clientId);
			throw new NotFoundClientIdException(clientId);
		}
		
		return application;
	}
	
	@Override
	public Application createAdmin() {
		Date now = new Date();
		UserGroup userGroup = new UserGroup();
		userGroup.setGroupKey(ADMIN_GROUPKEY);
		userGroup.setGroupName("administrator_group");
		userGroup.setModifyDate(now);
		userGroup.setRegistDate(now);
		userGroupRepository.save(userGroup);
		
		String encryptedSecret = passwordEncoder.encode(ADMIN_CLIENT_SECRET);		
		Application application = new Application();
		application.setClientId(ADMIN_CLIENTID);
		application.setClientSecret(encryptedSecret);
		application.setGroupKey(ADMIN_GROUPKEY);
		application.setAppName("administrator_application");
		application.setModifyDate(now);
		application.setRegistDate(now);
		
		Application result = new Application(ADMIN_CLIENTID, ADMIN_CLIENT_SECRET, ADMIN_GROUPKEY, "administrator_application");		
		applicationRepository.save(application);		
		return result;
	}
	
	@Override
	public String createUserGroup(String groupName) {		
		Date now = new Date();		
		String groupKey = valueGenerator.generateGroupKey();	
		UserGroup userGroup = new UserGroup();
		userGroup.setGroupKey(groupKey);
		userGroup.setGroupName(groupName);
		userGroup.setModifyDate(now);
		userGroup.setRegistDate(now);
		userGroupRepository.save(userGroup);
		
		return groupKey;
	}

	@Override
	public Application registApplication(String groupKey, String appName) {
		UserGroup userGroup = userGroupRepository.findOne(groupKey);
		if (userGroup == null) {
			logger.debug("The Group({}) is not found.", groupKey);
			throw new NotFoundGroupException(groupKey);
		}
		
		Date now = new Date();
		String clientId = valueGenerator.generateClientId();
		String clientSecret = valueGenerator.generateClientSecret();
		String encryptedSecret = passwordEncoder.encode(clientSecret);
		
		Application application = new Application();
		application.setClientId(clientId);
		application.setClientSecret(encryptedSecret);
		application.setGroupKey(groupKey);
		application.setAppName(appName);
		application.setModifyDate(now);
		application.setRegistDate(now);
		
		Application result = new Application(clientId, clientSecret, groupKey, appName);		
		applicationRepository.save(application);		
		return result;
	}
	
	@Override
	public String resetClientSecret(String clientId) {
		Application application = applicationRepository.findOne(clientId);
		if (application == null) {
			logger.debug("The ClientId({}) is not found.", clientId);
			throw new NotFoundClientIdException(clientId);
		}
		
		String newClientSecret = valueGenerator.generateClientSecret();
		String encryptedNewSecret = passwordEncoder.encode(newClientSecret);
		application.setClientSecret(encryptedNewSecret);
		application.setModifyDate(new Date());
		applicationRepository.save(application);
		
		return newClientSecret;
	}

	@Override
	public void deleteUserGroup(String groupKey) {
		UserGroup userGroup = userGroupRepository.findOne(groupKey);
		if (userGroup == null) {
			logger.debug("The Group({}) is not found.", groupKey);
			throw new NotFoundGroupException(groupKey);
		}
		
		userGroupRepository.delete(userGroup);		
	}

	@Override
	public void unregistApplication(String clientId) {
		Application application = applicationRepository.findOne(clientId);
		if (application == null) {
			logger.debug("The ClientId({}) is not found.", clientId);
			throw new NotFoundClientIdException(clientId);
		}
		
		applicationRepository.delete(application);		
	}
}
