package com.hcl.bank.benef.app.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.bank.benef.app.dto.AddPayeeRequest;
import com.hcl.bank.benef.app.dto.AddPayeeResponse;
import com.hcl.bank.benef.app.dto.ConfirmPayeeRequest;
import com.hcl.bank.benef.app.dto.ConfirmPayeeResponse;
import com.hcl.bank.benef.app.dto.DeleteResponse;
import com.hcl.bank.benef.app.dto.EditPayeeResponse;
import com.hcl.bank.benef.app.dto.PayeeListResponse;
import com.hcl.bank.benef.app.dto.ValidateRequest;
import com.hcl.bank.benef.app.service.BankBeneficiaryServiceImpl;

@RestController
@RequestMapping("/beneficiary")
@CrossOrigin
public class BankBeneficiaryController {
	private static final Logger logger=LogManager.getLogger(BankBeneficiaryController.class);
	


	
	@Autowired
	BankBeneficiaryServiceImpl bankBeneficiaryServiceImpl;
	
	@DeleteMapping("/deletePayee")
	public DeleteResponse deletePayee(@RequestParam Long payeeeId) {
		return bankBeneficiaryServiceImpl.deletePayee(payeeeId);
	}
	
	@GetMapping("/getPayeeList")
	public PayeeListResponse getAllPayeeList(){
		logger.info(this.getClass().getName()+" getAllPayeeList is calling ...!");
		return bankBeneficiaryServiceImpl.getPayeeList();
	}
	
	@GetMapping("/getPayeeById")
	public EditPayeeResponse getPayeeByUsingId(@RequestParam("payeeId") Long payeeId){
		logger.info(this.getClass().getName()+" getPayeeByUsingId is calling ...!");
		return bankBeneficiaryServiceImpl.getPayeeByUsingPayeeId(payeeId);
	}
	
	@PostMapping("/addPayee")
	public AddPayeeResponse addPayee(@RequestBody AddPayeeRequest request){
		logger.info(this.getClass().getName()+" addPayee is calling ...!");
		return bankBeneficiaryServiceImpl.addPayee(request);
	}
	
	@PostMapping("/confirmAddPayee")
	public  ConfirmPayeeResponse confirmAddPayee(@RequestBody ConfirmPayeeRequest request) {
		logger.info(this.getClass().getName()+" confirmAddPayee is calling ...!");
		return bankBeneficiaryServiceImpl.confirmAddPayee(request);	
	}
	@PostMapping("/confirmPayee")
	public DeleteResponse confirmDeletePayee(@RequestBody ValidateRequest requesst) {

	return	bankBeneficiaryServiceImpl.confirmDeletePayee(requesst);

	}
}