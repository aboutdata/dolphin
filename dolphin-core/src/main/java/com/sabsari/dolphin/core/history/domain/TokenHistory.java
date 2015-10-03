package com.sabsari.dolphin.core.history.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sabsari.dolphin.core.history.domain.code.TokenHistoryReason;

import lombok.Data;

@Entity
@Table(name="TOKEN_HISTORY")
@Data
public class TokenHistory implements Serializable {

	private static final long serialVersionUID = -4595354729673660062L;

	public TokenHistory() {
		
	}
	
	public TokenHistory(String owner, TokenHistoryReason reasonCode) {
		this.owner = owner;
		this.reasonCode = reasonCode.getCode();
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
	@Column(name="OWNER", length=36, nullable=false)
	private String owner;
	
	/**
     * 사유 코드
     */
    @Column(name="REASON_CODE", length=3, nullable=false)
    private String reasonCode;
	
	/**
	 * 등록 일시
	 */
	@Column(name="REGIST_DATE", nullable=false)
	private Date registDate;
	
	public TokenHistoryReason getReasonCode() {
		return TokenHistoryReason.search(this.reasonCode);
	}
}
