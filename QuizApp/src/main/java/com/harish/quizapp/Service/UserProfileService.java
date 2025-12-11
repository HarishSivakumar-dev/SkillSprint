package com.harish.quizapp.Service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import com.harish.quizapp.DataRepos.AttemptsRepo;
import com.harish.quizapp.DataRepos.CourseCompletionRepo;
import com.harish.quizapp.DataRepos.EnrollmentRepo;
import com.harish.quizapp.DataRepos.StreakMainRepo;
import com.harish.quizapp.DataRepos.UserProfileRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Dto.UserPersonalDetailsDto;
import com.harish.quizapp.Dto.UserStudyProfileDto;
import com.harish.quizapp.Model.StreakTable;
import com.harish.quizapp.Model.UserProfile;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.enums.CompletionStatus;
import com.harish.quizapp.enums.SkillLevelEnum;

public class UserProfileService
{
	@Autowired 
	private UserProfileRepo upr;
	@Autowired
	private StreakMainRepo smr;
	@Autowired 
	private EnrollmentRepo er;
	@Autowired 
	private CourseCompletionRepo ccr; 
	@Autowired
	private AttemptsRepo ar;
	@Autowired
	private UserRepo rep;

	
	public ResponseEntity<UserProfile> getUserProfileDetails()
	{
		String user= SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration re= rep.findByUserName(user).orElseThrow();
		
		UserProfile up=upr.findByUserName(user).orElseThrow();
		StreakTable tb= smr.findByUserId(re);
		
		int coursesEnrolled=er.countByUser(up.getUserName());
		int coursesCompleted=ccr.countByUser(up.getUserName());
		int certificates=ccr.countByUserAndCourseCompletionStatus(up.getUserName(),CompletionStatus.CompletedAndCertified);
		int quizzesAttended= ar.countByUser(up.getUserName());
		int quizzesCleared=ar.countByUserAndStatus(up.getUserName(),"PASSED");
		
		float avgClearingRate= (quizzesAttended >0 ) ? (quizzesCleared/ (float)quizzesAttended)*100 : 0;
		float avgCompletionRate= (coursesEnrolled >0) ? (coursesCompleted/ (float)coursesEnrolled)*100 : 0;
		float avgCertiRate=(coursesCompleted>0) ? (certificates/ (float)coursesCompleted)*100 : 0;
		
		
		SkillLevelEnum level= this.allocateLevel(avgClearingRate, avgCompletionRate, avgCertiRate);
		
		up.setTotCoursesEnrolled(coursesEnrolled);
		up.setCoursesCompleted(coursesCompleted);
		up.setNoOfCertificates(certificates);
		up.setQuizzesAttended(quizzesAttended);
		up.setAvgQuizezCleared(quizzesCleared);
		up.setAvgClearingRate(avgClearingRate);
		up.setStreakMaintanance(tb.getStreak());
		up.setLevel(level);
		
		upr.save(up);
		
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
	
	private SkillLevelEnum allocateLevel(float clearingrate, float completionrate, float certrate)
	{
		float course=(float) 0.30;
		float certi=(float) 0.45;
		float quiz=(float) 0.25;
		
		float score= (quiz*clearingrate) + (course*completionrate) + (certi * certrate);
		
		if(score<=40.00)
		{
			return SkillLevelEnum.Beginner;
		}
		else if(score>40.00 && score<=60.00)
		{
			return SkillLevelEnum.Intermediate;
		}
		else if(score>60.00 && score<=75)
		{
			return SkillLevelEnum.AdvancedIntermediate;
		}
		else if(score>75 && score<=85)
		{
			return SkillLevelEnum.Advanced;
		}
		else if(score>85 && score<=95)
		{
			return SkillLevelEnum.Expert;
		}
		else
		{
			return SkillLevelEnum.Master;
		}
	}
}
