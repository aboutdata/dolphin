package com.sabsari.dolphin.core.member.domain;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * USER table
 * 
 * @date 2014. 01. 12
 * @author sabsari@sk.com
 * @author jungkeun.park@sk.com
 */
@Entity
@Table(name="USER")
@Data
public class User implements Serializable {

	private static final long serialVersionUID = 7829463324688214429L;

	public User() {

	}

	public User(String userKey, String groupKey, String emailId) {
		Date now = new Date();
		this.userKey = userKey;
		this.groupKey = groupKey;
		this.emailId = emailId;
		this.registDate = now;
		this.modifyDate = now;
	}
	
	/**
	 * PK : 10자리 1000000001 부터 사용됨
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="USER_SEQ", length=14, nullable=false)
	private Long userSeq;

	/**
	 * 유저키
	 */
	@Column(name="USER_KEY", length=36, nullable=false, unique=true)
	private String userKey;

	/**
	 * 그룹키
	 */
	@Column(name="GROUP_KEY", length=36, nullable=false)
	private String groupKey;
	
	/**
	 * 이메일 아이디
	 */
	@Column(name="EMAIL_ID", length=128, nullable=false)
	private String emailId;

	/**
	 * 등록 일시
	 */
	@Column(name="REGIST_DATE", nullable=false)
	private Date registDate;

	/**
	 * 변경 일시
	 */
	@Column(name="MODIFY_DATE", nullable=false)
	private Date modifyDate;
		
	/**
	 * relation with emailAuth
	 */
	@OneToOne(fetch=FetchType.LAZY, targetEntity=Authentication.class)
	@JoinColumn(name="USER_SEQ")
	private Authentication authentication;
	
	/**
	 * relation with profile
	 */
	@OneToOne(fetch=FetchType.LAZY, targetEntity=Profile.class)
	@JoinColumn(name="USER_SEQ")
	private Profile Profile;
}
