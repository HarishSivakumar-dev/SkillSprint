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
import com.harish.quizapp.Dto.InstructorDto;
import com.harish.quizapp.Dto.SkillResponseDto;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Service.InstructorProfileService;

@RestController
@RequestMapping("/app/instructor")
public class InstructorProfileController 
{
	@Autowired
	private InstructorProfileService ip;
	
	@GetMapping("/Profile")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<InstructorProfile> getinstructorProfile()
	{
		return ip.getInstructorProfile();
	}
	
	@PostMapping("/edit/Profile")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<String> setInstructorProfileDetails(@RequestBody InstructorDto idto)
	{
		return ip.setInstructorProfile(idto);
	}
	
	@GetMapping("/profile/skills/approval/status")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<List<SkillResponseDto>> searchInstructorSkillStatusAdded()
	{
		return ip.searchInstructorSkills();
	}
	
}

