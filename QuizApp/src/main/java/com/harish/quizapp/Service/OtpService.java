package com.harish.quizapp.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.EmailTokenRepo;
import com.harish.quizapp.DataRepos.OtpRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Model.EmailVerificationToken;
import com.harish.quizapp.Model.OtpLogs;
import com.harish.quizapp.Model.UserRegistration;


@Service
public class OtpService
{
	
	@Autowired
	private UserRepo urep;
	@Autowired
	private OtpRepo ot;
	@Autowired
	private EmailService es;
	@Autowired 
	private EmailTokenRepo etr;
	
	public ResponseEntity<String> sendOtp()
	{
		Integer otp=0;
		
		SecureRandom rand=new SecureRandom();
		
		otp=rand.nextInt(900000) +100000;
		
		OtpLogs ol=new OtpLogs();
		UserRegistration ur=  urep.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
		
		ol.setOtp(otp);
		ol.setGeneratedTime(LocalDateTime.now());
		ol.setUser(ur);
		ol.setIsValid(true);
		ol.setIsVerified(false);
		
		ot.save(ol);
		
		Boolean isValid=es.sendMail(ur.getEmail(),otp.toString(),"ADMIN");
		
		if(isValid)
		{
			return ResponseEntity.status(HttpStatus.OK).body("Otp sent !");
		}
		else
		{
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Invalid Email");
		}
		
	}
	
	public ResponseEntity<Boolean> verify(int otp)
	{
		OtpLogs ol= ot.findByOtpAndIsValidTrue(otp).orElseThrow();
		
		Boolean isValid=ol.getGeneratedTime().plusSeconds(15).isAfter(LocalDateTime.now());
		
		
		if(isValid)
		{
			ol.setIsValid(false);
			ol.setIsVerified(true);
			ot.save(ol);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
		}
		else
		{
			ol.setIsValid(false);
			ol.setIsVerified(false);
			ot.save(ol);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
		}
	}
	
	public ResponseEntity<String> sendThroughEmail(String email)
	{
		Integer otp=0;
		SecureRandom rand=new SecureRandom();
		
		otp=rand.nextInt(900000) +100000;
		
		
		EmailVerificationToken evt= new EmailVerificationToken();
		
		evt.setGeneratedTime(LocalDateTime.now());
		evt.setEmail(email);
		evt.setOtp(otp);
		evt.setIsValid(true);
		evt.setIsVerified(false);
		
		etr.save(evt);
		
		Boolean isValid=es.sendMail(email,otp.toString(),"USER");
		
		if(isValid)
		{
			return ResponseEntity.status(HttpStatus.OK).body("Otp sent for Registration !");
		}
		else
		{
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Invalid Email");
		}
		
	}
	
	public ResponseEntity<Boolean> verifyThroughEmail(int otp)
	{
		EmailVerificationToken evt= etr.findByOtpAndIsValidTrue(otp).orElseThrow();
		
		Boolean isVerified=evt.getGeneratedTime().plusMinutes(2).isAfter(LocalDateTime.now());
		
		evt.setIsValid(false);
		
		if(isVerified)
		{
			evt.setIsVerified(true);
			etr.save(evt);
			
			return ResponseEntity.status(HttpStatus.OK).body(true);
		}
		else
		{
			evt.setIsVerified(false);
			etr.save(evt);
			
			return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
		}
	}
}
