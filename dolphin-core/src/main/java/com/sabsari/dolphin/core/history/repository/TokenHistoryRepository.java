package com.sabsari.dolphin.core.history.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sabsari.dolphin.core.history.domain.TokenHistory;

public interface TokenHistoryRepository extends JpaRepository<TokenHistory, Long> {
	public Page<TokenHistory> findByOwner(String Owner, Pageable page);
	public Page<TokenHistory> findByOwnerAndReasonCode(String Owner, String reasonCode, Pageable page);
	
	@Modifying
	@Query("delete from TokenHistory where registDate < :to")
	public int delete(@Param("to") Date to);
}
