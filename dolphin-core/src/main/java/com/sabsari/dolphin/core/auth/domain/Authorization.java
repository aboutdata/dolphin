package com.sabsari.dolphin.core.auth.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sabsari.dolphin.core.auth.domain.code.GrantType;
import com.sabsari.dolphin.core.auth.domain.code.Role;

import lombok.Data;

@Entity
@Table(name="AUTHORIZATION")
@Data
public class Authorization implements Serializable {
	
	private static final long serialVersionUID = -404624227503317894L;
	
	public Authorization() {		
	}
		
	public Authorization(String owner, GrantType grantType, Role role) {
		if (grantType == null) {
			throw new IllegalArgumentException("GrantType must not be null.");
		}
		
		this.owner = owner;
		this.grantTypeCode = grantType.getCode();
		this.level = role == null ? Role.ANONYMOUS.getLevel() : role.getLevel();
	}
	
	/**
	 * PK : 8자리 10000001 부터 사용됨
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_SEQ", length=14, nullable=false)
	private Long idSeq;
	
	/**
	 * clientId or uerKey
	 */
	@Column(name="OWNER", length=36, nullable=false)
	private String owner;
	
	/**
	 * grantType
	 */
	@Column(name="GRANT_TYPE_CODE", nullable=false)
	private int grantTypeCode;
	
	/**
	 * 권한 레벨
	 */
	@Column(name="LEVEL", nullable=false)
	private int level;
}
