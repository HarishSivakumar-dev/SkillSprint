package com.harish.quizapp.Service;

import java.time.LocalDate;

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
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.FeedbackTable;
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
	
	public ResponseEntity<String> submitFeedback(FeedbackTable ft, int courseid)
	{
		String ur= SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration reg= rep.findByUserName(ur).orElseThrow();
		CourseDetails cd= cr.findById(courseid).orElseThrow();
		UserRegistration ins=cd.getInstructor();
		
		ft.setCourseId(courseid);
		ft.setUser(reg);
		ft.setInstructor(ins);
		fr.save(ft);
		
		InstructorStatUpdate isu= new InstructorStatUpdate();
		isu.setProceeded(false);
		isu.setCreatedAt(LocalDate.now());
		isu.setDeltaValue(+1);
		isu.setEventType(StatUpdateEvent.FEEDBACK);
		isu.setInstId(cd.getInstructor().getId());
		
		isr.save(isu);
		
		return ResponseEntity.status(HttpStatus.OK).body("Feedback Submitted !");
	}

}
