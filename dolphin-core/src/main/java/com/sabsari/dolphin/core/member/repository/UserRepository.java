package com.sabsari.dolphin.core.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sabsari.dolphin.core.member.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUserKey(String userKey);

	public User findByGroupKeyAndEmailId(String groupKey, String emailId);
}
