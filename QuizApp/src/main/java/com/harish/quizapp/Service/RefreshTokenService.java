package com.harish.quizapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import com.harish.quizapp.Util.JwtUtil;

@Service
public class RefreshTokenService
{
	@Autowired
	private JwtUtil jwt;
	public ResponseEntity<String> ValidateRefreshToken(String token)
	{
		if(jwt.validateRefresh(token))
		{
			String name=jwt.clientRefresh(token);
			String access=jwt.generateToken(name);
			
			return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Access Token: " + access);
		}
		else
		{
			new BadCredentialsException("Invalid Refresh JWT");
			return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Invalid Refresh JWT");
		}	
	}
}
