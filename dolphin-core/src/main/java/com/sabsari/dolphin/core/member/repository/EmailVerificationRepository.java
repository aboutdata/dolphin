package com.sabsari.dolphin.core.member.repository;

import com.sabsari.dolphin.core.member.domain.EmailVerification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
	public EmailVerification findByVerificationValueAndCrc32(String verificationValue, Long crc32);
}
