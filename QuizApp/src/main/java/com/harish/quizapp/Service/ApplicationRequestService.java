package com.harish.quizapp.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.InstAppRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Model.InstructorApplication;
import com.harish.quizapp.Model.UserRegistration;

@Service
public class ApplicationRequestService
{
	@Autowired
	private UserRepo ur;
	@Autowired
	private InstAppRepo iar;
	
	public ResponseEntity<String> applyForInstructor(InstructorApplication application) 
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		
		UserRegistration user= ur.findByUserName(name).orElseThrow(()-> new BadCredentialsException("No User Found !"));
		
		Optional<InstructorApplication> ai= iar.findByUser(user);
		
		if(ai.isEmpty())
		{
			InstructorApplication ia= new InstructorApplication();
			ia.setUser(user);
			ia.setFullName(user.getName());
			ia.setQualification(application.getQualification());
			ia.setLinkedin(application.getLinkedin());
			ia.setReason(application.getReason());
			ia.setResumeUrl(application.getResumeUrl());
			ia.setIsPending(true);
			ia.setIsRejected(false);
			
			iar.save(ia);
			
			return ResponseEntity.status(HttpStatus.CREATED).body("Application Submitted");
		}
		else
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Already Exists");
		}
		
	}

	public ResponseEntity<String> checkPromotionStatus() 
	{
		String name= SecurityContextHolder.getContext().getAuthentication().getName();
		
		UserRegistration user= ur.findByUserName(name).orElseThrow(()-> new BadCredentialsException("No User Found !"));
		
		Optional<InstructorApplication> ai= iar.findByUser(user);
		
		if(ai.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Record Created");
		}
		
		InstructorApplication ap=ai.get();
		
		if(ap.getIsPending())
		{
			return ResponseEntity.status(HttpStatus.OK).body("Application Pending");
		}
		else if(ap.getIsRejected())
		{
			return ResponseEntity.status(HttpStatus.OK).body("Application Rejected");
		}
		else
		{
			return ResponseEntity.status(HttpStatus.OK).body("Application Accepted");
		}
		
	}

}
