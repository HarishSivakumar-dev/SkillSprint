package com.harish.quizapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.quizapp.Dto.UserPersonalDetailsDto;
import com.harish.quizapp.Model.UserProfile;
import com.harish.quizapp.Service.UserProfileService;

@RestController
@RequestMapping("/user/profile")
public class UserProfileController
{
	
	@Autowired
	private UserProfileService ups;
	
	@PostMapping("/personal")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> addUserPersonalDataToProfile(@RequestBody UserPersonalDetailsDto upd)
	{
		return ups.addUserProfilePersonalDetails(upd);
	}
	
	@GetMapping("/")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserProfile> getUserProfileData()
	{
		return ups.getUserProfileDetails();
	}

}
