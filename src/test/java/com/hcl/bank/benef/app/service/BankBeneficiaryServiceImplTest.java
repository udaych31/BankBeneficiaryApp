package com.hcl.bank.benef.app.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.bank.benef.app.dto.PayeeListResponse;
import com.hcl.bank.benef.app.entity.ManagePayee;
import com.hcl.bank.benef.app.repository.ManagePayeeRepository;

@RunWith(MockitoJUnitRunner.class)
public class BankBeneficiaryServiceImplTest{
	
	@Mock
	private ManagePayeeRepository payeeRepository;
	
	@InjectMocks
	private BankBeneficiaryServiceImpl service;

	@Test
	public void getPayeeList() {
		List<ManagePayee> payeeList=new ArrayList<>();
		ManagePayee payee=new ManagePayee();
		payee.setAccountNo(1L);
		payee.setPayeeAccountNo(1L);
		payee.setEmailId("uday@hcl.com");
		payee.setIfscCode("hdfc0001");
		payee.setNickName("uday");
		payee.setPayeeId(1L);
		payeeList.add(payee);
		
		when(payeeRepository.findAll()).thenReturn(payeeList);
		
		PayeeListResponse payeeList2 = service.getPayeeList();
		Double val=Double.valueOf(""+payeeList.size());
		Double val1=Double.valueOf(""+payeeList2.getPayeeList().size());
		assertEquals(val, val1);
		
	}

}
