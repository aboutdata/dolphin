package com.sabsari.dolphin.core.history.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="DELETE_USER_HISTORY")
@Data
public class DeleteUserHistory implements Serializable {
	
	private static final long serialVersionUID = 6725737034096698529L;

	public DeleteUserHistory() {
		
	}
	
	public DeleteUserHistory(String emailId, String userKey, String clientId) {
		this(emailId, userKey, null, clientId);
	}
	
	public DeleteUserHistory(String emailId, String userKey, String firstName, String clientId) {
		this.emailId = emailId;
		this.userKey = userKey;
		this.firstName = firstName;
		this.clientId = clientId;		
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
     * email id
     */
	@Column(name="EMAIL_ID", length=128, nullable=false)
	private String emailId;
	
	/**
	 * 유저키
	 */
	@Column(name="USER_KEY", length=36, nullable=false, unique=true)
	private String userKey;
	
	/**
	 * 이름
	 */
	@Column(name="FIRST_NAME", length=32, nullable=true)
	private String firstName;
	
	/**
	 * client id
	 */
	@Column(name="CLIENT_ID", length=36, nullable=false)
	private String clientId;	
	
	/**
	 * 등록 일시
	 */
	@Column(name="REGIST_DATE", nullable=false)
	private Date registDate;
}
