package com.harish.quizapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import java.time.Instant;
import java.util.Date;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
	@Autowired 
	private MailSender sender;
	
	String ml="harishss.2k07@gmail.com";
	
	public Boolean sendMail(String mail, String otp, String calling)
	{
		EmailValidator validator = EmailValidator.getInstance();
		
		if(validator.isValid(mail))
		{
			Instant is=Instant.now();
			SimpleMailMessage msg=new SimpleMailMessage();
			
			msg.setFrom(ml);
			msg.setTo(mail);
			msg.setSentDate(Date.from(is));
			if(calling.equals("ADMIN"))
			{
				msg.setSubject("OTP for Admin Promotion");
				msg.setText("Your OTP is valid for 15 seconds, Please dont share it, inorder to ensure the safety ultimately of yourself and of the platform. The OTP IS "+" "+otp);
			}
			else
			{
				msg.setSubject("OTP for Registration Verification");
				msg.setText("Your OTP is valid for 2 Minutes, Please dont share it, inorder to ensure the safety ultimately of yourself and of the platform. The OTP IS "+" "+otp);
			}
			
			sender.send(msg);
			
			return true;
		}
		else
		{
			return false;
		}
		
	}
	

}
