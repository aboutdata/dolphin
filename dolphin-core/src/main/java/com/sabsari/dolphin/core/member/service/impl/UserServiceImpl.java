package com.sabsari.dolphin.core.member.service.impl;

import com.sabsari.dolphin.core.auth.domain.Authorization;
import com.sabsari.dolphin.core.auth.repository.AuthorizationRepository;
import com.sabsari.dolphin.core.auth.repository.RefreshTokenRepository;
import com.sabsari.dolphin.core.exception.business.DupEmailIdException;
import com.sabsari.dolphin.core.exception.business.EmailAlreadyVerifiedException;
import com.sabsari.dolphin.core.exception.business.NotFoundClientIdException;
import com.sabsari.dolphin.core.exception.business.NotFoundUserException;
import com.sabsari.dolphin.core.exception.system.NotFoundEmailVerificationException;
import com.sabsari.dolphin.core.history.domain.DeleteUserHistory;
import com.sabsari.dolphin.core.history.domain.UserHistory;
import com.sabsari.dolphin.core.history.domain.code.UserHistoryReason;
import com.sabsari.dolphin.core.history.repository.DeleteUserHistoryRepository;
import com.sabsari.dolphin.core.history.repository.UserHistoryRepository;
import com.sabsari.dolphin.core.member.domain.Application;
import com.sabsari.dolphin.core.member.domain.Authentication;
import com.sabsari.dolphin.core.member.domain.EmailVerification;
import com.sabsari.dolphin.core.member.domain.Permission;
import com.sabsari.dolphin.core.member.domain.Profile;
import com.sabsari.dolphin.core.member.domain.User;
import com.sabsari.dolphin.core.member.domain.code.AuthStatusCode;
import com.sabsari.dolphin.core.member.domain.code.Gender;
import com.sabsari.dolphin.core.member.domain.code.YesNo;
import com.sabsari.dolphin.core.member.repository.*;
import com.sabsari.dolphin.core.member.service.UserService;
import com.sabsari.dolphin.core.value.ValueGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * UserService Implement
 * 
 * @author sabsari
 * @date   2014. 2. 28.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private AuthenticationRepository authenticationRepository;
	
	@Autowired
	private EmailVerificationRepository emailVerificationRepository;
	
	@Autowired
	private AuthorizationRepository authorizationRepository;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private UserHistoryRepository userHistoryRepository;
	
	@Autowired
	private DeleteUserHistoryRepository deleteUserHistoryRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	ValueGenerator valueGenerator;
	
	@Override
	@Transactional(readOnly=true)
	public String getGroupKey(String userKey) {
		User user = userRepository.findByUserKey(userKey);		
        if (user == null) {
            logger.debug("The User({}) is not found.", userKey);
            throw new NotFoundUserException(userKey);
        }
        
        return user.getGroupKey();
	}

	@Override
	@Transactional(readOnly=true)
	public User getUser(String userKey) {		
		User user = userRepository.findByUserKey(userKey);		
        if (user == null) {
            logger.debug("The User({}) is not found.", userKey);
            throw new NotFoundUserException(userKey);
        }        
        // lazy 조회 필요
        user.getAuthentication();
        user.getProfile();
		return user;
	}
	
	@Override
	@Transactional(readOnly=true)
	public User getUser(String clientId, String emailId) {
		Application application = applicationRepository.findOne(clientId);
		if (application == null) {
			logger.debug("The application({}) is not found.", clientId);
			throw new NotFoundClientIdException(clientId);
		}
		String groupKey = application.getGroupKey();
		
		User user = userRepository.findByGroupKeyAndEmailId(groupKey, emailId);
		if (user == null) {
            logger.debug("The User({}) is not found.", emailId);
            throw new NotFoundUserException(groupKey, emailId);
        }		
		// lazy 조회 필요
		user.getAuthentication();
        user.getProfile();
		return user;
	}
	
	@Override
	@Transactional(readOnly=true)
	public boolean isEmailVerified(String userKey) {
		User user = userRepository.findByUserKey(userKey);		
        if (user == null) {
            logger.debug("The User({}) is not found.", userKey);
            throw new NotFoundUserException(userKey);
        }
        
        EmailVerification emailVerification = emailVerificationRepository.findOne(user.getUserSeq());
        
        if (emailVerification == null || emailVerification.getVerifiedDate() == null)
        	return false;
        else
        	return true;
	}
	
	@Override
	@Transactional(readOnly=true)
	public boolean isEmailIdAvailable(String clientId, String emailId) {
		// 그룹키 조회
		Application application = applicationRepository.findOne(clientId);
		if (application == null) {
			logger.debug("The application({}) is not found.", clientId);
			throw new NotFoundClientIdException(clientId);
		}
		
		User user = userRepository.findByGroupKeyAndEmailId(application.getGroupKey(), emailId);
		if (user == null)
			return true;
		else
			return false;
	}
	
	@Override
	public void verifyEmail(String userKey) {
		User user = userRepository.findByUserKey(userKey);		
        if (user == null) {
            logger.debug("The User({}) is not found.", userKey);
            throw new NotFoundUserException(userKey);
        }
        
        EmailVerification emailVerification = emailVerificationRepository.findOne(user.getUserSeq());        
        if (emailVerification == null) {
        	logger.debug("Not found email verificaiton info.");
        	throw new NotFoundEmailVerificationException();
        }        
        if (emailVerification.getVerifiedDate() != null) {
        	logger.debug("The email of the user({}) is already verified.", userKey);
        	throw new EmailAlreadyVerifiedException(userKey);
        }
        
        emailVerification.setVerifiedDate(new Date());
        emailVerificationRepository.save(emailVerification);
	}
	
	@Override
	@Transactional(readOnly=true)
	public boolean hasPermission(Long userSeq, String clientId) {
		Permission permission = permissionRepository.findByUserSeqAndClientId(userSeq, clientId);		
		
		if (permission == null)
			return false;
		else
			return true;
	}
	
	@Override
	@Transactional(readOnly=true)
	public boolean hasPermission(String userKey, String clientId) {
		User user = userRepository.findByUserKey(userKey);		
        if (user == null) {
            logger.debug("The User({}) is not found.", userKey);
            throw new NotFoundUserException(userKey);
        }
        
        Permission permission = permissionRepository.findByUserSeqAndClientId(user.getUserSeq(), clientId);		
		
		if (permission == null)
			return false;
		else
			return true;
	}
	
	@Override
	public User createUser(String clientId, String emailId,
			AuthStatusCode authStatusCode, YesNo emailVerified, String firstName, String lastName,
			String password, String phone, String birthday, Gender gender) {
		
		// 그룹키 조회
		Application application = applicationRepository.findOne(clientId);
		if (application == null) {
			logger.debug("The application({}) is not found.", clientId);
			throw new NotFoundClientIdException(clientId);
		}
		String groupKey = application.getGroupKey();
		
		// user		
		String generatedUserKey = valueGenerator.generateUserKey();
		User user = new User(generatedUserKey, groupKey, emailId);
		User newUser = null;		
		try {
			newUser = userRepository.saveAndFlush(user);
		}
		catch (DataIntegrityViolationException ex) {
			logger.debug("The email emailId({}) already exists.", emailId);
			throw new DupEmailIdException(emailId);
		}
		
		Long userSeq = newUser.getUserSeq();
				
		// profile
		Profile profile = new Profile(userSeq, firstName, lastName, birthday, phone, gender);
		Profile savedProfile = profileRepository.save(profile);
		user.setProfile(savedProfile);				
		
		// permission
		createPermission(userSeq, clientId);
		
		// authentication
		Authentication authentication = createAuthInfo(userSeq, authStatusCode, password);
		user.setAuthentication(authentication);
		
		// email verification : 메일 발송은 MailService 통에서 할것 
		createEmailVerification(userSeq, emailVerified);
				
		return user;
	}
		
	@Override
	public User createUser(String clientId, String emailId,
			String firstName, String lastName, String password, String phone, String birthday, Gender gender) {		
		return this.createUser(clientId, emailId, AuthStatusCode.NORMAL, YesNo.NO, firstName, lastName, password, phone, birthday, gender);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void deleteUser(String userKey) {		
		User user = userRepository.findByUserKey(userKey);		
        if (user == null) {
            logger.debug("The User({}) is not found.", userKey);
            throw new NotFoundUserException(userKey);
        }    
		
		Long userSeq = user.getUserSeq();

		emailVerificationRepository.delete(userSeq);
		authenticationRepository.delete(userSeq);
		
		List<Permission> permissions = permissionRepository.findByUserSeq(userSeq);
		if (permissions != null && permissions.size() != 0) {
			permissionRepository.delete(permissions);
		}		
		profileRepository.delete(userSeq);
		userRepository.delete(user);
		
		Authorization authorization = authorizationRepository.findByOwner(userKey);		
		if (authorization != null) {		
			refreshTokenRepository.delete(authorization.getIdSeq());
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void deleteUser_v1_1(String userKey, String clientId) {
		User user = userRepository.findByUserKey(userKey);		
        if (user == null) {
            logger.debug("The User({}) is not found.", userKey);
            throw new NotFoundUserException(userKey);
        }    
		
        deleteUserHistoryRepository.save(new DeleteUserHistory(user.getEmailId(), userKey, user.getProfile().getFirstName(), clientId));
		Long userSeq = user.getUserSeq();

		emailVerificationRepository.delete(userSeq);
		authenticationRepository.delete(userSeq);
		
		List<Permission> permissions = permissionRepository.findByUserSeq(userSeq);
		if (permissions != null && permissions.size() != 0) {
			permissionRepository.delete(permissions);
		}		
		profileRepository.delete(userSeq);
		userRepository.delete(user);
		
		Authorization authorization = authorizationRepository.findByOwner(userKey);		
		if (authorization != null) {		
			refreshTokenRepository.delete(authorization.getIdSeq());
		}
	}
	
	@Override
	public User updateUser(String userKey, String firstName, String lastName, String birthday, String phone, Gender gender) {
		User user = userRepository.findByUserKey(userKey);
		if (user == null) {
			logger.debug("The User({}) is not found.", userKey);
			throw new NotFoundUserException(userKey);
		}
		
		Profile profile = user.getProfile();
		
		if(firstName != null)
			profile.setFirstName(firstName);
		if(lastName != null)
			profile.setLastName(lastName);
		if(birthday != null)
			profile.setBirthday(birthday);
		if(gender != null)
			profile.setGender(gender.getCode());
		if(phone != null)
			profile.setPhone(phone);
		
		profile.setModifyDate(new Date());
		
		User updated = userRepository.save(user);
		updated.getProfile(); //lazy
		
		userHistoryRepository.save(new UserHistory(userKey, null, UserHistoryReason.MODIFY_PROFILE));
		return updated;
	}
	
	@Override
	public void updateEmailId(String userKey, String updateEmailId, YesNo emailVerified) {
		User user = userRepository.findByUserKey(userKey);
		if (user == null) {
			logger.debug("The User({}) is not found.", userKey);
			throw new NotFoundUserException(userKey);
		}				
		
		String oldEmailId = user.getEmailId();
		Date now = new Date();
		user.setEmailId(updateEmailId);
		user.setModifyDate(now);
		
		try {
			userRepository.saveAndFlush(user);
		}
		catch (DataIntegrityViolationException ex) {
			logger.debug("The email emailId({}) already exists.", updateEmailId);
			throw new DupEmailIdException(updateEmailId);
		}
		
		userHistoryRepository.save(new UserHistory(userKey, oldEmailId, UserHistoryReason.MODIFY_EMAIL));
		
		// email 확인용 정보 새로 생성
		createEmailVerification(user.getUserSeq(), emailVerified);
	}
	
	@Override
	public void updateEmailId(String userKey, String updateEmailId) {
		this.updateEmailId(userKey, updateEmailId, YesNo.NO);
	}
	
	private void createPermission(Long userSeq, String clientId) {
		Permission permission = new Permission(userSeq, clientId);
		permissionRepository.save(permission);
	}	
	
	private Authentication createAuthInfo(Long userSeq, AuthStatusCode authStatusCode, String plainPassword) {
		String encodedPassword = passwordEncoder.encode(plainPassword);
		Authentication emailAuthentication = new Authentication(userSeq, encodedPassword, authStatusCode);
		return authenticationRepository.save(emailAuthentication);
	}
	
	private void createEmailVerification(Long userSeq, YesNo emailVerified) {
		String veficationValue = valueGenerator.generateEmailVerificationValue();
		EmailVerification emailVerification = new EmailVerification(userSeq, veficationValue);		
		if (emailVerified == YesNo.YES) {
			emailVerification.setVerifiedDate(new Date());
		}		
		emailVerificationRepository.save(emailVerification);
	}
}