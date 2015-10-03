package com.sabsari.dolphin.core.member.repository;

import com.sabsari.dolphin.core.member.domain.UserGroup;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup, String>  {

}
