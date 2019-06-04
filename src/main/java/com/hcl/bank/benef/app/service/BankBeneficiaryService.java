package com.hcl.bank.benef.app.service;

import com.hcl.bank.benef.app.dto.AddPayeeRequest;
import com.hcl.bank.benef.app.dto.AddPayeeResponse;
import com.hcl.bank.benef.app.dto.ConfirmPayeeRequest;
import com.hcl.bank.benef.app.dto.ConfirmPayeeResponse;
import com.hcl.bank.benef.app.dto.DeleteResponse;
import com.hcl.bank.benef.app.dto.EditPayeeResponse;
import com.hcl.bank.benef.app.dto.PayeeListResponse;	


public interface BankBeneficiaryService {
	
	public PayeeListResponse getPayeeList();
	public DeleteResponse deletePayee(Long payeeId);
	public EditPayeeResponse getPayeeByUsingPayeeId(Long payeeId);
	
	public AddPayeeResponse addPayee(AddPayeeRequest request);
	
	public ConfirmPayeeResponse confirmAddPayee(ConfirmPayeeRequest request);

}
