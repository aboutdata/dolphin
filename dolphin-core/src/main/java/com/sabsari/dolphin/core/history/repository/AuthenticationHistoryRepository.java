package com.sabsari.dolphin.core.history.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sabsari.dolphin.core.history.domain.AuthenticationHistory;

public interface AuthenticationHistoryRepository extends JpaRepository<AuthenticationHistory, Long> {
	public Page<AuthenticationHistory> findByUserKey(String userKey, Pageable page);
	public Page<AuthenticationHistory> findByUserKeyAndReasonCode(String userKey, String reasonCode, Pageable page);
	
	@Modifying
	@Query("delete from AuthenticationHistory where registDate < :to")
	public int delete(@Param("to") Date to);
}
