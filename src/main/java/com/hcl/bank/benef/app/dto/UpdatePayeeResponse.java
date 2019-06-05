package com.hcl.bank.benef.app.dto;

import java.io.Serializable;

import com.hcl.bank.benef.app.util.ApiResponse;

public class UpdatePayeeResponse extends ApiResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String message;
	
	private Long referenceNo;
	
	public UpdatePayeeResponse() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(Long referenceNo) {
		this.referenceNo = referenceNo;
	}
	
	
}
