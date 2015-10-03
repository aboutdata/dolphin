package com.sabsari.dolphin.core.history.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sabsari.dolphin.core.history.domain.DeleteUserHistory;

public interface DeleteUserHistoryRepository extends JpaRepository<DeleteUserHistory, Long> {
	public DeleteUserHistory findByClientIdAndUserKey(String clientId, String userKey);
	public Page<DeleteUserHistory> findByClientIdAndEmailId(String clientId, String emailId, Pageable page);	
	public Page<DeleteUserHistory> findByClientId(String clientId, Pageable page);
	
	@Modifying
	@Query("delete from DeleteUserHistory where registDate < :to")
	public int delete(@Param("to") Date to);
}
