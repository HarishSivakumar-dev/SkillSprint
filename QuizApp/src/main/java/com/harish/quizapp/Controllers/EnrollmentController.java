package com.harish.quizapp.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.EnrollmentData;
import com.harish.quizapp.Service.EnrollmentService;

@RestController
@RequestMapping("/app")
public class EnrollmentController 
{
	
	@Autowired 
	private EnrollmentService es;

	@PostMapping("/enroll")
	@PreAuthorize("hasAnyRole('USER','INSTRUCTOR')")
	public ResponseEntity<String> enrollmentCourse(@RequestBody CourseDetails cd)
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		
		return es.enrollUserintoCourse(name,cd);
	}
	@GetMapping("/enrolled/courses")
	@PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR','USER')")
	public ResponseEntity<List<EnrollmentData>> getEnrolledCourses()
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		
		return es.getAllEnrolledCourses(name);
	}
	
	@PatchMapping("/enrolled/courses/update/status")
	@PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
	public ResponseEntity<String> updateCourseStatus(@RequestBody CourseDetails cd)
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		
		return es.setStatusCompleted(name,cd);
	}
	
	@DeleteMapping("/courses/user/unenroll/{courseid}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> unEnrollUserFromCourse(@PathVariable int courseid)
	{
		String name= SecurityContextHolder.getContext().getAuthentication().getName();
		
		return es.deleteEnrollmentOfUser(name,courseid);
	}
	
	
}
