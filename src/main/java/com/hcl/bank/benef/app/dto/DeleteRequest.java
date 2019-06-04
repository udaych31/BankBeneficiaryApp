package com.hcl.bank.benef.app.dto;

import java.io.Serializable;

public class DeleteRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long payeeId;
	private Long payeeAccountNo;
	
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
	
	
	

}
