package com.harish.quizapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.quizapp.Dto.RegistrationDto;
import com.harish.quizapp.Service.UserRegistrationService;

@RestController
@RequestMapping("/app")
public class UserRegistrationController 
{
	@Autowired
	private UserRegistrationService urs;
	
	
	@PostMapping("/register")
	public ResponseEntity<String> userRegisterations(@RequestBody RegistrationDto ur)
	{
		System.out.println("entered controller");
		return urs.registerUserIntoDb(ur);
	}
	
	

}
