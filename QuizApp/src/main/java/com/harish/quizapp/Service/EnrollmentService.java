package com.harish.quizapp.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.AttemptsRepo;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.EnrollmentRepo;
import com.harish.quizapp.DataRepos.InstStatRepo;
import com.harish.quizapp.DataRepos.QuizRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Dto.CourseDetailsDto;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.EnrollmentData;
import com.harish.quizapp.Model.InstructorStatUpdate;
import com.harish.quizapp.Model.Quiz;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.Model.attemptsTable;
import com.harish.quizapp.enums.StatUpdateEvent;
import jakarta.transaction.Transactional;

@Service
public class EnrollmentService
{
	
	@Autowired
	private UserRepo ur;

	@Autowired 
	private EnrollmentRepo er;
	
	@Autowired
	private QuizRepo qr;
	
	@Autowired
	private AttemptsRepo ar;
	
	@Autowired
	private InstStatRepo isr; 
	
	@Autowired
	private CoursesRepo cr;
	
	
	public ResponseEntity<String> enrollUserintoCourse(String name, CourseDetailsDto cdp)
	{
		UserRegistration user=ur.findByUserName(name).orElseThrow(()->new BadCredentialsException("No users found !"));
		CourseDetails cd= cr.findById(cdp.getCourseId()).orElseThrow(()-> new BadCredentialsException("No Course Found"));
		
		if(er.findByUserAndCourse(user,cd).isPresent())
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Registered !");
		}
		else
		{
			EnrollmentData ed=new EnrollmentData();
			ed.setCourse(cd);
			ed.setUser(user);
			ed.setEnrollment_date(LocalDateTime.now());
			ed.setStatus("ACTIVE");
		
			er.save(ed);
			
			InstructorStatUpdate isu= new InstructorStatUpdate();
			isu.setProceeded(false);
			isu.setCreatedAt(LocalDateTime.now());
			isu.setDeltaValue(+1);
			isu.setEventType(StatUpdateEvent.ENROLLMENT);
			isu.setInstId(cd.getInstructor().getId());
			
			isr.save(isu);
			
			List<Quiz> quiz= qr.findByCourse(cd);
			List<attemptsTable> preattempts=new ArrayList<attemptsTable>();
			for(Quiz qz : quiz)
			{
				attemptsTable at=new attemptsTable();
				at.setAttemptcount(0);
				at.setQuiz(qz);
				at.setStatus("NOT_STARTED");
				at.setUser(user);
				at.setCourse(cd);
				preattempts.add(at);
			}
		
			ar.saveAll(preattempts);
			
			
			return ResponseEntity.status(HttpStatus.CREATED).body("Course Registered SuccessFully !");
		}
	}

	public ResponseEntity<List<EnrollmentData>> getAllEnrolledCourses(String name)
	{
		UserRegistration usr= ur.findByUserName(name).orElseThrow(()-> new BadCredentialsException("No User Found"));
		List<EnrollmentData> ed=er.findByUser(usr);
		
		return ResponseEntity.status(HttpStatus.OK).body(ed);
	}
	public ResponseEntity<String> setStatusCompleted(String name, CourseDetails cs)
	{
		UserRegistration usr=ur.findByUserName(name).orElseThrow();
		
		EnrollmentData ed=er.findByUserAndCourse(usr,cs).orElseThrow(()->new BadCredentialsException("User Not Enrolled "));
		ed.setStatus("COMPLETED");
		
		er.save(ed);
	
		return ResponseEntity.status(HttpStatus.OK).body("Couse Completed !");
	}

	@Transactional
	public ResponseEntity<String> deleteEnrollmentOfUser(String name, int courseid)
	{
		UserRegistration usr=ur.findByUserName(name).orElseThrow();
		
		UserRegistration cd= er.findByUser_IdAndCourse_Id(usr.getId(), courseid).orElseThrow().getUser();
		int rows=er.deleteByUser_IdAndCourse_Id(usr.getId(),courseid);
		if(rows>0)
		{
			
			InstructorStatUpdate isu= new InstructorStatUpdate();
			isu.setProceeded(false);
			isu.setCreatedAt(LocalDateTime.now());
			isu.setDeltaValue(-1);
			isu.setEventType(StatUpdateEvent.UNENROLLMENT);
			isu.setInstId(cd.getId());
			
			isr.save(isu);
		
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("DELETED");
		}
		else
		{
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Record Not Found ");
		}
	}
	

}
