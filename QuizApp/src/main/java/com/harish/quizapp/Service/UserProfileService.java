package com.harish.quizapp.Service;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.UserProfileRepo;
import com.harish.quizapp.Dto.UserPersonalDetailsDto;
import com.harish.quizapp.Dto.UserProfileDto;
import com.harish.quizapp.Dto.UserStudyProfileDto;
import com.harish.quizapp.Model.UserProfile;
import jakarta.transaction.Transactional;


@Service
public class UserProfileService
{
	@Autowired 
	private UserProfileRepo upr;
	
	@Autowired
	@Qualifier(value="redisTemplate")
	private RedisTemplate<String, UserProfile> rt;

	
	public ResponseEntity<UserProfileDto> getUserProfileDetails()
	{
		String user= SecurityContextHolder.getContext().getAuthentication().getName();
		UserProfile up=rt.opsForValue().get(user);
		if(up==null)
		{
			up= upr.findByUserName_UserName(user).orElseThrow();
			rt.opsForValue().set(user,up, 10, TimeUnit.MINUTES);
		}
		
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
	
	@Transactional
	public ResponseEntity<String> addUserProfilePersonalDetails(UserPersonalDetailsDto uppd)
	{
		    UserProfile profile= rt.opsForValue().get(SecurityContextHolder.getContext().getAuthentication().getName());
		    
		    if(profile==null)
		    {
		    	profile=upr.findByUserName_UserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
		    }
			
			if(uppd.getDateOfBirth()!=null) profile.setDateOfBirth(uppd.getDateOfBirth());
			if(uppd.getGender()!=null) profile.setGender(uppd.getGender());
			if(uppd.getPhoneNumber()!=null) profile.setPhoneNumber(uppd.getPhoneNumber());
			if(uppd.getUserBio()!=null) profile.setUserBio(uppd.getUserBio());
			if(uppd.getPlace()!=null) profile.setPlace(uppd.getPlace());
			
			UserProfile usr=upr.save(profile);
			
			rt.opsForValue().set(SecurityContextHolder.getContext().getAuthentication().getName(), usr, 10, TimeUnit.MINUTES);
		
		return ResponseEntity.status(HttpStatus.OK).body("Updated");
		
	}
	
	@Transactional
	public ResponseEntity<String> addUserStudyDetails(UserStudyProfileDto dto)
	{
		    UserProfile profile=rt.opsForValue().get(SecurityContextHolder.getContext().getAuthentication().getName());
			
		    if(profile==null)
		    {
		    	profile=upr.findByUserName_UserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
		    }
		    
			if(dto.getCollegeName()!=null) profile.setCollegeName(dto.getCollegeName());
			if(dto.getDepartment()!=null) profile.setDepartment(dto.getDepartment());
			if(dto.getLinkedIn()!=null) profile.setLinkedIn(dto.getLinkedIn());
			if(dto.getYearOfStudy()!=0) profile.setYearOfStudy(dto.getYearOfStudy());;
			
			UserProfile pr=upr.save(profile);
			
			rt.opsForValue().set(SecurityContextHolder.getContext().getAuthentication().getName(), pr, 10, TimeUnit.MINUTES);
		
		return ResponseEntity.status(HttpStatus.OK).body("Updated");
	}
	
}
