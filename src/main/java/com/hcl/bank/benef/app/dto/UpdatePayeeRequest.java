package com.hcl.bank.benef.app.dto;

import java.io.Serializable;

public class UpdatePayeeRequest implements Serializable {

	private Long payeeId;
	
	private Long payeeAccountNo;
	
	private String nickName;
	
	private String ifscCode;
	
	private String emailId;
	
	public UpdatePayeeRequest() {
		super();
	}

	public Long getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(Long payeeId) {
		this.payeeId = payeeId;
	}

	public Long getPayeeAccountNo() {
		return payeeAccountNo;
	}

	public void setPayeeAccountNo(Long payeeAccountNo) {
		this.payeeAccountNo = payeeAccountNo;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	
	
	
	
}
