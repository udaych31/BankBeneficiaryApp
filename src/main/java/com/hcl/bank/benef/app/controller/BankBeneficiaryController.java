package com.hcl.bank.benef.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.bank.benef.app.dto.DeleteResponse;
import com.hcl.bank.benef.app.dto.ValidateRequest;
import com.hcl.bank.benef.app.service.BankBeneficiaryServiceImpl;

@RestController
@RequestMapping("/beneficiary")
@CrossOrigin
public class BankBeneficiaryController {

	
	@Autowired
	BankBeneficiaryServiceImpl bankBeneficiaryServiceImpl;
	
	@DeleteMapping("/deletePayee")
	public DeleteResponse deletePayee(@RequestParam Long payeeeId) {
		return bankBeneficiaryServiceImpl.deletePayee(payeeeId);
		
		
		
	}
	
	@DeleteMapping("/confirmPayee")
	public DeleteResponse confirmDeletePayee(ValidateRequest requesst) {

	return	bankBeneficiaryServiceImpl.confirmDeletePayee(requesst);

}
}