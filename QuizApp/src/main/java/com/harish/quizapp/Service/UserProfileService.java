package com.harish.quizapp.Service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import com.harish.quizapp.DataRepos.UserProfileRepo;
import com.harish.quizapp.Dto.UserPersonalDetailsDto;
import com.harish.quizapp.Dto.UserStudyProfileDto;
import com.harish.quizapp.Model.UserProfile;

public class UserProfileService
{
	@Autowired 
	private UserProfileRepo upr;

	
	public ResponseEntity<UserProfile> getUserProfileDetails()
	{
		String user= SecurityContextHolder.getContext().getAuthentication().getName();
		UserProfile up=upr.findByUserName(user).orElseThrow();
		
		return ResponseEntity.status(HttpStatus.OK).body(up);
	}
	
	public ResponseEntity<String> addUserProfilePersonalDetails(UserPersonalDetailsDto uppd)
	{
		Optional<UserProfile> up=upr.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		
	    UserProfile profile=up.get();
			
			if(uppd.getDateOfBirth()!=null) profile.setDateOfBirth(uppd.getDateOfBirth());
			if(uppd.getGender()!=null) profile.setGender(uppd.getGender());
			if(uppd.getPhoneNumber()!=null) profile.setPhoneNumber(uppd.getPhoneNumber());
			if(uppd.getUserBio()!=null) profile.setUserBio(uppd.getUserBio());
			if(uppd.getPlace()!=null) profile.setPlace(uppd.getPlace());
			
			upr.save(profile);
		
		return ResponseEntity.status(HttpStatus.OK).body("Updated");
		
	}
	
	public ResponseEntity<String> addUserStudyDetails(UserStudyProfileDto dto)
	{
		Optional<UserProfile> up=upr.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

		UserProfile profile=up.get();
			
			if(dto.getCollegeName()!=null) profile.setCollegeName(dto.getCollegeName());
			if(dto.getDepartment()!=null) profile.setDepartment(dto.getDepartment());
			if(dto.getLinkedIn()!=null) profile.setLinkedIn(dto.getLinkedIn());
			if(dto.getYearOfStudy()!=0) profile.setYearOfStudy(dto.getYearOfStudy());;
			
			upr.save(profile);
		
		return ResponseEntity.status(HttpStatus.OK).body("Updated");
	}
	
}
