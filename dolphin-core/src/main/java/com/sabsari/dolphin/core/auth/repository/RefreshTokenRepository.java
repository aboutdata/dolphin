package com.sabsari.dolphin.core.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sabsari.dolphin.core.auth.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	public RefreshToken findByRefreshToken(String refreshToken);
}
