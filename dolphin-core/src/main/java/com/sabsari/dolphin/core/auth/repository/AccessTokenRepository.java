package com.sabsari.dolphin.core.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sabsari.dolphin.core.auth.domain.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
	public AccessToken findByAccessToken(String accessToken);
}
