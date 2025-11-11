package com.harish.quizapp.Service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.ComplaintsRepo;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Dto.ComplaintsDto;
import com.harish.quizapp.Model.ComplaintsTable;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.enums.ComplaintStatus;

@Service
public class ComplaintService
{
	@Autowired 
	private UserRepo ur; 
	@Autowired 
	private CoursesRepo cr;
	@Autowired
	private ComplaintsRepo comrep;

	public ResponseEntity<String> addUserReport(ComplaintsDto ct)
	{
		String user=SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration us= ur.findByUserName(user).orElseThrow();
		CourseDetails cd=cr.findById(ct.getId()).orElseThrow();
	
		ComplaintsTable com=new ComplaintsTable();
		com.setComments(ct.getComments());
		com.setInstructor(cd.getInstructor());
		com.setUser(us);
		com.setReason(ct.getReason());
		com.setCreatedAt(LocalDate.now());
		com.setStatus(ComplaintStatus.Pending);
		
		comrep.save(com);
		
		return ResponseEntity.status(HttpStatus.OK).body("Submitted");
	}
	
	public ResponseEntity<List<ComplaintsTable>> getAllUserSubmitted()
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration us= ur.findByUserName(name).orElseThrow();
		
		return ResponseEntity.status(HttpStatus.FOUND).body(comrep.findByUser(us));
	}
}
