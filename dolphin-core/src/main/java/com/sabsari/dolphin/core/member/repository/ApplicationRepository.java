package com.sabsari.dolphin.core.member.repository;

import com.sabsari.dolphin.core.member.domain.Application;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, String> {
}
