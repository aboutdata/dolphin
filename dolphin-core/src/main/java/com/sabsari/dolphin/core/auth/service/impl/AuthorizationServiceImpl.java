package com.sabsari.dolphin.core.auth.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sabsari.dolphin.core.auth.domain.AccessToken;
import com.sabsari.dolphin.core.auth.domain.Authorization;
import com.sabsari.dolphin.core.auth.domain.RefreshToken;
import com.sabsari.dolphin.core.auth.domain.code.GrantType;
import com.sabsari.dolphin.core.auth.domain.code.HttpAuthenticationScheme;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.auth.domain.code.TokenTypeHint;
import com.sabsari.dolphin.core.auth.exception.InvalidStatusException;
import com.sabsari.dolphin.core.auth.exception.InvalidTokenTypeException;
import com.sabsari.dolphin.core.auth.exception.InvalidUserException;
import com.sabsari.dolphin.core.auth.exception.NotFoundClientException;
import com.sabsari.dolphin.core.auth.exception.NotFoundTokenException;
import com.sabsari.dolphin.core.auth.exception.NotFoundUserException;
import com.sabsari.dolphin.core.auth.exception.NotFoundUsernameException;
import com.sabsari.dolphin.core.auth.exception.status401.ClientSecretVerificationFailException;
import com.sabsari.dolphin.core.auth.exception.status401.NotFoundAccessTokenException;
import com.sabsari.dolphin.core.auth.exception.status401.TokenExpiredException;
import com.sabsari.dolphin.core.auth.repository.AccessTokenRepository;
import com.sabsari.dolphin.core.auth.repository.AuthorizationRepository;
import com.sabsari.dolphin.core.auth.repository.RefreshTokenRepository;
import com.sabsari.dolphin.core.auth.service.AuthorizationService;
import com.sabsari.dolphin.core.common.CommonConstants;
import com.sabsari.dolphin.core.history.domain.TokenHistory;
import com.sabsari.dolphin.core.history.domain.code.TokenHistoryReason;
import com.sabsari.dolphin.core.history.repository.TokenHistoryRepository;
import com.sabsari.dolphin.core.member.domain.Application;
import com.sabsari.dolphin.core.member.domain.Authentication;
import com.sabsari.dolphin.core.member.domain.Permission;
import com.sabsari.dolphin.core.member.domain.User;
import com.sabsari.dolphin.core.member.domain.code.AuthStatusCode;
import com.sabsari.dolphin.core.member.repository.ApplicationRepository;
import com.sabsari.dolphin.core.member.repository.AuthenticationRepository;
import com.sabsari.dolphin.core.member.repository.PermissionRepository;
import com.sabsari.dolphin.core.member.repository.UserRepository;
import com.sabsari.dolphin.core.value.ValueGenerator;

@Service
@Transactional
public class AuthorizationServiceImpl implements AuthorizationService {

	private final static Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private AccessTokenRepository accessTokenRepository;
	
	@Autowired
	private AuthenticationRepository authenticationRepository;
	
	@Autowired
	private AuthorizationRepository authorizationRepository;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private TokenHistoryRepository tokenHistoryRepository;
	
	@Autowired
	private ValueGenerator valueGenerator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional(readOnly=true)
	public boolean hasPermission(String userKey, String clientId) {
		User user = userRepository.findByUserKey(userKey);		
        if (user == null) {
            logger.debug("The User({}) is not found.", userKey);
            throw new NotFoundUserException();
        }
        
        Permission permission = permissionRepository.findByUserSeqAndClientId(user.getUserSeq(), clientId);		
		
		if (permission == null)
			return false;
		else
			return true;
	}
	
	@Override
	@Transactional(noRollbackFor={InvalidUserException.class, InvalidStatusException.class})
	public String verifyUserPassword(String clientId, String emailId, String password) {
		Application application = applicationRepository.findOne(clientId);
		if (application == null) {
			logger.debug("The application({}) is not found.", clientId);
			throw new NotFoundClientException();
		}
		String groupKey = application.getGroupKey();
		
		User user = userRepository.findByGroupKeyAndEmailId(groupKey, emailId);
		if (user == null) {
            logger.debug("The user({}) is not found.", emailId);
            throw new NotFoundUsernameException();
        }
		
        Authentication authentication = user.getAuthentication();
		String target = authentication.getPassword();
		AuthStatusCode statusCode = authentication.getAuthStatusCode();
		
		// password검증
		if (!passwordEncoder.matches(password, target)) {
			// 실패 카운트 증가
			authentication.setAuthFailCount(authentication.getAuthFailCount() + 1);
			authenticationRepository.save(authentication);
			logger.debug("The password for the email ID({}) of group({}) is incorrect", emailId, groupKey);
			throw new InvalidUserException();
		}
		
		// 인증 상태코드 확인
		if (AuthStatusCode.LOCKED == statusCode || AuthStatusCode.LOGIN_IMPOSSIBLE == statusCode) {
			logger.debug("The email auth status of the user({}) is not normal({}).", emailId, statusCode);
			throw new InvalidStatusException();
		}
		
		// 실패 카운트 확인 일단은 10 회이상이면 로그인 잠금
		if (authentication.getAuthFailCount() >= CommonConstants.MAX_LOGIN_FAIL_COUNT) {
			authentication.setAuthStatusCode(AuthStatusCode.LOCKED);
			authenticationRepository.save(authentication);
			logger.debug("The email auth failure count({}) is over.", authentication.getAuthFailCount());
			throw new InvalidStatusException();
		}
		
		// 실패 카운트 0으로 리셋
		if (authentication.getAuthFailCount() != 0) {
			authentication.setAuthFailCount(0);
			authenticationRepository.save(authentication);
		}
		
		return user.getUserKey();
	}
	

