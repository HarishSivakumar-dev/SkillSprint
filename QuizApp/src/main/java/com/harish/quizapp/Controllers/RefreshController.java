package com.harish.quizapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.quizapp.Model.RefreshToken;
import com.harish.quizapp.Service.RefreshTokenService;


@RestController
@RequestMapping("/app")
public class RefreshController
{
	@Autowired 
	private RefreshTokenService rts;
	
	@PostMapping("/refresh")
	public ResponseEntity<String> refreshController(@RequestBody RefreshToken rt)
	{
		String token=rt.getToken();
		
		return rts.ValidateRefreshToken(token);
	}

}
