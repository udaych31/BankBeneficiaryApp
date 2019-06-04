package com.hcl.bank.benef.app.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.bank.benef.app.dto.DeleteResponse;
import com.hcl.bank.benef.app.dto.OtpRequest;
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

	@Autowired
	ManagePayeeRepository managePayeeRepository;

	@Autowired
	AccountSummaryRepository accountSummaryRepository;

	@Autowired
	EmailSender emailSender;

	@Autowired
	OtpRepository otpRepository;

	private static final Logger logger = LogManager.getLogger(BankBeneficiaryServiceImpl.class);

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
