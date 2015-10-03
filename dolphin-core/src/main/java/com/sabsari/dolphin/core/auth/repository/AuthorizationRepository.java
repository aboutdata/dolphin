package com.sabsari.dolphin.core.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sabsari.dolphin.core.auth.domain.Authorization;

public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {
	public Authorization findByOwner(String owner);
}
