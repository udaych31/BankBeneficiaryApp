package com.hcl.bank.benef.app.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.bank.benef.app.dto.AddPayeeRequest;
import com.hcl.bank.benef.app.dto.AddPayeeResponse;
import com.hcl.bank.benef.app.dto.ConfirmPayeeRequest;
import com.hcl.bank.benef.app.dto.ConfirmPayeeResponse;
import com.hcl.bank.benef.app.dto.EditPayeeResponse;
import com.hcl.bank.benef.app.dto.OtpRequest;
import com.hcl.bank.benef.app.dto.PayeeDto;
import com.hcl.bank.benef.app.dto.PayeeListResponse;
import com.hcl.bank.benef.app.dto.ValidateOtpRequest;
import com.hcl.bank.benef.app.entity.AccountSummary;
import com.hcl.bank.benef.app.entity.ManagePayee;
import com.hcl.bank.benef.app.entity.OtpDetails;
import com.hcl.bank.benef.app.entity.TempPayee;
import com.hcl.bank.benef.app.repository.AccountSummaryRepository;
import com.hcl.bank.benef.app.repository.ManagePayeeRepository;
import com.hcl.bank.benef.app.repository.OtpRepository;
import com.hcl.bank.benef.app.repository.TempPayeeRepository;
import com.hcl.bank.benef.app.util.BeneficiaryServiceException;
import com.hcl.bank.benef.app.util.EmailSender;

@Service
public class BankBeneficiaryServiceImpl implements BankBeneficiaryService {
	
	private static final Logger logger=LogManager.getLogger(BankBeneficiaryServiceImpl.class);
	
	private static final String FAILURE="FAILURE";
	
	private static final String SUCCESS="SUCCESS";
	
	@Autowired
	private ManagePayeeRepository payeeRepository;
	
	@Autowired
	private TempPayeeRepository tempPayeeRepository;
	
	@Autowired
	private OtpRepository otpRepository;
	
	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	private AccountSummaryRepository accountRepository;

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
			response.setStatusCode(200);
			response.setStatus("SUCCESS");
			
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setStatus("INTERNALSERVERERROR");
			logger.error(this.getClass().getName()+" getPayeeList : "+e.getMessage());
		}
		return response;
	}
	
	@Override
	public EditPayeeResponse getPayeeByUsingPayeeId(Long payeeId) {
		EditPayeeResponse response=new EditPayeeResponse();
		try {
			ManagePayee payee = payeeRepository.findByPayeeId(payeeId);
			if(payee!=null) {
				PayeeDto dto=new PayeeDto();
				dto.setAccountNo(payee.getAccountNo());
				dto.setEmailId(payee.getEmailId());
				dto.setIfscCode(payee.getIfscCode());
				dto.setNickName(payee.getNickName());
				dto.setPayeeAccountNo(payee.getPayeeAccountNo());
				dto.setPayeeId(payee.getPayeeId());
				response.setPayeeDetails(dto);
				response.setStatus("SUCCESS");
				response.setStatusCode(200);
			}
			
			
		} catch (Exception e) {
			logger.error(this.getClass().getName()+" getPayeeByUsingPayeeId :"+e.getMessage());
		}
		return response;
	}
	
	@Override
	public AddPayeeResponse addPayee(AddPayeeRequest request) {
		AddPayeeResponse response=null;
		try {
			
			if(request!=null) {
				AccountSummary summary = accountRepository.findByAccountNoAndIfscCodeAndEmail(request.getAccountNo(), request.getIfscCode(), request.getEmailId());
				
				if(summary!=null) {
					TempPayee payee=new TempPayee();
					payee.setAccountNo(1L);
					payee.setNickName(request.getNickName());
					payee.setPayeeAccountNo(request.getAccountNo());
					payee.setEmailId(request.getEmailId());
					payee.setIfscCode(request.getIfscCode());
					TempPayee save = tempPayeeRepository.save(payee);
					Long referenceId=save.getPayeeId();
					
					response=new AddPayeeResponse();
					OtpRequest otpReq=new OtpRequest();
					otpReq.setAccountNo(1L);
					otpReq.setEmail(request.getEmailId());
					emailSender.sendOtp(otpReq);			
					
					response.setMessage("Success! your reference number is "+referenceId);
					response.setReferenceNo(referenceId);
					response.setStatus(SUCCESS);
					response.setStatusCode(201);
				}else {
					response=new AddPayeeResponse();
					response.setMessage("Account is not valid");
					response.setReferenceNo(0L);
					response.setStatus(FAILURE);
					response.setStatusCode(404);
				}
				
			}
			
			
		} catch (Exception e) {
			logger.error(this.getClass().getName()+" addPayee :"+e.getMessage());
		}
		return response;
	}
	
	@Override
	public ConfirmPayeeResponse confirmAddPayee(ConfirmPayeeRequest request) {
		ConfirmPayeeResponse response=new ConfirmPayeeResponse();
		try {
			ValidateOtpRequest otpReq=new ValidateOtpRequest();
			otpReq.setAccountNo(request.getReferenceNo());
			otpReq.setOtp(request.getOtp());
			OtpDetails otp = validateOtp(otpReq);
			if(otp!=null) {
				otp.setOtpUsed('T');
				otpRepository.save(otp);
				TempPayee findByPayeeId = tempPayeeRepository.findByPayeeId(request.getReferenceNo());
				if(findByPayeeId!=null) {
					ManagePayee payee=new ManagePayee();
					payee.setAccountNo(request.getAccountNo());
					payee.setNickName(findByPayeeId.getNickName());
					payee.setPayeeAccountNo(findByPayeeId.getPayeeAccountNo());
					payee.setEmailId(findByPayeeId.getEmailId());
					payee.setIfscCode(findByPayeeId.getIfscCode());
					payeeRepository.save(payee);
					response.setStatusCode(201);
					response.setStatus(SUCCESS);
					response.setMessage("Payee added successfully ..!");
					return response;
				}else {
					response.setStatusCode(404);
					response.setStatus(FAILURE);
					response.setMessage("SESSION TIME OUT");
					return response;
				}
				
				
			}else {
				response.setStatusCode(400);
				response.setStatus(FAILURE);
				response.setMessage("INVALID OTP");
				return response;
			}
			
			
			
		} catch (Exception e) {
			logger.error(this.getClass().getName()+" addPayee :"+e.getMessage());
		}
		
		return null;
	}
	
	private OtpDetails validateOtp(ValidateOtpRequest request) {
		OtpDetails otp=null;
		try {
			
			TempPayee findByPayeeId = tempPayeeRepository.findByPayeeId(request.getAccountNo());
			if(findByPayeeId!=null) {
				otp = otpRepository.findByAccountNo(findByPayeeId.getAccountNo());
				if(otp!=null && otp.getOtp().longValue()==request.getOtp().longValue()) {
					return otp;
				}
			}
			
			
		} catch (Exception e) {
			logger.error(this.getClass().getName()+" validateOtp :"+e.getMessage());
		}
		return otp;

	}

}
