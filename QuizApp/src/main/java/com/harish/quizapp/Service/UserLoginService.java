package com.harish.quizapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.Util.JwtUtil;

@Service
public class UserLoginService
{
	@Autowired
	private JwtUtil jwt;
	@Autowired 
	private AuthenticationManager am;

	public ResponseEntity<String> loginAuthenticationService(UserRegistration rd)
	{
		
		String name=rd.getUserName();
		String pass=rd.getPassword();
		
		am.authenticate(new UsernamePasswordAuthenticationToken(name,pass));
		
		String access=jwt.generateToken(name);
		String refresh=jwt.generateRefresh(name,pass);
		
		return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Access Token:    "+ access +"Refresh Token:   "+ refresh );
		
	}
}
