package com.harish.quizapp.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.quizapp.Dto.ComplaintInstructorDto;
import com.harish.quizapp.Dto.ComplaintsDto;
import com.harish.quizapp.Dto.UserComplaintsDto;
import com.harish.quizapp.Dto.ViolationResultDto;
import com.harish.quizapp.Service.ComplaintService;

@RestController 
@RequestMapping("/app/complaint")
public class ComplaintController 
{
	
	@Autowired
	private ComplaintService cs; 
	
	
	@PostMapping("/raisecomplaint")
	@PreAuthorize("hasAnyRole('USER','INSTRUCTOR')")
	public ResponseEntity<String> submitUserProblem(@RequestBody ComplaintsDto ct)
	{
		return cs.addUserReport(ct);
	}
	
	@GetMapping("/getallcomplaints")
	@PreAuthorize("hasAnyRole('USER','INSTRUCTOR')")
	public ResponseEntity<List<UserComplaintsDto>> getAllUserProblem()
	{
		return cs.getAllUserSubmitted();
	}
	
	@GetMapping("/get/me")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<List<ComplaintInstructorDto>> getComplaintsAgainstMe()
	{
		return cs.getComplaintsInstructor();
	}
	
	@GetMapping("/violations")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<ViolationResultDto> getMyViolationStatus()
	{
		return cs.getViolationOfMe();
	}

}
