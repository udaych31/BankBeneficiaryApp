package com.hcl.bank.benef.app.util;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.hcl.bank.benef.app.dto.OtpRequest;
import com.hcl.bank.benef.app.entity.OtpDetails;
import com.hcl.bank.benef.app.repository.OtpRepository;

@Component
public class EmailSender {
	
	private static final Logger logger=LogManager.getLogger(EmailSender.class);
	
	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	private OtpRepository otpRepository;

	public void sendOtp(OtpRequest request) throws Exception{
		try {
			
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(request.getEmail());
			message.setSubject("OTP verification");
			Random random=new Random();
			int otp = random.nextInt(100000);
			Long userOtp=Long.valueOf(""+otp);
			
			OtpDetails findByAccountNo = otpRepository.findByAccountNo(request.getAccountNo());
			if(findByAccountNo!=null) {
				findByAccountNo.setOtp(userOtp);
				findByAccountNo.setOtpUsed('F');
				message.setText("This is OTP for adding payee : "+userOtp);
			}
			emailSender.send(message);
			
		} catch (Exception e) {
			logger.error(this.getClass().getName()+" sendOtp :"+e.getMessage());
		}
	}

}
