package com.sabsari.dolphin.core.member.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * APPLICATION table
 * 
 * @author shong@sk.com
 * @date   2014. 2. 28.
 */
@Entity
@Table(name="APPLICATION")
@Data
public class Application implements Serializable {

	private static final long serialVersionUID = 4913180669292028812L;

	public Application() {
		
	}
	
	public Application(String clientId, String clientSecret, String groupKey, String appName) {
		Date now = new Date();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		setGroupKey(groupKey);
		this.appName = appName;
		this.modifyDate = now;
		this.registDate = now;		
	}
	
	/**
	 * client id
	 */
	@Id
	@Column(name="CLIENT_ID", length=36, nullable=false)
	private String clientId;
	
	/**
	 * client secret
	 */
	@Column(name="CLIENT_SECRET", length=128, nullable=false)
	private String clientSecret;

	/**
	 * 그룹키
	 */
	@Column(name="GROUP_KEY", nullable=false, updatable=false, insertable=false)
    private String groupKey;

	/**
	 * app name
	 */
	@Column(name="APP_NAME", length=32, nullable=false)
	private String appName;

	/**
	 * 그룹 식별자
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="GROUP_KEY")
	private UserGroup userGroup;

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
	
	public String getGroupKey() {
		if (this.userGroup == null || this.userGroup.getGroupKey() == null) {
			return null;
		}
		else
			return this.userGroup.getGroupKey();
	}
	
	public void setGroupKey(String groupKey) {
		if (this.userGroup == null)
			this.userGroup = new UserGroup();
		this.userGroup.setGroupKey(groupKey);
	}
}
