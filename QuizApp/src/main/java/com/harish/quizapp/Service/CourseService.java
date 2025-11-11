package com.harish.quizapp.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import com.harish.quizapp.Model.StudentsDTO;
import com.harish.quizapp.DataRepos.CourseContentsRepo;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.EnrollmentRepo;
import com.harish.quizapp.DataRepos.MaterialsRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Model.CourseContents;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.EnrollmentData;
import com.harish.quizapp.Model.MaterialsDto;
import com.harish.quizapp.Model.UserRegistration;

@Service
public class CourseService 
{
	@Autowired
	private UserRepo ur;
	
	@Autowired
	private CoursesRepo cr;
	
	@Autowired
	private CourseContentsRepo ccr;
	
	@Autowired
	private EnrollmentRepo er;
	
	@Autowired
	private MaterialsRepo mr;

	public ResponseEntity<String> createCourses(CourseDetails cd, String name)
	{
		UserRegistration instructor=ur.findByUserName(name).orElseThrow(()->new BadCredentialsException("No Instructor Found"));
		if(cr.findByTitleAndInstructor(cd.getTitle(),instructor).isPresent())
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course Already Created");
		}
		
		cd.setInstructor(instructor);
		cd.setCreated_at(LocalDateTime.now());
		cd.setStatus("ACTIVE");
		
		cr.save(cd);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Requested Course creation successfull");
	}
	
	public ResponseEntity<List<CourseDetails>> displayRelevantCourses(String name)
	{
		UserRegistration inst=ur.findByUserName(name).orElseThrow(()-> new BadCredentialsException("No Instructor Found"));
		
		return ResponseEntity.status(HttpStatus.OK).body(cr.findByInstructor(inst));
	}
	
	public ResponseEntity<String> deleteCourses(int id,String name)
	{
		UserRegistration inst=ur.findByUserName(name).orElseThrow(()-> new BadCredentialsException("No Instructor Found"));
		int ids=inst.getId();
		
		CourseDetails cd=cr.findById(id).orElseThrow();
		
		if(cd.getInstructor().getId()==ids)
		{
			cr.delete(cd);
			return ResponseEntity.status(HttpStatus.OK).body("DELETED A COURSE");
		}
		else
		{
			return ResponseEntity.status(HttpStatus.OK).body("Cannot Delete This Course !");
		}
	}
	
	public ResponseEntity<List<CourseDetails>> getAllCourses()
	{
		return ResponseEntity.status(HttpStatus.OK).body(cr.findByStatus("ACTIVE"));
	}

	public ResponseEntity<String> updateCourseStatus(int id, String name)
	{
		CourseDetails cd=cr.findById(id).orElseThrow();
		cd.setStatus("INACTIVE");
		
		cr.save(cd);
		
		return ResponseEntity.status(HttpStatus.OK).body("Updated");
		
	}

	public ResponseEntity<String> putCourseContent(String name)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Course Content Added !");
	}

	public ResponseEntity<List<EnrollmentData>> getCourseUsers(int courseid)
	{
		List<EnrollmentData> enroll= er.findByCourse_Id(courseid);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(enroll);
	}
	
	public ResponseEntity<List<StudentsDTO>> getStudentsforInstructor(int courseid)
	{
		List<EnrollmentData> ed=er.findByCourse_Id(courseid);
		List<StudentsDTO> stu=new ArrayList<StudentsDTO>();
		
		for(EnrollmentData data : ed)
		{
			StudentsDTO st=new StudentsDTO();
			st.setCourseid(data.getCourse().getId());
			st.setName(data.getUser().getName());
			st.setEmail(data.getUser().getEmail());
			
			stu.add(st);
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(stu);
	}

	public ResponseEntity<String> addCourseContents(CourseContents cc, int courseid)
	{
		CourseDetails cd=cr.findById(courseid).orElseThrow();
		cc.setCourseid(cd);
		int topicno=ccr.findByMaxCourseId(courseid);
		cc.setTopicid(topicno+1);
		
		CourseContents ca = ccr.save(cc);
		
		
		List<MaterialsDto> materials = cc.getMaterialsdto();
		
		for(MaterialsDto mat : materials)
		{
			mat.setContent(ca);
		}
		
		mr.saveAll(materials);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Materials and Topics added !");
	}

	public ResponseEntity<List<CourseContents>> getCourseContents(int courseid)
	{
		List<CourseContents> contents = ccr.findByCourse_Id(courseid);
		return ResponseEntity.status(HttpStatus.FOUND).body(contents);
	}

	public ResponseEntity<List<MaterialsDto>> getMaterialsforTopic(int courseid, int topicid)
	{
		List<MaterialsDto> materialsDto=mr.findByContent_Id(topicid);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(materialsDto);
	}
	
	public ResponseEntity<String> addMoreMaterials(int topicid,CourseContents cc)
	{
		CourseContents contents=ccr.findById(topicid).orElseThrow();
		
		List<MaterialsDto> mt=cc.getMaterialsdto();
		for(MaterialsDto md : mt)
		{
			md.setContent(contents);
		}
		
		mr.saveAll(mt);
		return ResponseEntity.status(HttpStatus.CREATED).body("Added");
	}
}