	@Override
	@Transactional(readOnly=true)
	public Application verifyClientSecret(String clientId, String clientSecret) {
		Application application = applicationRepository.findOne(clientId);
		if (application == null) {
			logger.debug("The application({}) is not found.", clientId);
			throw new ClientSecretVerificationFailException(HttpAuthenticationScheme.BASIC);
		}
		
		String target = application.getClientSecret();
		
		if (!passwordEncoder.matches(clientSecret, target)) {
			logger.debug("The ClientSecret of the clientId({}) is incorrect.", clientId);
			throw new ClientSecretVerificationFailException(HttpAuthenticationScheme.BASIC);
		}
		
		return application;
	}
	
	@Override 
	public AccessToken issueAdminToken(String clientId) {
		Application application = applicationRepository.findOne(clientId);
		if (application == null) {
			logger.debug("The application({}) is not found.", clientId);
			throw new NotFoundClientException();
		}
		
		Calendar refreshDate = Calendar.getInstance();
		refreshDate.set(2020, 11, 31);
		
		Authorization authorization = authorizationRepository.findByOwner(clientId);
		if (authorization == null) {	// 새로 발급
			tokenHistoryRepository.save(new TokenHistory(clientId, TokenHistoryReason.ISSUE_TOKEN));			
			authorization = new Authorization(clientId, GrantType.CLIENT_CREDENTIALS, Role.ADMIN);
			Authorization savedAuthorization = authorizationRepository.save(authorization);
			
			AccessToken accessToken = new AccessToken(savedAuthorization.getIdSeq(), valueGenerator.generateAccessToken());
			accessToken.setRefreshDate(refreshDate.getTime());
			accessToken.setAuthorization(savedAuthorization);
			
			return accessTokenRepository.save(accessToken);
		}
		
		Date now = new Date();
		AccessToken accessToken = accessTokenRepository.findOne(authorization.getIdSeq());
		if (!accessToken.isExpired()) {
			return accessToken;
		}
		
		tokenHistoryRepository.save(new TokenHistory(clientId, TokenHistoryReason.ISSUE_TOKEN));		
		accessToken.setAccessToken(valueGenerator.generateAccessToken());
		accessToken.setIssueDate(now);
		accessToken.setRefreshDate(refreshDate.getTime());
		return accessTokenRepository.save(accessToken);
	}
		
	@Override
	public AccessToken issueAccessToken(String clientId) {
		return issueAccessToken(clientId, Role.CLIENT);
	}
	
	@Override
	public AccessToken issueAccessToken(String clientId, Role role) {
		Application application = applicationRepository.findOne(clientId);
		if (application == null) {
			logger.debug("The application({}) is not found.", clientId);
			throw new NotFoundClientException();
		}
		
		Authorization authorization = authorizationRepository.findByOwner(clientId);
		if (authorization == null) {	// 새로 발급
			tokenHistoryRepository.save(new TokenHistory(clientId, TokenHistoryReason.ISSUE_TOKEN));			
			authorization = new Authorization(clientId, GrantType.CLIENT_CREDENTIALS, role);
			Authorization savedAuthorization = authorizationRepository.save(authorization);
			
			AccessToken accessToken = new AccessToken(savedAuthorization.getIdSeq(), valueGenerator.generateAccessToken());
			accessToken.setAuthorization(savedAuthorization);
			
			return accessTokenRepository.save(accessToken);
		}
				
		AccessToken accessToken = accessTokenRepository.findOne(authorization.getIdSeq());
		if (!accessToken.isExpired()) {
			return accessToken;
		}
		
		tokenHistoryRepository.save(new TokenHistory(clientId, TokenHistoryReason.ISSUE_TOKEN));		
		Date now = new Date();
		accessToken.setAccessToken(valueGenerator.generateAccessToken());
		accessToken.setIssueDate(now);
		accessToken.setRefreshDate(now);
		return accessTokenRepository.save(accessToken);
	}
	
	@Override
	public RefreshToken issueRefreshToken(String userKey) {
		return issueRefreshToken(userKey, Role.USER);
	}
	
