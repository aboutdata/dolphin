package com.sabsari.dolphin.core.history.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sabsari.dolphin.core.history.domain.UserHistory;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
	public Page<UserHistory> findByUserKey(String userKey, Pageable page);
	public Page<UserHistory> findByUserKeyAndReasonCode(String userKey, String reasonCode, Pageable page);
	
	@Modifying
	@Query("delete from UserHistory where registDate < :to")
	public int delete(@Param("to") Date to);
}
