package com.sabsari.dolphin.core.member.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 * USER_GROUP table
 *
 * @author sabsari
 * @author jungkeun.park@sk.com
 * @date   2014. 2. 28.
 */
@Entity
@Table(name="USER_GROUP")
@Data
public class UserGroup implements Serializable {
	
	private static final long serialVersionUID = -5094396072496974241L;

	/**
	 * 그룹 식별자
	 */
	@Id
	@Column(name="GROUP_KEY", length=36, nullable=false)
	private String groupKey;
	
	/**
	 * 설명
	 */
	@Column(name="GROUP_NAME", length=32, nullable=false)
	private String groupName;
		
	/**
	 * 변경 일시
	 */
	@Column(name="MODIFY_DATE", nullable=false)
	private Date modifyDate;
	
	/**
	 * 등록 일시
	 */
	@Column(name="REGIST_DATE", nullable=false)
	private Date registDate;
	
	/**
	 * relation with application
	 */
	@OneToMany(mappedBy="userGroup", fetch=FetchType.LAZY)
	private List<Application> applications;
}
