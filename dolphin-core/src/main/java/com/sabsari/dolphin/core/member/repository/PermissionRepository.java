package com.sabsari.dolphin.core.member.repository;

import java.util.List;

import com.sabsari.dolphin.core.member.domain.Permission;
import com.sabsari.dolphin.core.member.domain.Permission.PermissionId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, PermissionId> {
	
	public List<Permission> findByUserSeq(Long userSeq);
	
	public Permission findByUserSeqAndClientId(Long userSeq, String clientId);
}
