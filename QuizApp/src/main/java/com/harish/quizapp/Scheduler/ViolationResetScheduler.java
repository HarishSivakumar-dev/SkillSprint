package com.harish.quizapp.Scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.harish.quizapp.DataRepos.AttemptsRepo;
import com.harish.quizapp.DataRepos.CourseCompletionRepo;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.EnrollmentRepo;
import com.harish.quizapp.DataRepos.FeedbackRepo;
import com.harish.quizapp.DataRepos.InstructorRepo;
import com.harish.quizapp.DataRepos.StreakMainRepo;
import com.harish.quizapp.DataRepos.UserProfileRepo;
import com.harish.quizapp.DataRepos.ViolationTableRepo;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.FeedbackTable;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.StreakTable;
import com.harish.quizapp.Model.UserProfile;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.Model.ViolationsTable;
import com.harish.quizapp.enums.CompletionStatus;
import com.harish.quizapp.enums.SkillLevelEnum;
import jakarta.transaction.Transactional;

@Component
public class ViolationResetScheduler
{
	@Autowired
	private ViolationTableRepo vtr;
	@Autowired
	private CoursesRepo rep;
	@Autowired
	private FeedbackRepo feed;
	@Autowired
	private InstructorRepo ir;
	@Autowired
	private CoursesRepo cr;
	@Autowired
	private FeedbackRepo fr;
	@Autowired 
	private CourseCompletionRepo ccr;
	@Autowired
	private EnrollmentRepo er;
	@Autowired 
	private UserProfileRepo upr;
	@Autowired
	private StreakMainRepo smr;
	@Autowired
	private AttemptsRepo ar;
	
	@Scheduled(cron="0 0 0 * * * ")
	public void resetViolationField()
	{
		try
		{
			List<ViolationsTable> tbl= vtr.findAll();
			
			List<ViolationsTable> newrec= new ArrayList<>();
			
			for(ViolationsTable vt : tbl)
			{
				if(vt.isViolated() && vt.getDateOfViolation().isBefore(LocalDateTime.now().minusDays(30)))
				{
					vt.setDateOfViolation(null);
					vt.setFinalViolationCount(0);
					vt.setInitialViolationCount(0);
					vt.setViolated(false);
					
					newrec.add(vt);	
				}
			}
			
			vtr.saveAll(newrec);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}	
	
	@Transactional
	@Scheduled(cron="0 10 0 * * * ")
	public void calibrateRatingForAllCourses()
	{
		try
		{
			List<CourseDetails> det= rep.findAll();
			List<FeedbackTable> ft= feed.findAll();
			List<CourseDetails> fin= new ArrayList<>();
			
			Map<Integer, List<FeedbackTable>> map= new HashMap<>();
			
			for(FeedbackTable cd : ft)
			{
				if(map.get(cd.getCourseId())!=null)
				{
					map.get(cd.getCourseId()).add(cd);
				}
				else
				{
					List<FeedbackTable> fbt= new ArrayList<>();
					fbt.add(cd);
					
					map.put(cd.getCourseId(),  fbt);
				}
			}
			
			for(CourseDetails fd : det)
			{
				List<FeedbackTable> cou = map.get(fd.getId());
				
				float rating = (cou.isEmpty() || cou==null) ? 0 : (float) (cou.stream()
								  .mapToInt(r->r.getRating())
								  .sum())/cou.size();
				fd.setRating(rating);
				
				fin.add(fd);
			}
			
			rep.saveAll(fin);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	@Transactional
	@Scheduled(cron="0 20 0 * * * ")
	public void updateInstructorProfileDetails()
	{
		try
		{
			List<InstructorProfile> ip= ir.findAll();
			
			for(InstructorProfile pro : ip)
			{
				UserRegistration usr= pro.getUserName();
				
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
				 
				 float completionRate=(totStudents > 0) ? ( (float) ccr.countDistinctUserByCourse_Instructor_Id(usr.getId()) / (float) totStudents) * 100 : 0;
				 Period general= Period.between(pro.getJoinedDate(), LocalDate.now());
				 int yearOfExp= general.getYears();
				 int monthOfExp= general.getMonths();
				 String totExp= yearOfExp+" "+"Years" +monthOfExp+" "+"Months";
				 
				 pro.setTotCourses(totCourses);
				 pro.setTotStudents(totStudents);
				 pro.setTotReviews(totReviews);
				 pro.setAvgRating(avgRating);
				 pro.setCompletionRate(completionRate);
				 pro.setTotExp(totExp);
				
			}
			
			ir.saveAll(ip);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		 
	}
	
	@Transactional
	@Scheduled(cron="0 0 * * * * ")
	public void updateUserProfileDetails()
	{
		try
		{
			List<UserProfile> usr= upr.findAll();
			
			for(UserProfile up : usr)
			{
				
			StreakTable tb= smr.findByUserId(up.getUserName());
			
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
			
			}
			
			upr.saveAll(usr);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
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
