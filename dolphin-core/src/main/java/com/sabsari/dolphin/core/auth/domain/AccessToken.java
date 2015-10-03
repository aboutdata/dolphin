package com.sabsari.dolphin.core.auth.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sabsari.dolphin.core.auth.domain.code.GrantType;
import com.sabsari.dolphin.core.auth.domain.code.Role;
import com.sabsari.dolphin.core.auth.domain.code.TokenLifeTime;
import com.sabsari.dolphin.core.common.CommonConstants;
import com.sabsari.dolphin.core.common.util.CommonUtils;

import lombok.Data;

@Entity
@Table(name="ACCESS_TOKEN")
@Data
public class AccessToken implements Serializable {

	private static final long serialVersionUID = -8983838106352620517L;

	public AccessToken() {		
	}
	
	public AccessToken(Long idSeq, String accessToken, TokenLifeTime tokenLifeTime) {
		Date now = new Date();
		this.idSeq = idSeq;
		this.accessToken = accessToken;
		this.expiresIn = tokenLifeTime.getTerm();
		this.refreshDate = now;
		this.issueDate = now;
	}
	
	public AccessToken(Long idSeq, String accessToken) {
		this(idSeq, accessToken, CommonConstants.ACCESSTOKEN_DEFAULT_LIFETIME);
	}

	@Id
	@Column(name="ID_SEQ", length=14, nullable=false)
	private Long idSeq;
	
	/**
	 * accessToken
	 */
	@Column(name="ACCESS_TOKEN", length=24, nullable=false, unique=true)
	private String accessToken;
	
	/**
	 * 만료시간 초단위
	 */
	@Column(name="EXPIRES_IN", nullable=false)
	private int expiresIn;
	
	/**
	 * 리프레쉬 날짜
	 */
	@Column(name="REFRESH_DATE", nullable=false)
	private Date refreshDate;
	
	/**
	 * 토큰발급날짜
	 */
	@Column(name="ISSUE_DATE", nullable=false)
	private Date issueDate;
	
	/**
	 * relation with Authorizaiton
	 */
	@OneToOne(fetch=FetchType.EAGER, targetEntity=Authorization.class, cascade={CascadeType.ALL})
	@JoinColumn(name="ID_SEQ")
	private Authorization authorization;
	
	public String getOwner() {
		if (authorization == null)
			return null;
		
		return authorization.getOwner();
	}
	
	public GrantType getGrantType() {
		if (authorization == null)
			return null;
		
		int code = authorization.getGrantTypeCode();
		GrantType grantType = GrantType.search(code);
		
		return grantType;
	}
	
	public Role getRole() {
		if (authorization == null)
			return null;
		
		int level = authorization.getLevel();
		Role role = Role.search(level);
		return role;
	}
	
	public boolean isExpired() {
		return CommonUtils.isExpired(this.expiresIn, this.refreshDate);
	}
	
	public int getRestLifeTime() {
		return CommonUtils.getRestTime(this.expiresIn, this.refreshDate);
	}
}
