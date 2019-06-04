package com.hcl.bank.benef.app.dto;

import java.io.Serializable;

import com.hcl.bank.benef.app.util.ApiResponse;

public class EditPayeeResponse extends ApiResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private PayeeDto payeeDetails;
	
	public EditPayeeResponse() {
		super();
	}

	public PayeeDto getPayeeDetails() {
		return payeeDetails;
	}

	public void setPayeeDetails(PayeeDto payeeDetails) {
		this.payeeDetails = payeeDetails;
	}

	

}
