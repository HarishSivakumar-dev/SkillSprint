package com.harish.quizapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.Service.UserLoginService;

@RestController
@RequestMapping("/app")
public class UserLoginController
{
	
	@Autowired 
	private UserLoginService uls;
	
	@PostMapping("/login")
	public ResponseEntity<String> loginController(@RequestBody UserRegistration rd)
	{
		return uls.loginAuthenticationService(rd);
	}

}
