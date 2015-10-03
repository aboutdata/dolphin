package com.sabsari.dolphin.core.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * PERMISSION( 동의테이블 )
 * 
 * @date 2014. 01. 12
 * @author sabsari@sk.com , jungkeun.park@sk.com
 */
@Entity
@Table(name="PERMISSION")
@Data
public class Permission implements Serializable {

	private static final long serialVersionUID = -3912543557028542036L;

	public Permission() {

	}

	public Permission(Long userSeq, String clientId) {
		this.setUserSeq(userSeq);
		this.setClientId(clientId);
		this.registDate = new Date();
	}
	
	/**
	 * PK
	 */
	@EmbeddedId
	private PermissionId id = new PermissionId();
	
	@Column(name="USER_SEQ", nullable=false, updatable=false, insertable=false)
    private Long userSeq;
	
	@Column(name="CLIENT_ID", nullable=false, updatable=false, insertable=false)
    private String clientId;

	/**
	 * 등록 일시
	 */
	@Column(name="REGIST_DATE", nullable=false)
	private Date registDate;
	
	@Transient
	public Long getUserSeq() {
		return this.id.getUserSeq();
	}

	public void setUserSeq(Long userSeq) {
		this.id.setUserSeq(userSeq);
	}

	@Transient
	public String getClientId() {
		return this.id.getClientId();
	}

	public void setClientId(String clientId) {
		this.id.setClientId(clientId);
	}
	
	@Embeddable
	@Data
	public static class PermissionId implements Serializable {
		
		private static final long serialVersionUID = -7482490213142341320L;
		
		public PermissionId() {
			
		}
		
		public PermissionId(Long userSeq, String clientId) {
			this.userSeq = userSeq;
			this.clientId = clientId;
		}
		
		@Column(name="USER_SEQ", length=14, nullable=false)
		private Long userSeq;
		
		@Column(name="CLIENT_ID", length=36, nullable=false)
		private String clientId; 
	}
}
