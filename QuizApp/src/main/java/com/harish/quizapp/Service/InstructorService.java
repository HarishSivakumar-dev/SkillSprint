package com.harish.quizapp.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.AdminPromotionRepo;
import com.harish.quizapp.DataRepos.CourseCompletionRepo;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.EnrollmentRepo;
import com.harish.quizapp.DataRepos.FeedbackRepo;
import com.harish.quizapp.DataRepos.OtpRepo;
import com.harish.quizapp.DataRepos.RoleRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.DataRepos.ViolationTableRepo;
import com.harish.quizapp.Dto.ApplicationDto;
import com.harish.quizapp.Model.AdminApplication;
import com.harish.quizapp.Model.CourseCompletionStatus;
import com.harish.quizapp.Model.FeedbackTable;
import com.harish.quizapp.Model.OtpLogs;
import com.harish.quizapp.Model.Roles;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.enums.PromotionStatus;
import com.harish.quizapp.enums.PromotionType;

@Service
public class InstructorService 
{
	@Autowired
	private UserRepo ur;
	@Autowired
	private CoursesRepo cr;
	@Autowired
	private FeedbackRepo fr;
	@Autowired 
	private ViolationTableRepo vtr;
	@Autowired
	private EnrollmentRepo er;
	@Autowired 
	private OtpRepo otp;
	@Autowired
	private RoleRepo rr;
	@Autowired
	private CourseCompletionRepo ccr;
	@Autowired 
	private AdminPromotionRepo apr;
	
	public ResponseEntity<String> applyForAdmin(ApplicationDto app)
	{
		AdminApplication admin=new AdminApplication();
		
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration user= ur.findByUserName(name).orElseThrow();
		String email=user.getEmail();
		LocalDateTime date=LocalDateTime.now();
		
		int totcourses=cr.countByInstructorAndStatus(user, "Completed");
		int feedbackcount=fr.countByInstructor(user);
		
		List<FeedbackTable> ft= fr.findByInstructor(user);
		
		float rating=0;
		int num=0;
		for(FeedbackTable tb : ft)
		{
			rating+=tb.getRating();
			num++;
		}
		
		float avgrating=rating/num;
		
		Boolean violation= vtr.findByInstructor_Id(user.getId()).get().isViolated();
		
		LocalDateTime now=LocalDateTime.now();
		LocalDateTime join= user.getJoinedDate();
		long exp=ChronoUnit.YEARS.between(now, join);
		
		int nostud= er.findDistinctByCourse_Instructor_Id(user.getId()).size();
		Optional<OtpLogs> isVerified= otp.findByUserAndIsVerifiedTrue(user);
		Boolean verified;
		
		if(isVerified.isEmpty())
		{
			verified=false;
		}
		else
		{
			verified=true;
		}
		
		List<CourseCompletionStatus> ls= ccr.findByCourse_Instructor_Id(user.getId());
		int completedStud=ls.size();
	
		admin.setAchievements(app.getApplication());
		admin.setAppliedDate(date);
		admin.setAvgrating(avgrating);
		admin.setDocumentsUrl(app.getDocumentUrl());
		admin.setExpYears(exp);
		admin.setFeedbackcount(feedbackcount);
		admin.setInstructorEmail(email);
		admin.setIsVerified(verified);
		admin.setReasonForApplication(app.getApplication());
		admin.setRemarks(null);
		admin.setReviewedOn(date);
		admin.setStudTrained(nostud);
		admin.setSuperAdmin(null);
		admin.setTotcourses(totcourses);
		admin.setUser(user);
		admin.setIsViolated(violation);
		
		if(totcourses>=15 && feedbackcount>=20 && avgrating>=60.00 && !violation && exp>=4 && nostud>=300 && verified && completedStud>=200)
		{
			Roles r= rr.findByRolename("ROLE_ADMIN").orElseThrow();
			user.getRoles().add(r);
			ur.save(user);
			
			admin.setType(PromotionType.Auto);
			admin.setAutoEvaluation(true);
			admin.setPromotionStatus(PromotionStatus.Promoted);
			apr.save(admin);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Promoted Automatically");
		}
		else if(totcourses<8 || feedbackcount<10 || avgrating<30.00 || violation || exp<2 || nostud<150 || !verified || completedStud<100)
		{
			admin.setType(PromotionType.Auto);
			admin.setAutoEvaluation(true);
			admin.setPromotionStatus(PromotionStatus.Rejected);
			apr.save(admin);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Application didnot meet the criteria !");
		}
		else
		{
			admin.setType(PromotionType.Manual);
			admin.setAutoEvaluation(false);
			admin.setPromotionStatus(PromotionStatus.Pending);
			apr.save(admin);
			
			return ResponseEntity.status(HttpStatus.OK).body("Submitted");
		}
	}
	
	public ResponseEntity<AdminApplication> checkStatus()
	{
		UserRegistration regis = ur.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
		AdminApplication app=apr.findByUser_Id(regis.getId()).orElseThrow();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(app);
	}
}
