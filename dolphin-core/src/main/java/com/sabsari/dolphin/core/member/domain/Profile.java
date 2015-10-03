package com.sabsari.dolphin.core.member.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sabsari.dolphin.core.member.domain.code.Gender;

import lombok.Data;

/**
 * Profile table
 *
 * @author shong@sk.com
 * @author jungkeun.park@sk.com
 * @date   2014. 2. 28.
 */
@Entity
@Table(name="Profile")
@Data
public class Profile implements Serializable {
	
	private static final long serialVersionUID = -9003113787391306004L;
	
	public Profile() {
		
	}
	
	public Profile(Long userSeq, String firstName, String lastName, String birthday, String phone, String gender) {
		Date now = new Date();
		this.userSeq = userSeq;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.phone = phone;
		this.gender = gender;
		this.registDate = now;
		this.modifyDate = now;
	}

	public Profile(Long userSeq, String firstName, String lastName, String birthday, String phone, Gender gender) {
		this(userSeq, firstName, lastName, birthday, phone, gender != null ? gender.getCode() : null);
	}
	
	@Id
	@Column(name="USER_SEQ", length=14, nullable=false)
	private Long userSeq;
	
	/**
	 * 이름
	 */
	@Column(name="FIRST_NAME", length=32, nullable=true)
	private String firstName;
	
	/**
	 * 성
	 */
	@Column(name="LAST_NAME", length=32, nullable=true)
	private String lastName;

	/**
	 * 생년월일
	 */
	@Column(name="BIRTHDAY", length=8, nullable=true)
	private String birthday;
	
	/**
	 * 전화번호
	 */
	@Column(name="PHONE", length=32, nullable=true)
	private String phone;

	/**
	 * 성별
	 */
	@Column(name="GENDER", length=1, nullable=true)
	private String gender;

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
}
