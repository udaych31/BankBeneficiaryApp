package com.hcl.bank.benef.app.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.bank.benef.app.dto.PayeeDto;
import com.hcl.bank.benef.app.dto.PayeeListResponse;
import com.hcl.bank.benef.app.entity.ManagePayee;
import com.hcl.bank.benef.app.repository.ManagePayeeRepository;

@Service
public class BankBeneficiaryServiceImpl implements BankBeneficiaryService {
	
	private static final Logger logger=LogManager.getLogger(BankBeneficiaryServiceImpl.class);
	
	@Autowired
	private ManagePayeeRepository payeeRepository;

	@Override
	public PayeeListResponse getPayeeList() {
		
		PayeeListResponse response=new PayeeListResponse();
		
		List<PayeeDto> payeeList=new ArrayList<>();
		try {
			
			List<ManagePayee> listAll = payeeRepository.findAll();
			listAll.stream().forEach(payee->{
				PayeeDto dto=new PayeeDto();
				dto.setNickName(payee.getNickName());
				dto.setPayeeId(payee.getPayeeId());
				dto.setAccountNo(payee.getAccountNo());
				dto.setEmailId(payee.getEmailId());
				dto.setIfscCode(payee.getIfscCode());
				dto.setPayeeAccountNo(payee.getPayeeAccountNo());
				payeeList.add(dto);
			});
			response.setPayeeList(payeeList);
			
		} catch (Exception e) {
			logger.error(this.getClass().getName()+" getPayeeList : "+e.getMessage());
		}
		return response;
	}

}
