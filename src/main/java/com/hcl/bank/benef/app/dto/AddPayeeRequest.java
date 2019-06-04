package com.hcl.bank.benef.app.dto;

import java.io.Serializable;

public class AddPayeeRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long accountNo;
	
	private String ifscCode;
	
	private String emailId;
	
	private String nickName;
	
	public AddPayeeRequest() {
		super();
	}

	public Long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	

}
