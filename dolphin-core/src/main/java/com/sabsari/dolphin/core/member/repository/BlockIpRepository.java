package com.sabsari.dolphin.core.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sabsari.dolphin.core.member.domain.BlockIp;

public interface BlockIpRepository extends JpaRepository<BlockIp, String> {

}
