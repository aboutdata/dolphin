package com.sabsari.dolphin.core.auth.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sabsari.dolphin.core.common.CommonConstants;
import com.sabsari.dolphin.core.common.util.CommonUtils;

import lombok.Data;

@Entity
@Table(name="REFRESH_TOKEN")
@Data
public class RefreshToken implements Serializable {
	
	private static final long serialVersionUID = 7592062134545178656L;

	public RefreshToken() {
		
	}
	
	public RefreshToken(Long idSeq, String refreshToken) {
		this.idSeq = idSeq;
		this.refreshToken = refreshToken;
		this.expiresIn = CommonConstants.REFRESHTOKEN_DEFAULT_LIFETIME.getTerm();
	}

	@Id
	@Column(name="ID_SEQ", length=14, nullable=false)
	private Long idSeq;
	
	/**
	 * accessToken
	 */
	@Column(name="REFRESH_TOKEN", length=24, nullable=false, unique=true)
	private String refreshToken;
		
	/**
	 * 만료시간 초단위
	 */
	@Column(name="EXPIRES_IN", nullable=false)
	private int expiresIn;
	
	/**
	 * relation with AccessToken
	 */
	@OneToOne(fetch=FetchType.EAGER, targetEntity=AccessToken.class, cascade={CascadeType.ALL})
	@JoinColumn(name="ID_SEQ")
	private AccessToken accessToken;
	
	public String getAccessTokenValue() {
		if (this.accessToken == null)
			return null;
		
		return accessToken.getAccessToken();
	}
	
	public int getAccessTokenRestLifeTime() {
		return this.accessToken.getRestLifeTime();
	}
	
	public boolean isExpired() {
		return CommonUtils.isExpired(this.expiresIn, this.accessToken.getRefreshDate());
	}
	
	public int getRestLifeTime() {
		return CommonUtils.getRestTime(this.expiresIn, this.accessToken.getRefreshDate());
	}
}
