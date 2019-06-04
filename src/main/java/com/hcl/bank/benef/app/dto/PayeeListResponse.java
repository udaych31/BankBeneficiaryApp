package com.hcl.bank.benef.app.dto;

import java.io.Serializable;
import java.util.List;

import com.hcl.bank.benef.app.util.ApiResponse;

public class PayeeListResponse extends ApiResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<PayeeDto> payeeList;
	
	
	public PayeeListResponse() {
		super();
	}

	public List<PayeeDto> getPayeeList() {
		return payeeList;
	}

	public void setPayeeList(List<PayeeDto> payeeList) {
		this.payeeList = payeeList;
	}

	
}
