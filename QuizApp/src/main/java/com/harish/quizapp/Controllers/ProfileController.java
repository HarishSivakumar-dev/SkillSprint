package com.harish.quizapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.quizapp.Model.UserProfile;
import com.harish.quizapp.Service.UserProfileService;

@RestController
@RequestMapping("/app/")
public class ProfileController 
{
	
	@Autowired
	private UserProfileService ups;
	
	
	@GetMapping("")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserProfile> getUserProfileDetails()
	{
		return ups.getUserProfileDetails();
	}
	

}
