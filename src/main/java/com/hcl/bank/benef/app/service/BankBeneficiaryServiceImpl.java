package com.hcl.bank.benef.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.bank.benef.app.dto.AddPayeeRequest;
import com.hcl.bank.benef.app.dto.AddPayeeResponse;
import com.hcl.bank.benef.app.dto.ConfirmPayeeRequest;
import com.hcl.bank.benef.app.dto.ConfirmPayeeResponse;
import com.hcl.bank.benef.app.dto.DeleteResponse;
import com.hcl.bank.benef.app.dto.EditPayeeResponse;
import com.hcl.bank.benef.app.dto.OtpRequest;
import com.hcl.bank.benef.app.dto.PayeeDto;
import com.hcl.bank.benef.app.dto.PayeeListResponse;
import com.hcl.bank.benef.app.dto.ValidateOtpRequest;
import com.hcl.bank.benef.app.dto.ValidateRequest;
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
					payee.setAccountNo(1L);
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
	
	public DeleteResponse deletePayee(Long payeeId) {

		logger.info("Enter into delete payee method");
		DeleteResponse response = new DeleteResponse();
		try {

			if (payeeId != null) {

				Optional<ManagePayee> magepayee = payeeRepository.findById(payeeId);

				ManagePayee payee = magepayee.get();

				if (magepayee.isPresent()) {

					Optional<AccountSummary> accountSummary = accountRepository
							.findById(magepayee.get().getAccountNo());

					if (accountSummary.isPresent()) {

						AccountSummary account = accountSummary.get();
						OtpRequest otpRequest = new OtpRequest();
						otpRequest.setAccountNo(payee.getPayeeAccountNo());
						otpRequest.setEmail(account.getEmail());

						emailSender.sendOtp(otpRequest);
						response.setMessage("otp sent successfully to your email");
						response.setStatusCode(200);

					}

				}
		
			}

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatusCode(401);
			logger.error(e.getClass().getName() + " send otp was failed " + e.getMessage());
		}

		return response;
	}

	public DeleteResponse confirmDeletePayee(ValidateRequest requesst) {

		DeleteResponse response = new DeleteResponse();

		try {

			ValidateRequest otpReq = new ValidateRequest();
			otpReq.setPayeeId(requesst.getPayeeId());
			otpReq.setOtp(requesst.getOtp());
			OtpDetails otp = validateOtp(otpReq);

			Optional<ManagePayee> magepayee = payeeRepository.findById(requesst.getPayeeId());

			if (magepayee.isPresent()) {

				Optional<AccountSummary> accountSummary = accountRepository
						.findById(magepayee.get().getAccountNo());

				if (accountSummary.isPresent()) {

					OtpDetails otpDetails = otpRepository.findByAccountNo(accountSummary.get().getAccountNo());
					if (otp != null) {
						otpDetails.setOtpUsed('T');
						otpRepository.save(otpDetails);
						payeeRepository.deleteByPayeeId(otpReq.getPayeeId());
						response.setMessage("Payee was deleted successfully");
						response.setStatusCode(200);

					}
				}

			} else {

				throw new BeneficiaryServiceException("Invalid otp");

			}

		} catch (Exception e) {
			response.setMessage("Payee was not deleted");
			response.setStatusCode(401);
			logger.error(logger.getClass().getName() + " " + e.getMessage());

		}
		return response;

	}

	public OtpDetails validateOtp(ValidateRequest request) {
		OtpDetails otp = null;
		try {
			Optional<ManagePayee> magepayee = payeeRepository.findById(request.getPayeeId());

			ManagePayee payee = magepayee.get();

			otp = otpRepository.findByAccountNo(payee.getAccountNo());
			if (otp != null && otp.getOtp().longValue() == request.getOtp().longValue() && otp.getOtpUsed().equals("F")) {
				return otp;
			} else {
				throw new BeneficiaryServiceException("validation otp was failed");
			}
		} catch (Exception e) {

			logger.error(e.getClass().getName() + " " + e.getMessage());
		}
		return otp;

	}
}
