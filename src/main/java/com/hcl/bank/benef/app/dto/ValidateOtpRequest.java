package com.hcl.bank.benef.app.dto;

import java.io.Serializable;

public class ValidateOtpRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long accountNo;
	
	private Long otp;
	
	public ValidateOtpRequest() {
		super();
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

	@Override
	public String toString() {
		return "ValidateOtpRequest [accountNo=" + accountNo + ", otp=" + otp + "]";
	}

	
	
}
