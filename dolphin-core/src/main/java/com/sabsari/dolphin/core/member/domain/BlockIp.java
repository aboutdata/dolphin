package com.sabsari.dolphin.core.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="BLOCK_IP")
@Data
public class BlockIp {
	
	public BlockIp() {
		
	}

	public BlockIp(String ip) {
		this.ip = ip;
		this.registDate = new Date();
	}
	
	/**
	 * 이메일 아이디
	 */
	@Id
	@Column(name="IP", length=18, nullable=false)
	private String ip;
	
	/**
	 * 등록 일시
	 */
	@Column(name="REGIST_DATE", nullable=false)
	private Date registDate;
}
