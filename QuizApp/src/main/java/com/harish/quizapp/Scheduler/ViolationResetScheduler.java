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
import com.harish.quizapp.DataRepos.CourseCompletionRepo;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.FeedbackRepo;
import com.harish.quizapp.DataRepos.InstStatRepo;
import com.harish.quizapp.DataRepos.InstructorRepo;
import com.harish.quizapp.DataRepos.InstructorStatUpdateProjection;
import com.harish.quizapp.DataRepos.UserDeltaProjection;
import com.harish.quizapp.DataRepos.UserDeltaRepo;
import com.harish.quizapp.DataRepos.UserProfileRepo;
import com.harish.quizapp.DataRepos.ViolationTableRepo;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.FeedbackTable;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.InstructorStatUpdate;
import com.harish.quizapp.Model.UserProfile;
import com.harish.quizapp.Model.ViolationsTable;
import com.harish.quizapp.enums.SkillLevelEnum;
import com.harish.quizapp.enums.StatUpdateEvent;
import com.harish.quizapp.enums.UserDeltaAction;
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
	private UserProfileRepo upr;
	@Autowired
	private InstStatRepo isr;
	@Autowired
	private UserDeltaRepo udr;
 
	
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
			
			List<UserDeltaProjection> usr= udr.findAllPendingDeltas();
			
			Map<Integer,List<UserDeltaProjection>> mp= new HashMap<>();
			
			if(usr.isEmpty())
			{
				return;
			}
			
			for(UserDeltaProjection udp : usr)
			{
				
				if(mp.containsKey(udp.getUserId()))
				{
					mp.get(udp.getUserId()).add(udp);
				}
				else
				{
					List<UserDeltaProjection> pr= new ArrayList<>();
					pr.add(udp);
					mp.put(udp.getUserId(),pr);
				}
			}
			
			Set<Integer> userids= mp.keySet();
			List<UserProfile> changedusers= upr.findAllById(userids);
			List<UserProfile> dbsave= new ArrayList<>();
			
			for(UserProfile pro : changedusers)
			{
				List<UserDeltaProjection> evn= mp.get(pro.getId());
				
				for(UserDeltaProjection prj : evn)
				{
					this.applyUserDeltaLogic(prj.getTotDelta(),prj.getUserAction(),pro);
				}
				
				this.recalculateDerivedDeltas(pro);
				this.allocateLevel(pro);
				dbsave.add(pro);
				
			}
			
			upr.saveAll(dbsave);
				
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
			int totstd= ip.getTotalCleared();
			int completed= ccr.countByCourse_Instructor(ip);
			
			ip.setCompletionRate(((float)completed/(float)totstd) * 100);
		}
		
	}
	
	private void applyUserDeltaLogic(int deltaval, UserDeltaAction act, UserProfile pr)
	{
		if(act.equals(UserDeltaAction.Enrolled))
		{
			pr.setTotCoursesEnrolled(pr.getTotCoursesEnrolled()+deltaval);
		}
		else if(act.equals(UserDeltaAction.QuizAttended))
		{
			pr.setQuizzesAttended(pr.getQuizzesAttended()+deltaval);
		}
		else if(act.equals(UserDeltaAction.QuizCleared))
		{
			pr.setQuizzesCleared(pr.getQuizzesCleared()+deltaval);
		}
		else if(act.equals(UserDeltaAction.Completed))
		{
			pr.setCoursesCompleted(pr.getCoursesCompleted()+deltaval);
		}
		else if(act.equals(UserDeltaAction.Certificates))
		{
			pr.setNoOfCertificates(pr.getNoOfCertificates()+deltaval);
		}
	}
	
	private void recalculateDerivedDeltas(UserProfile up)
	{
		int totcert=up.getNoOfCertificates();
		int coursescomplete=up.getCoursesCompleted();
		int enrolled=up.getTotCoursesEnrolled();
		int quizcleared= up.getQuizzesCleared();
		int quizattended=up.getQuizzesAttended();
		
		up.setAvgCertificationRate((coursescomplete>0) ? (totcert/ (float)coursescomplete)*100 : 0);
		up.setAvgClearingRate((enrolled >0) ? (coursescomplete/ (float)enrolled)*100 : 0);
		up.setAvgQuizezCleared((quizattended >0 ) ? (quizcleared/ (float)quizattended)*100 : 0);
		
	}
	
	private void allocateLevel(UserProfile up)
	{
		up.setLevel(this.allocateLevel(up.getAvgQuizezCleared(),up.getAvgClearingRate(),up.getAvgCertificationRate()));
	}
}
