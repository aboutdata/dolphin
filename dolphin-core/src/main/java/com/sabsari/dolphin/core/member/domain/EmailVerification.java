package com.sabsari.dolphin.core.member.domain;

import lombok.Data;

import javax.persistence.*;

import com.sabsari.dolphin.core.common.util.SimpleCrypto;

import java.io.Serializable;
import java.util.Date;

/**
 * EMAIL_VERIFICATION table
 * 
 * @author shong@sk.com
 * @author jungkeun.park@sk.com
 * @date   2014. 2. 28.
 */
@Entity
@Table(name="EMAIL_VERIFICATION")
@Data
public class EmailVerification implements Serializable {

	private static final long serialVersionUID = 5995832393159764992L;
	
	public EmailVerification() {
		
	}
	
	public EmailVerification(Long userSeq, String verificationValue) {
		Date now = new Date();
		this.userSeq = userSeq;
		this.verificationValue = verificationValue;
		this.crc32 = SimpleCrypto.getCRC32(verificationValue);
		this.registDate = now;		
	}
	
	/**
	 * user seq
	 */
	@Id
	@Column(name="USER_SEQ", length=14, nullable=false)
	private Long userSeq;
	
	/**
	 * value for verification
	 */
	@Column(name="VERIFICATION_VALUE", length=64, nullable=false)
	private String verificationValue;
	
	/**
	 * VERIFICATION_VALUE as crc32
	 */
	@Column(name="CRC32", nullable=false)
	private Long crc32;

    /**
     * verified date
     */
    @Column(name="VERIFIED_DATE", nullable=true)
    private Date verifiedDate;
	    
	/**
	 * Regist date
	 */
	@Column(name="REGIST_DATE", nullable=false)
	private Date registDate;
}
