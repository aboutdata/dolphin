package com.sabsari.dolphin.core.member.repository;

import java.util.Date;
import java.util.List;

//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import com.sabsari.dolphin.repository.dao.DomainDao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sabsari.dolphin.core.config.ContextConfig;
import com.sabsari.dolphin.core.member.domain.Application;
import com.sabsari.dolphin.core.member.domain.Authentication;
import com.sabsari.dolphin.core.member.domain.EmailVerification;
import com.sabsari.dolphin.core.member.domain.Permission;
import com.sabsari.dolphin.core.member.domain.Profile;
import com.sabsari.dolphin.core.member.domain.User;
import com.sabsari.dolphin.core.member.domain.UserGroup;
import com.sabsari.dolphin.core.member.domain.Permission.PermissionId;
import com.sabsari.dolphin.core.member.domain.code.AuthStatusCode;
import com.sabsari.dolphin.core.member.domain.code.Gender;
import com.sabsari.dolphin.core.member.repository.ApplicationRepository;
import com.sabsari.dolphin.core.member.repository.AuthenticationRepository;
import com.sabsari.dolphin.core.member.repository.EmailVerificationRepository;
import com.sabsari.dolphin.core.member.repository.PermissionRepository;
import com.sabsari.dolphin.core.member.repository.ProfileRepository;
import com.sabsari.dolphin.core.member.repository.UserGroupRepository;
import com.sabsari.dolphin.core.member.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class})
@ActiveProfiles(value = "dev")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JpaRepositoryTest {
	
	@Autowired
	private UserGroupRepository userGroupRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationRepository authenticationRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private EmailVerificationRepository emailVerificationRepository;	
	
//	@Autowired
//	private DomainDao criteriaDao;
//	
//	@PersistenceContext
//	private EntityManager entityManager;
	
	private static final String GROUP_KEY 		= "7d0837e9-b5d4-48bc-8718-8342f78a451P";
	private static final String CLIENT_ID 		= "dc32c017-1c1c-4f0b-9edf-d3b42ff71e5P";
	private static final String CLIENT_SECRET 	= "QFtVLRZ1mHxmPTZo5u0wFS0tXye8iSdaONvVCqi2A8IAwGpTvHelDDILzPlP";
	private static final String USER_KEY 		= "0ed99000-0c52-4b31-8d58-bb56ee8b15bP";
	private static final String VERIFICATION_VALUE = "iRetsFcBJFbIJgseEkFJZVGWaTkyZqW5NzpUJoKWjDZfYafotNsS9Crzo";
	
	private static final String EMAIL_ID		= "sabsabsab@gg.com";
	private static final String FIRST_NAME		= "first";
	private static final String LAST_NAME		= "last";
	private static final String GENDER			= Gender.MALE.getCode();
	private static final String BIRTHDAY		= "19990101";
	
	private static final String AUTH_STATUS_CODE = AuthStatusCode.NORMAL.getCode();
	private static final String PASSWORD		= "aaaa1111";
	
	private static final Date now = new Date();
	
	private static Long userSeq = 0L;
	private static Long crc32 = 941181854L;
		
	@Test
	@Transactional
	@Rollback(false)
	public void _1_test_도메인객체생성() {
		UserGroup userGroup = new UserGroup();
		userGroup.setGroupKey(GROUP_KEY);
		userGroup.setGroupName("test_user_group");
		userGroup.setModifyDate(now);
		userGroup.setRegistDate(now);		
		userGroupRepository.save(userGroup);
		
		User user = new User();
		user.setUserKey(USER_KEY);
		user.setGroupKey(GROUP_KEY);
		user.setEmailId(EMAIL_ID);
		user.setModifyDate(now);
		user.setRegistDate(now);
		User newUser = userRepository.save(user);
		userSeq = newUser.getUserSeq();
		
		Profile profile = new Profile();
		profile.setUserSeq(userSeq);
		profile.setFirstName(FIRST_NAME);
		profile.setLastName(LAST_NAME);
		profile.setBirthday(BIRTHDAY);
		profile.setGender(GENDER);
		profile.setModifyDate(now);
		profile.setRegistDate(now);
		profileRepository.save(profile);
		
		Application application = new Application();
		application.setClientId(CLIENT_ID);
		application.setClientSecret(CLIENT_SECRET);
		application.setAppName("test_application");
		application.setGroupKey(GROUP_KEY);;
		application.setModifyDate(now);
		application.setRegistDate(now);
		applicationRepository.save(application);
		
		Authentication authentication = new Authentication();
		authentication.setUserSeq(userSeq);
		authentication.setPassword(PASSWORD);
		authentication.setAuthFailCount(1);
		authentication.setAuthStatusCode(AuthStatusCode.search(AUTH_STATUS_CODE));		
		authentication.setModifyDate(now);
		authentication.setRegistDate(now);
		authenticationRepository.save(authentication);
		
		Permission permission = new Permission();
		permission.setUserSeq(userSeq);
		permission.setClientId(CLIENT_ID);
		permission.setRegistDate(now);
		permissionRepository.save(permission);
		
		EmailVerification emailVerification = new EmailVerification();
		emailVerification.setUserSeq(userSeq);
		emailVerification.setVerificationValue(VERIFICATION_VALUE);
		emailVerification.setCrc32(crc32);
		emailVerification.setRegistDate(now);
		emailVerificationRepository.save(emailVerification);
	}
	
	@Test
	@Transactional(readOnly=true)
	@Rollback(false)
	public void _2_test_도메인객체조회() {
		UserGroup userGroup = userGroupRepository.findOne(GROUP_KEY);
		Application application = applicationRepository.findOne(CLIENT_ID);
		User user = userRepository.findByUserKey(USER_KEY);
		Profile profile = profileRepository.findOne(userSeq);
		Authentication authentication = authenticationRepository.findOne(user.getUserSeq());
		PermissionId permissionId = new PermissionId(userSeq, CLIENT_ID); 
		Permission permission = permissionRepository.findOne(permissionId);
		EmailVerification emailVerification = emailVerificationRepository.findOne(userSeq);
		List<Permission> permissions = permissionRepository.findByUserSeq(userSeq);
		Permission permission_ = permissionRepository.findByUserSeqAndClientId(userSeq, CLIENT_ID);
		
		assertThat(GROUP_KEY, is(application.getUserGroup().getGroupKey()));
		assertThat(CLIENT_ID, is(application.getClientId()));
		assertThat(CLIENT_SECRET, is(userGroup.getApplications().get(0).getClientSecret()));
		assertThat(USER_KEY, is(user.getUserKey()));
		assertThat(userSeq, is(user.getUserSeq()));
		assertThat(EMAIL_ID, is(user.getEmailId()));
		assertThat(FIRST_NAME, is(user.getProfile().getFirstName()));
		assertThat(BIRTHDAY, is(profile.getBirthday()));
		assertThat(PASSWORD, is(user.getAuthentication().getPassword()));
		assertThat(AUTH_STATUS_CODE, is(authentication.getAuthStatusCode().getCode()));
		assertThat(userSeq, is(permission.getUserSeq()));
		assertThat(VERIFICATION_VALUE, is(emailVerification.getVerificationValue()));
		assertThat(crc32, is(emailVerification.getCrc32()));
		assertThat(CLIENT_ID, is(permissions.get(0).getClientId()));
		assertThat(CLIENT_ID, is(permission_.getClientId()));
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void _3_test_도메인객체클리어() {
		authenticationRepository.delete(userSeq);
		applicationRepository.delete(CLIENT_ID);
		userRepository.delete(userSeq);
		userGroupRepository.delete(GROUP_KEY);
		profileRepository.delete(userSeq);
		permissionRepository.delete(new PermissionId(userSeq, CLIENT_ID));
		emailVerificationRepository.delete(userSeq);
	}
}
