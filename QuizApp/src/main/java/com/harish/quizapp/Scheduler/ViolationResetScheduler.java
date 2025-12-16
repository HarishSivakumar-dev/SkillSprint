package com.harish.quizapp.Scheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.FeedbackRepo;
import com.harish.quizapp.DataRepos.ViolationTableRepo;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.FeedbackTable;
import com.harish.quizapp.Model.ViolationsTable;

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
	
	@Scheduled(cron="0 0 0 * * * ")
	public void resetViolationField()
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
	
	@Transactional
	@Scheduled(cron="0 0 0 * * * ")
	public void calibrateRatingForAllCourses()
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
			
			float rating = (cou.stream()
							  .mapToInt(r->r.getRating())
							  .sum())/cou.size();
			fd.setRating(rating);
			
			fin.add(fd);
		}
		
		rep.saveAll(fin);
		
	}
}
