package com.hcl.bank.benef.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="account_summary")
public class AccountSummary implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountNo;
	
	@Column(name="ACCOUNT_TYPE")
	private String accountType;
	
	@Column(name="ACCOUNT_NAME")
	private String accountName;
	
	@Column(name = "DATE_OF_BIRTH")
	private String dob;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="BRANCH_NAME")
	private String branchName;
	
	@Column(name="EMAIL_ID")
	private String email;
	
	@Column
	private String ifscCode;
	
	@Column(name="CLOSING_BALANCE")
	private Double closingBalance;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DT",columnDefinition = "DATETIME")
	private Date createDt;
	
	
	public AccountSummary() {
		super();
	}

	public Long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Double getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(Double closingBalance) {
		this.closingBalance = closingBalance;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountSummary [accountNo=");
		builder.append(accountNo);
		builder.append(", accountType=");
		builder.append(accountType);
		builder.append(", accountName=");
		builder.append(accountName);
		builder.append(", dob=");
		builder.append(dob);
		builder.append(", address=");
		builder.append(address);
		builder.append(", branchName=");
		builder.append(branchName);
		builder.append(", emailId=");
		builder.append(email);
		builder.append(", closingBalance=");
		builder.append(closingBalance);
		builder.append(", createDt=");
		builder.append(createDt);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
