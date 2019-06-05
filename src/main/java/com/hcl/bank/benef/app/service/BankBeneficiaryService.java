package com.hcl.bank.benef.app.service;

import com.hcl.bank.benef.app.dto.AddPayeeRequest;
import com.hcl.bank.benef.app.dto.AddPayeeResponse;
import com.hcl.bank.benef.app.dto.ConfirmPayeeRequest;
import com.hcl.bank.benef.app.dto.ConfirmPayeeResponse;
import com.hcl.bank.benef.app.dto.DeleteResponse;
import com.hcl.bank.benef.app.dto.EditPayeeResponse;
import com.hcl.bank.benef.app.dto.PayeeListResponse;
import com.hcl.bank.benef.app.dto.UpdatePayeeRequest;
import com.hcl.bank.benef.app.dto.UpdatePayeeResponse;	


public interface BankBeneficiaryService {
	
	public PayeeListResponse getPayeeList();
	public DeleteResponse deletePayee(Long payeeId);
	public EditPayeeResponse getPayeeByUsingPayeeId(Long payeeId);
	
	public AddPayeeResponse addPayee(AddPayeeRequest request);
	
	public ConfirmPayeeResponse confirmAddPayee(ConfirmPayeeRequest request);
	
	public UpdatePayeeResponse updatePayee(UpdatePayeeRequest request);
	
	public ConfirmPayeeResponse confirmUpdatePayee(ConfirmPayeeRequest request);

}
