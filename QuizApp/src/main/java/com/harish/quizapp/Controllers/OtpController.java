package com.harish.quizapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harish.quizapp.Dto.EmailTokenDto;
import com.harish.quizapp.Dto.OtpDto;
import com.harish.quizapp.Service.OtpService;

@RestController
@RequestMapping("/app")
public class OtpController
{
	@Autowired 
	private OtpService otp;
	
	@GetMapping("/otp/send")
	public ResponseEntity<String> requestOtp()
	{
		return otp.sendOtp();
	}
	
	@PostMapping("/otp/verify")
	public ResponseEntity<Boolean> verifyOtp(@RequestBody OtpDto dto)
	{
		return otp.verify(dto.getOtp());
	}
	
	@PostMapping("/otp/registration/send")
	public ResponseEntity<String> generateOtpDuringRegistration(@RequestBody EmailTokenDto et)
	{
		return otp.sendThroughEmail(et.getEmail());
	}
	
	@PostMapping("/otp/registration/verify")
	public ResponseEntity<Boolean> verifyOtpDuringRegistration(@RequestBody OtpDto dto)
	{
		return otp.verifyThroughEmail(dto.getOtp());
	}

}
