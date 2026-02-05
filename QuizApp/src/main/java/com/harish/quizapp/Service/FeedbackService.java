package com.harish.quizapp.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.enums.StatUpdateEvent;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.FeedbackRepo;
import com.harish.quizapp.DataRepos.InstStatRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Dto.FeedbackDto;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.FeedbackTable;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.InstructorStatUpdate;

@Service 
public class FeedbackService
{
	@Autowired
	private UserRepo rep;
	@Autowired
	private CoursesRepo cr;
	@Autowired
	private FeedbackRepo fr;
	@Autowired
	private InstStatRepo isr;
	
	public ResponseEntity<String> submitFeedback(FeedbackDto ft, int courseid)
	{
		String ur= SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration reg= rep.findByUserName(ur).orElseThrow();
		CourseDetails cd= cr.findById(courseid).orElseThrow();
		InstructorProfile ins=cd.getInstructor();
		
		FeedbackTable tb= new FeedbackTable();
		
		tb.setCourseId(courseid);
		tb.setUser(reg);
		tb.setComments(ft.getComments());
		tb.setRating(ft.getRating());
		tb.setInstructor(ins);
		
		fr.save(tb);
		
		InstructorStatUpdate isu= new InstructorStatUpdate();
		isu.setProceeded(false);
		isu.setCreatedAt(LocalDateTime.now());
		isu.setDeltaValue(+1);
		isu.setEventType(StatUpdateEvent.FEEDBACK);
		isu.setInstId(cd.getInstructor().getId());
		
		
		InstructorStatUpdate isu1= new InstructorStatUpdate();
		isu1.setProceeded(false);
		isu1.setDeltaValue(+0);
		isu1.setEventType(StatUpdateEvent.RATING);
		isu1.setInstId(cd.getInstructor().getId());
		isu1.setCreatedAt(LocalDateTime.now());
		
		
		List<InstructorStatUpdate> upd= new ArrayList<>();
		upd.add(isu1);
		upd.add(isu);
		
		isr.saveAll(upd);
		
		return ResponseEntity.status(HttpStatus.OK).body("Feedback Submitted !");
	}

}
