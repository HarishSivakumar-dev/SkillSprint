package com.harish.quizapp.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harish.quizapp.Dto.ApplicationDto;
import com.harish.quizapp.Model.CourseContents;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.EnrollmentData;
import com.harish.quizapp.Model.MaterialsDto;
import com.harish.quizapp.Model.StudentsDTO;
import com.harish.quizapp.Service.CourseService;
import com.harish.quizapp.Service.InstructorService;

@RestController
@RequestMapping("/app")
public class CourseController
{
	
	@Autowired 
	private CourseService cs;
	@Autowired 
	private InstructorService is;
	
	@PostMapping("/course/create")
	@PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
	public ResponseEntity<String> createCourse(@RequestBody CourseDetails cd)
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		return cs.createCourses(cd,name);
	}
	
	@GetMapping("/user/courses/get")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<List<CourseDetails>> getRelatedCourses()
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		return cs.displayRelevantCourses(name);
	}
	
	@DeleteMapping("/user/courses/delete/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
	public ResponseEntity<String> deleteRelatedCourses(@PathVariable int id)
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		return cs.deleteCourses(id, name);
	}
	
	@PostMapping("/user/courses/update/course/status/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
	public ResponseEntity<String> updateCourseStatus(@PathVariable int id)
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		return cs.updateCourseStatus(id,name);
	}
	
	@GetMapping("/user/courses/getall")
	@PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR','USER')")
	public ResponseEntity<List<CourseDetails>> getAllCourses()
	{
		return cs.getAllCourses();
	}
	
	@GetMapping("/course/admin/getenrolled/{courseid}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<EnrollmentData>> getCourseUsers(@PathVariable int courseid)
	{
		return cs.getCourseUsers(courseid);
	}
	
	@GetMapping("/course/instructor/getenrolled/{courseid}")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<List<StudentsDTO>> getStudentsDetails(@PathVariable int courseid)
	{
		return cs.getStudentsforInstructor(courseid);
	}
	
	@PostMapping("/course/instructor/add/{courseid}")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<String> addCourseContents(@RequestBody CourseContents cc, @PathVariable int courseid)
	{
		return cs.addCourseContents(cc,courseid);
	}
	
	@GetMapping("/course/contents/get/{courseid}")
	@PreAuthorize("hasRole('USER','INSTRUCTOR','ADMIN')")
	public ResponseEntity<List<CourseContents>> getCourseContents(@PathVariable int courseid)
	{
		return cs.getCourseContents(courseid);
	}

	@GetMapping("/course/content/get/{courseid}/{topicid}")
	@PreAuthorize("hasRole('USER','INSTRUCTOR','ADMIN')")
	public ResponseEntity<List<MaterialsDto>> getMaterialsforCourse(@PathVariable int courseid, @PathVariable int topicid)
	{
		return cs.getMaterialsforTopic(courseid,topicid);
	}
	
	@PutMapping("/course/content/{topicid}/materials")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<String> addMoreMaterialsforCourseTopic(@PathVariable int topicid, @RequestBody CourseContents md)
	{
		return cs.addMoreMaterials(topicid, md);
	}
	
	@PostMapping("/user/apply/system/admin")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<String> applyForAdmin(@RequestBody ApplicationDto app)
	{
		return is.applyForAdmin(app);
	}
	
}
