package com.harish.quizapp.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.AdminPromotionRepo;
import com.harish.quizapp.DataRepos.CourseCompletionRepo;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.EnrollmentRepo;
import com.harish.quizapp.DataRepos.FeedbackRepo;
import com.harish.quizapp.DataRepos.InstructorRepo;
import com.harish.quizapp.DataRepos.OtpRepo;
import com.harish.quizapp.DataRepos.RoleRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.DataRepos.ViolationTableRepo;
import com.harish.quizapp.Dto.ApplicationDto;
import com.harish.quizapp.Model.AdminApplication;
import com.harish.quizapp.Model.FeedbackTable;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.OtpLogs;
import com.harish.quizapp.Model.Roles;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.enums.PromotionStatus;
import com.harish.quizapp.enums.PromotionType;

@Service
@Component
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
	@Autowired
	private InstructorRepo ir;
	
	public ResponseEntity<String> applyForAdmin(ApplicationDto app)
	{
		AdminApplication admin=new AdminApplication();
		
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		InstructorProfile rep= ir.findByUserName_UserName(name).orElseThrow();
		String email=rep.getMail();
		LocalDateTime date=LocalDateTime.now();
		
		int totcourses=cr.countByInstructorAndStatus(rep, "Completed");
		int feedbackcount=fr.countByInstructor(rep);
		
		List<FeedbackTable> ft= fr.findByInstructor(rep);
		
		float rating=0;
		int num=0;
		for(FeedbackTable tb : ft)
		{
			rating+=tb.getRating();
			num++;
		}
		
		float avgrating=rating/num;
		
		Boolean violation= vtr.findByInstructor_Id(rep.getId()).get().isViolated();
		
		LocalDateTime now=LocalDateTime.now();
		LocalDate join= rep.getJoinedDate();
		long exp=ChronoUnit.MONTHS.between(join, now);
		long exp1=exp/12;
		
		int nostud= er.countDistinctUserByCourse_Instructor(rep);
		Optional<OtpLogs> isVerified= otp.findByUserAndIsVerifiedTrue(rep);
		Boolean verified;
		
		if(isVerified.isEmpty())
		{
			verified=false;
		}
		else
		{
			verified=true;
		}
		
		int completedStud= ccr.countDistinctUserByCourse_Instructor_Id(rep.getId());
	
		admin.setAchievements(app.getApplication());
		admin.setAppliedDate(date);
		admin.setAvgrating(avgrating);
		admin.setDocumentsUrl(app.getDocumentUrl());
		admin.setExpYears(exp1);
		admin.setFeedbackcount(feedbackcount);
		admin.setInstructorEmail(email);
		admin.setIsVerified(verified);
		admin.setReasonForApplication(app.getApplication());
		admin.setRemarks(null);
		admin.setReviewedOn(date);
		admin.setStudTrained(nostud);
		admin.setAdminManager(null);
		admin.setTotcourses(totcourses);
		admin.setUser(rep);
		admin.setIsViolated(violation);
		
		if(totcourses>=15 && feedbackcount>=20 && avgrating>=60.00 && !violation && exp>=4 && nostud>=300 && verified && completedStud>=200)
		{
			Roles r= rr.findByRolename("ROLE_ADMIN").orElseThrow();
			UserRegistration user=rep.getUserName();
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
