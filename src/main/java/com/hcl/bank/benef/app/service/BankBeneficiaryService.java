package com.hcl.bank.benef.app.service;

import com.hcl.bank.benef.app.dto.DeleteResponse;

public interface BankBeneficiaryService {
	
	public DeleteResponse deletePayee(Long payeeId);

}
