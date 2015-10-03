package com.sabsari.dolphin.core.member.repository;

import com.sabsari.dolphin.core.member.domain.Authentication;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {
}
