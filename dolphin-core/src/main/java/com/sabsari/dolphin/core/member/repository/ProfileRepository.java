package com.sabsari.dolphin.core.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sabsari.dolphin.core.member.domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

}
