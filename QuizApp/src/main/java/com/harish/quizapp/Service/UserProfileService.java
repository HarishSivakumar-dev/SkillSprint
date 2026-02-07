package com.harish.quizapp.Service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.UserProfileRepo;
import com.harish.quizapp.Dto.UserPersonalDetailsDto;
import com.harish.quizapp.Dto.UserProfileDto;
import com.harish.quizapp.Dto.UserStudyProfileDto;
import com.harish.quizapp.Model.UserProfile;


@Service
public class UserProfileService
{
	@Autowired 
	private UserProfileRepo upr;

	
	public ResponseEntity<UserProfileDto> getUserProfileDetails()
	{
		String user= SecurityContextHolder.getContext().getAuthentication().getName();
		UserProfile up=upr.findByUserName_UserName(user).orElseThrow();
		
		UserProfileDto dto= new UserProfileDto();
		dto.setAvgCourseCertificationRate(up.getAvgCourseCertificationRate());
		dto.setAvgCourseCompletionRate(up.getAvgCourseCompletionRate());
		dto.setAvgQuizezCleared(up.getAvgQuizezCleared());
		dto.setCollegeName(up.getCollegeName());
		dto.setCoursesCompleted(up.getCoursesCompleted());
		dto.setDateOfBirth(up.getDateOfBirth());
		dto.setDepartment(up.getDepartment());
		dto.setEmail(up.getEmail());
		dto.setFullName(up.getFullName());
		dto.setGender(up.getGender());
		dto.setIsEmailVerified(up.getIsEmailVerified());
		dto.setJoinedDate(up.getJoinedDate());
		dto.setLevel(up.getLevel());
		dto.setNoOfCertificates(up.getNoOfCertificates());
		dto.setPhoneNumber(up.getPhoneNumber());
		dto.setPlace(up.getPlace());
		dto.setQuizzesAttended(up.getQuizzesAttended());
		dto.setQuizzesCleared(up.getQuizzesCleared());
		dto.setStreakMaintanance(up.getStreakMaintanance());
		dto.setTotCoursesEnrolled(up.getTotCoursesEnrolled());
		dto.setUserBio(up.getUserBio());
		dto.setYearOfStudy(up.getYearOfStudy());
		dto.setUserId(up.getUserName().getId());
		dto.setUserName(up.getUserName().getUserName());
		
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	public ResponseEntity<String> addUserProfilePersonalDetails(UserPersonalDetailsDto uppd)
	{
		Optional<UserProfile> up=upr.findByUserName_UserName(SecurityContextHolder.getContext().getAuthentication().getName());
		
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
		Optional<UserProfile> up=upr.findByUserName_UserName(SecurityContextHolder.getContext().getAuthentication().getName());

		UserProfile profile=up.get();
			
			if(dto.getCollegeName()!=null) profile.setCollegeName(dto.getCollegeName());
			if(dto.getDepartment()!=null) profile.setDepartment(dto.getDepartment());
			if(dto.getLinkedIn()!=null) profile.setLinkedIn(dto.getLinkedIn());
			if(dto.getYearOfStudy()!=0) profile.setYearOfStudy(dto.getYearOfStudy());;
			
			upr.save(profile);
		
		return ResponseEntity.status(HttpStatus.OK).body("Updated");
	}
	
}
