package com.harish.quizapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.quizapp.Dto.ApplicationDto;
import com.harish.quizapp.Model.AdminApplication;
import com.harish.quizapp.Model.InstructorApplication;
import com.harish.quizapp.Service.ApplicationRequestService;
import com.harish.quizapp.Service.InstructorService;

@RestController
@RequestMapping("/app")
public class InstructorRequestController
{
	
	@Autowired
	private ApplicationRequestService ars;
	
	@Autowired
	private InstructorService is;
	
	@PostMapping("/user/role/upgrade/toInstructor")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> applyForPromotiontoInstructor(@RequestBody InstructorApplication application)
	{
		return ars.applyForInstructor(application);
	}
	@GetMapping("/user/role/upgrade/view/status")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> checkPromotionStatus()
	{
		return ars.checkPromotionStatus();
	}
	@PostMapping("/user/role/upgrade/apply/admin")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<String> submitPromotonRequestForAdmin(@RequestBody ApplicationDto ad)
	{
		return is.applyForAdmin(ad);
	}
	@GetMapping("/user/role/upgrade/application/status")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<AdminApplication> verifyPromotionRequestStatus()
	{
		return is.checkStatus();
	}
 
}
