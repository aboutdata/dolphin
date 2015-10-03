package com.sabsari.dolphin.core.member.domain;

import lombok.Data;

import javax.persistence.*;

import com.sabsari.dolphin.core.member.domain.code.AuthStatusCode;

import java.io.Serializable;
import java.util.Date;

/**
 * AUTHENTICATION table
 *
 * @author shong@sk.com
 * @author jungkeun.park@sk.com
 * @date   2014. 2. 28.
 */
@Entity
@Table(name="AUTHENTICATION")
@Data
public class Authentication implements Serializable {

	private static final long serialVersionUID = -8591894902235012371L;

	public Authentication() {

	}

	public Authentication(Long userSeq, String password, String authStatusCode) {
		Date date = new Date();
		this.userSeq = userSeq;
		this.password = password;
		this.authStatusCode = authStatusCode;
		this.authFailCount = 0;
		this.registDate = date;
		this.modifyDate = date;
	}
	
	public Authentication(Long userSeq, String password, AuthStatusCode authStatusCode) {
		this(userSeq, password, authStatusCode == null ? AuthStatusCode.LOGIN_IMPOSSIBLE.getCode() : authStatusCode.getCode());
	}

	@Id
	@Column(name="USER_SEQ", length=14, nullable=false)
	private Long userSeq;
	
	/**
	 * 비밀번호
	 */
	@Column(name="PASSWORD", length=128, nullable=false)
	private String password;

    /**
     * 인증 상태 코드
     */
    @Column(name="AUTH_STATUS_CODE", length=2, nullable=false)
    private String authStatusCode;

    /**
     * 인증 실패 카운트
     */
    @Column(name="AUTH_FAIL_COUNT", length=6, nullable=false)
    private int authFailCount;

    /**
     * last modification date
     */
    @Column(name="MODIFY_DATE", nullable=false)
    private Date modifyDate;
	
	/**
	 * Regist date
	 */
	@Column(name="REGIST_DATE", nullable=false)
	private Date registDate;
	
	public AuthStatusCode getAuthStatusCode() {
		return AuthStatusCode.search(this.authStatusCode);
	}
	
	public void setAuthStatusCode(AuthStatusCode authStatusCode) {
		this.authStatusCode = authStatusCode.getCode();
	}
}
