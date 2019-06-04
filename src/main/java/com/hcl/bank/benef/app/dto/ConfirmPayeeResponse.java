package com.hcl.bank.benef.app.dto;

import java.io.Serializable;

import com.hcl.bank.benef.app.util.ApiResponse;

public class ConfirmPayeeResponse extends ApiResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public ConfirmPayeeResponse() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
