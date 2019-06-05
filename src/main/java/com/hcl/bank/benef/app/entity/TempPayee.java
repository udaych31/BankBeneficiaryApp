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
public class TempPayee implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tempPayeeId;
	
	@Column
	private Long accountNo;
	
	@Column
	private Long payeeAccountNo;
	
	@Column
	private String nickName;
	
	@Column
	private String emailId;
	
	@Column
	private String ifscCode;
	
	private Long payeeId;
	
	
	public TempPayee() {
		super();
	}


	public Long getPayeeId() {
		return payeeId;
	}


	public void setPayeeId(Long payeeId) {
		this.payeeId = payeeId;
	}


	public Long getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
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


	public String getEmailId() {
		return emailId;
	}


	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	public String getIfscCode() {
		return ifscCode;
	}


	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}


	public Long getTempPayeeId() {
		return tempPayeeId;
	}


	public void setTempPayeeId(Long tempPayeeId) {
		this.tempPayeeId = tempPayeeId;
	}
	
	
	
	

}
