package com.hcl.bank.benef.app.dto;

import java.io.Serializable;
import java.util.List;

public class PayeeListResponse implements Serializable {

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
