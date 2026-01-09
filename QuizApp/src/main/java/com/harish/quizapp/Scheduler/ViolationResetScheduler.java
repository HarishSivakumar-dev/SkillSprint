package com.harish.quizapp.Scheduler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.harish.quizapp.DataRepos.AttemptsRepo;
import com.harish.quizapp.DataRepos.CourseCompletionRepo;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.EnrollmentRepo;
import com.harish.quizapp.DataRepos.FeedbackRepo;
import com.harish.quizapp.DataRepos.InstStatRepo;
import com.harish.quizapp.DataRepos.InstructorRepo;
import com.harish.quizapp.DataRepos.InstructorStatUpdateProjection;
import com.harish.quizapp.DataRepos.StreakMainRepo;
import com.harish.quizapp.DataRepos.UserProfileRepo;
import com.harish.quizapp.DataRepos.ViolationTableRepo;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.FeedbackTable;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.InstructorStatUpdate;
import com.harish.quizapp.Model.StreakTable;
import com.harish.quizapp.Model.UserProfile;
import com.harish.quizapp.Model.ViolationsTable;
import com.harish.quizapp.enums.CompletionStatus;
import com.harish.quizapp.enums.SkillLevelEnum;
import com.harish.quizapp.enums.StatUpdateEvent;
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
	@Autowired
	private InstStatRepo isr;

 
	
	@Transactional
	@Scheduled(cron="0 0 0 * * * ")
	public void resetViolationField()
	{
		try
		{
			List<ViolationsTable> tbl= vtr.findByViolatedTrue();
			List<InstructorProfile> ip= new ArrayList<>();
			Map<Integer, InstructorProfile> linkprof= new HashMap<>();
			
			for(ViolationsTable vio : tbl)
			{
				linkprof.put(vio.getId(), vio.getInstructor());
			}
			
			for(ViolationsTable vt : tbl)
			{
				if(vt.getDateOfViolation().isBefore(LocalDateTime.now().minusDays(30)))
				{
					vt.setDateOfViolation(null);
					vt.setFinalViolationCount(0);
					vt.setInitialViolationCount(0);
					vt.setViolated(false);
					
					InstructorProfile prof=linkprof.get(vt.getId());
					prof.setIsViolated(false);
					ip.add(prof);
				}
			}
			
			vtr.saveAll(tbl);
			ir.saveAll(ip);
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
				
				float rating = (cou==null || cou.isEmpty()) ? 0 : (cou.stream()
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
	@Scheduled(cron="0 */5 * * * * ")
	public void updateInstructorProfileDetails()
	{
		try
		{
			
			List<InstructorStatUpdateProjection> isup= isr.findByRecordsForStat();
			
			if(isup.isEmpty())
			{
				return;
			}
			else
			{
				Map<Integer, List<InstructorStatUpdateProjection>> mp1= new HashMap<>();
				
				for(InstructorStatUpdateProjection isu : isup)
				{
					List<InstructorStatUpdateProjection> pro= mp1.get(isu.getInstId());
					
					if(pro==null)
					{
						List<InstructorStatUpdateProjection> prod= new ArrayList<>();
						prod.add(isu);
						mp1.put(isu.getInstId(), prod);
					}
					else
					{
						pro.add(isu);
					}
				}
				
				Set<Integer> instid=mp1.keySet();
				
				List<InstructorProfile> pr= ir.findAllById(instid);
				List<InstructorProfile> prf= new ArrayList<>();
				
				for(InstructorProfile ins : pr)
				{
					List<InstructorStatUpdateProjection> is= mp1.get(ins.getId());
				
					for(InstructorStatUpdateProjection ipj : is)
					{
						this.applyDeltaLogic(ipj.getTotChange(),ipj.getEventType(), ins);
					}
					
					prf.add(ins);
					
				}
				
				List<InstructorStatUpdate> ipro= isr.findallPending();
				for(InstructorStatUpdate upd: ipro)
				{
					upd.setProceeded(true);
				}
				
				isr.saveAll(ipro);
				ir.saveAll(prf);
			}
			
			
		
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
			System.out.println("Entered user profile scheduler !");
			
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
	
	private void applyDeltaLogic(int deltaval, StatUpdateEvent sue, InstructorProfile ip)
	{
		if(sue.equals(StatUpdateEvent.ENROLLMENT))
		{
			ip.setTotStudents(ip.getTotStudents()+deltaval);
		}
		else if(sue.equals(StatUpdateEvent.UNENROLLMENT))
		{
			ip.setTotStudents(ip.getTotStudents()-deltaval);
		}
		else if(sue.equals(StatUpdateEvent.COURSE))
		{
			ip.setTotCourses(ip.getTotCourses()+deltaval);
		}
		else if(sue.equals(StatUpdateEvent.FEEDBACK))
		{
			ip.setTotReviews(ip.getTotReviews()+deltaval);
		}
		else if(sue.equals(StatUpdateEvent.RATING))
		{
			BigDecimal rating= fr.getRatingForInstructor(ip.getId());
			float avg=rating.setScale(1, RoundingMode.HALF_UP).floatValue();
			ip.setAvgRating(avg);
		}
		else if(sue.equals(StatUpdateEvent.COMPLETION))
		{
			int totstd= er.countByCourse_Instructor(ip);
			int completed= ccr.countByCourse_Instructor(ip);
			
			ip.setCompletionRate(((float)completed/(float)totstd) * 100);
		}
		
	}
}