	@Override
	public RefreshToken issueRefreshToken(String userKey, Role role) {
		User user = userRepository.findByUserKey(userKey);		
        if (user == null) {
            logger.debug("The User({}) is not found.", userKey);
            throw new NotFoundUsernameException();
        }
		
		Authorization authorization = authorizationRepository.findByOwner(userKey);
		if (authorization == null) {	// 새로 발급
			tokenHistoryRepository.save(new TokenHistory(userKey, TokenHistoryReason.ISSUE_TOKEN));			
			authorization = new Authorization(userKey, GrantType.PASSWORD, role);
			Authorization savedAuthorization = authorizationRepository.save(authorization);
			Long idSeq = savedAuthorization.getIdSeq();
			
			AccessToken accessToken = new AccessToken(idSeq, valueGenerator.generateAccessToken());
			accessToken.setAuthorization(savedAuthorization);
			AccessToken savedAccessToken = accessTokenRepository.save(accessToken);
			
			RefreshToken refreshToken = new RefreshToken(idSeq, valueGenerator.generateRefreshToken());
			refreshToken.setAccessToken(savedAccessToken);
			
			return refreshTokenRepository.save(refreshToken);
		}
				
		RefreshToken refreshToken = refreshTokenRepository.findOne(authorization.getIdSeq());
		if (!refreshToken.getAccessToken().isExpired()) {
			return refreshToken;
		}		
		
		tokenHistoryRepository.save(new TokenHistory(userKey, TokenHistoryReason.ISSUE_TOKEN));
		Date now = new Date();
		refreshToken.setRefreshToken(valueGenerator.generateRefreshToken());
		refreshToken.getAccessToken().setAccessToken(valueGenerator.generateAccessToken());
		refreshToken.getAccessToken().setIssueDate(now);
		refreshToken.getAccessToken().setRefreshDate(now);
		return refreshTokenRepository.save(refreshToken);
	}

	@Override
	public RefreshToken refreshToken(String refreshToken) {
		RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken);
		
		if (token == null) {
			logger.debug("The refreshToken({}) is not found.", refreshToken);
			throw new NotFoundTokenException();
		}
		
		if (token.isExpired()) {
			logger.debug("The refreshToken({}) is expired.", refreshToken);
			throw new TokenExpiredException(HttpAuthenticationScheme.BEARER);
		}
		
		if (!token.getAccessToken().isExpired()) {
			return token;
		}
		
		tokenHistoryRepository.save(new TokenHistory(token.getAccessToken().getOwner(), TokenHistoryReason.REFRESH_TOKEN));				
		token.getAccessToken().setAccessToken(valueGenerator.generateAccessToken());
		token.getAccessToken().setRefreshDate(new Date());
		token.setRefreshToken(valueGenerator.generateRefreshToken());				
		return refreshTokenRepository.save(token);
	}

	@Override
	public void revokeToken(TokenTypeHint tokenTypeHint, String token) {
		if (TokenTypeHint.ACCESS_TOKEN == tokenTypeHint) {
			AccessToken accessToken = accessTokenRepository.findByAccessToken(token);			
			if (accessToken == null) {
				logger.debug("The accessToken({}) is not found.", token);
				throw new NotFoundTokenException();
			}
			
			tokenHistoryRepository.save(new TokenHistory(accessToken.getOwner(), TokenHistoryReason.REVOKE_TOKEN));			
			accessTokenRepository.delete(accessToken);
            RefreshToken refreshToken = refreshTokenRepository.findOne(accessToken.getIdSeq());
            if (refreshToken != null) {
                refreshTokenRepository.delete(refreshToken);
            }           
		}
		else if (TokenTypeHint.REFRESH_TOKEN == tokenTypeHint) {
			RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token);			
			if (refreshToken == null) {
				logger.debug("The refreshToken({}) is not found.", token);
				throw new NotFoundTokenException();
			}
			
			tokenHistoryRepository.save(new TokenHistory(refreshToken.getAccessToken().getOwner(), TokenHistoryReason.REVOKE_TOKEN));
			refreshTokenRepository.delete(refreshToken);
		}
		else {
			logger.debug("Invalid token type to revoke({}).", tokenTypeHint.getHint());
			throw new InvalidTokenTypeException();
		}		
	}
	
	@Override
	@Transactional(readOnly=true)
	public AccessToken verifyAccessToken(String accessToken) {				
		AccessToken token = accessTokenRepository.findByAccessToken(accessToken);		
		if (token == null) {
			logger.debug("The accessToken({}) is not found.", accessToken);
			throw new NotFoundAccessTokenException(HttpAuthenticationScheme.BEARER);
		}
		
		if (token.isExpired()) {
			logger.debug("The accessToken({}) is expired.", accessToken);
			throw new TokenExpiredException(HttpAuthenticationScheme.BEARER);
		}
				
		return token;
	}
}
