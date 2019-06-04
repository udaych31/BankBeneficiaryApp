package com.hcl.bank.benef.app.dto;

import java.io.Serializable;

public class ValidateRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long payeeId;
	private Long otp;

	public Long getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(Long payeeId) {
		this.payeeId = payeeId;
	}

	public Long getOtp() {
		return otp;
	}

	public void setOtp(Long otp) {
		this.otp = otp;
	}

}
