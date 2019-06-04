package com.hcl.bank.benef.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class OtpDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long otpId;
	
	@Column(name="ACCOUNT_NO")
	private Long accountNo;
	
	@Column(name="OTP")
	private Long otp;
	
	@Column(name="OTP_USED")
	private Character otpUsed;
	
	public OtpDetails() {
		super();
	}

	public Long getOtpId() {
		return otpId;
	}

	public void setOtpId(Long otpId) {
		this.otpId = otpId;
	}

	public Long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}

	public Long getOtp() {
		return otp;
	}

	public void setOtp(Long otp) {
		this.otp = otp;
	}
	

	public Character getOtpUsed() {
		return otpUsed;
	}

	public void setOtpUsed(Character otpUsed) {
		this.otpUsed = otpUsed;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OtpDetails [otpId=");
		builder.append(otpId);
		builder.append(", accountNo=");
		builder.append(accountNo);
		builder.append(", otp=");
		builder.append(otp);
		builder.append(", otpused=");
		builder.append(otpUsed);
		builder.append("]");
		return builder.toString();
	}
	
	

}
