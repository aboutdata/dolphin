package com.sabsari.dolphin.core.auth.repository;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sabsari.dolphin.core.auth.domain.AccessToken;
import com.sabsari.dolphin.core.auth.domain.Authorization;
import com.sabsari.dolphin.core.auth.domain.RefreshToken;
import com.sabsari.dolphin.core.auth.domain.code.GrantType;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.auth.repository.AccessTokenRepository;
import com.sabsari.dolphin.core.auth.repository.AuthorizationRepository;
import com.sabsari.dolphin.core.auth.repository.RefreshTokenRepository;
import com.sabsari.dolphin.core.config.ContextConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class})
@ActiveProfiles(value = "dev")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorizationRepositoryTest {

	@Autowired
	private AuthorizationRepository authorizationRepository;
	
	@Autowired
	private AccessTokenRepository accessTokenRepository;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	private static final String CLIENT_ID = "dc32c017-1c1c-4f0b-9edf-d3b42ff71e5P";
	private static final String ACCESS_TOKEN = "accessToken";
	private static final String REFRESH_TOKEN = "refreshToken";
	private static Long idSeq = 0L;
	
	@Test
	@Transactional
	@Rollback(false)
	public void _1_권한관리정보생성() {
		
		Authorization authorization = new Authorization(CLIENT_ID, GrantType.CLIENT_CREDENTIALS, Role.ADMIN);
		Authorization savedAuthorization = authorizationRepository.save(authorization);		
		
		idSeq = savedAuthorization.getIdSeq();
		
		AccessToken accessToken = new AccessToken(idSeq, ACCESS_TOKEN);
		accessTokenRepository.save(accessToken);
		
		RefreshToken refreshToken = new RefreshToken(idSeq, REFRESH_TOKEN);
		refreshTokenRepository.save(refreshToken);
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void _2_권한관리정보조회() {
		Authorization authorization = authorizationRepository.findOne(idSeq);
		assertEquals(CLIENT_ID, authorization.getOwner());
		
		Authorization authorization_ = authorizationRepository.findByOwner(authorization.getOwner());
		assertEquals(idSeq, authorization_.getIdSeq());
		
		AccessToken accessToken = accessTokenRepository.findByAccessToken(ACCESS_TOKEN);
		assertEquals(idSeq, accessToken.getIdSeq());
		assertEquals(CLIENT_ID, accessToken.getAuthorization().getOwner());		
		
		RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(REFRESH_TOKEN);
		assertEquals(idSeq, refreshToken.getAccessToken().getIdSeq());
		assertEquals(CLIENT_ID, refreshToken.getAccessToken().getAuthorization().getOwner());		
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void _2_권한관리업데이트() {		
		RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(REFRESH_TOKEN);
		assertEquals(idSeq, refreshToken.getAccessToken().getIdSeq());
		assertEquals(CLIENT_ID, refreshToken.getAccessToken().getAuthorization().getOwner());
		
		refreshToken.getAccessToken().getAuthorization().setLevel(0);
		RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);
		
		assertEquals(0, savedRefreshToken.getAccessToken().getAuthorization().getLevel());
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void _3_권한관리정보삭제() {
		refreshTokenRepository.delete(idSeq);
//		accessTokenRepository.delete(idSeq);
//		authorizationRepository.delete(idSeq);
	}
}
