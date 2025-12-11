package com.harish.quizapp.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.CourseCompletionRepo;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.EnrollmentRepo;
import com.harish.quizapp.DataRepos.FeedbackRepo;
import com.harish.quizapp.DataRepos.InstructorRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Model.FeedbackTable;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.UserRegistration;

@Service
public class InstructorProfileService
{
	
	@Autowired
	private InstructorRepo ir;
	@Autowired
	private EnrollmentRepo er;
	@Autowired
	private CoursesRepo cr;
	@Autowired 
	private CourseCompletionRepo ccr;
	@Autowired
	private UserRepo ur;
	@Autowired
	private FeedbackRepo fr;
	
	
	public ResponseEntity<InstructorProfile> getInstructorProfile()
	{
		String user= SecurityContextHolder.getContext().getAuthentication().getName();
		InstructorProfile ip= ir.findByUserName_UserName(user).orElseThrow();
		UserRegistration usr= ur.findByUserName(user).orElseThrow();
		
		 int totCourses=cr.countByInstructor(usr);
		 int totStudents=er.countDistinctUserByCourse_Instructor(usr);
		 int totReviews=fr.countByInstructor(usr);
		 
		 List<FeedbackTable> rat= fr.findByInstructor(usr);
		 float avgRating=0;
		 
		 if(rat.size()!=0)
		 {
			 
			 float sum=rat.stream()
				 	  .mapToInt(r->r.getRating())
				 	  .sum();
		 
			 avgRating= sum/rat.size();
		 }
		 
		 float completionRate= ccr.countDistinctUserByCourse_Instructor_Id(usr.getId());
		 Period general= Period.between(ip.getJoinedDate(), LocalDate.now());
		 int yearOfExp= general.getYears();
		 int monthOfExp= general.getMonths();
		 String totExp= yearOfExp+" "+"Years" +monthOfExp+" "+"Months";
		 
		 ip.setTotCourses(totCourses);
		 ip.setTotStudents(totStudents);
		 ip.setTotReviews(totReviews);
		 ip.setAvgRating(avgRating);
		 ip.setCompletionRate(completionRate);
		 ip.setTotExp(totExp);
		 
		 
		 InstructorProfile prof= ir.save(ip);
		 
		 return ResponseEntity.status(HttpStatus.OK).body(prof);
	}
	

}
