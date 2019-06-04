package com.hcl.bank.benef.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.bank.benef.app.dto.DeleteResponse;
import com.hcl.bank.benef.app.dto.EditPayeeResponse;
import com.hcl.bank.benef.app.dto.OtpRequest;
import com.hcl.bank.benef.app.dto.PayeeDto;
import com.hcl.bank.benef.app.dto.PayeeListResponse;
import com.hcl.bank.benef.app.dto.ValidateRequest;
import com.hcl.bank.benef.app.entity.AccountSummary;
import com.hcl.bank.benef.app.entity.ManagePayee;
import com.hcl.bank.benef.app.entity.OtpDetails;
import com.hcl.bank.benef.app.repository.AccountSummaryRepository;
import com.hcl.bank.benef.app.repository.ManagePayeeRepository;
import com.hcl.bank.benef.app.repository.OtpRepository;
import com.hcl.bank.benef.app.util.BeneficiaryServiceException;
import com.hcl.bank.benef.app.util.EmailSender;

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

	@Autowired
	ManagePayeeRepository managePayeeRepository;

	@Autowired
	AccountSummaryRepository accountSummaryRepository;

	@Autowired
	EmailSender emailSender;

	@Autowired
	OtpRepository otpRepository;

	
	public DeleteResponse deletePayee(Long payeeId) {

		logger.info("Enter into delete payee method");
		DeleteResponse response = new DeleteResponse();
		try {

			if (payeeId != null) {

				Optional<ManagePayee> magepayee = managePayeeRepository.findById(payeeId);

				ManagePayee payee = magepayee.get();

				if (magepayee.isPresent()) {

					Optional<AccountSummary> accountSummary = accountSummaryRepository
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

			Optional<ManagePayee> magepayee = managePayeeRepository.findById(requesst.getPayeeId());

			if (magepayee.isPresent()) {

				Optional<AccountSummary> accountSummary = accountSummaryRepository
						.findById(magepayee.get().getAccountNo());

				if (accountSummary.isPresent()) {

					OtpDetails otpDetails = otpRepository.findByAccountNo(accountSummary.get().getAccountNo());
					if (otp != null) {
						otpDetails.setOtpUsed('T');
						otpRepository.save(otpDetails);
						managePayeeRepository.deleteByPayeeId(otpReq.getPayeeId());
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
			Optional<ManagePayee> magepayee = managePayeeRepository.findById(request.getPayeeId());

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
