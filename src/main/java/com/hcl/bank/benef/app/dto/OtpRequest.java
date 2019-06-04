package com.hcl.bank.benef.app.dto;

import java.io.Serializable;

public class OtpRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String email;
	
	private Long accountNo;
	
	public OtpRequest() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OtpRequest [email=");
		builder.append(email);
		builder.append(", accountNo=");
		builder.append(accountNo);
		builder.append("]");
		return builder.toString();
	}

	
}
