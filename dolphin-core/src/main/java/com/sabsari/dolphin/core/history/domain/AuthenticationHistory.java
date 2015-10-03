package com.sabsari.dolphin.core.history.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sabsari.dolphin.core.history.domain.code.AuthenticationHistoryReason;

import lombok.Data;

@Entity
@Table(name="AUTHENTICATION_HISTORY")
@Data
public class AuthenticationHistory implements Serializable {
	
	private static final long serialVersionUID = 7709366126495856156L;
	
	public AuthenticationHistory() {
		
	}
	
	public AuthenticationHistory(String userKey, String content, AuthenticationHistoryReason reasonCode) {
		this.userKey = userKey;
		this.reasonCode = reasonCode.getCode();
		this.content = content;
		this.registDate = new Date();
	}

	/**
	 * PK : 7자리 1000001 부터 사용됨
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SEQ", length=14, nullable=false)
	private Long seq;
	
	/**
	 * 유저키
	 */
	@Column(name="USER_KEY", length=36, nullable=false)
	private String userKey;
	
	/**
     * 사유 코드
     */
    @Column(name="REASON_CODE", length=3, nullable=false)
    private String reasonCode;
    
    /**
     * 변경 내용
     */
    @Column(name="CONTENT", length=48, nullable=true)
    private String content;
	
	/**
	 * 등록 일시
	 */
	@Column(name="REGIST_DATE", nullable=false)
	private Date registDate;
	
	public AuthenticationHistoryReason getReasonCode() {
		return AuthenticationHistoryReason.search(this.reasonCode);
	}
}
