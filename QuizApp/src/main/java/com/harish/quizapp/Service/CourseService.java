package com.harish.quizapp.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.CourseContentsRepo;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.EnrollmentRepo;
import com.harish.quizapp.DataRepos.InstructorRepo;
import com.harish.quizapp.DataRepos.MaterialsRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Dto.CourseContentsDto;
import com.harish.quizapp.Dto.CourseDetailsDto;
import com.harish.quizapp.Dto.EnrollmentDataDto;
import com.harish.quizapp.Dto.StatusUpdateDto;
import com.harish.quizapp.Dto.StudentsDTO;
import com.harish.quizapp.Dto.StudyMaterialDto;
import com.harish.quizapp.Model.CourseContents;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.EnrollmentData;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.MaterialsDto;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.enums.CourseStatus;
import jakarta.transaction.Transactional;

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
	
	@Autowired
	private InstructorRepo ipr;

	@Transactional
	public ResponseEntity<String> createCourses(CourseDetails cd, String name)
	{
		InstructorProfile instructor=ipr.findByUserName_UserName(name).orElseThrow();
		if(cr.findByTitleAndInstructor(cd.getTitle(),instructor).isPresent())
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course Already Created");
		}
		
		cd.setInstructor(instructor);
		cd.setCreatedAt(LocalDateTime.now());
		cd.setStatus(CourseStatus.Active);
		
		cr.save(cd);
		
		instructor.setTotCourses(cr.countByInstructor(instructor));
		ipr.save(instructor);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Requested Course creation successfull");
	}
	
	public ResponseEntity<List<CourseDetailsDto>> displayRelevantCourses(String name)
	{
		InstructorProfile inst=ipr.findByUserName_UserName(name).orElseThrow(()-> new BadCredentialsException("No Instructor Found"));
		List<CourseDetails> res= cr.findByInstructor(inst);
		
		List<CourseDetailsDto> dto= new ArrayList<>();
		
		for(CourseDetails det : res)
		{
			CourseDetailsDto od= new CourseDetailsDto();
		
			od.setCatagory(det.getCatagory());
			od.setCourseId(det.getId());
			od.setCreatedAt(det.getCreatedAt());
			od.setDescription(det.getDescription());
			od.setDuration(det.getDuration());
			od.setFullName(det.getInstructor().getFullName());
			od.setInstructorId(det.getInstructor().getId());
			od.setLevel(det.getLevel());
			od.setRating(det.getRating());
			od.setStatus(det.getStatus());
			od.setTitle(det.getTitle());
			od.setUserName(det.getInstructor().getUserName().getUserName());
			
			dto.add(od);	
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	@Transactional
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
	
	public ResponseEntity<List<CourseDetailsDto>> getAllCourses()
	{
		List<CourseDetails> cd=cr.findByStatus(CourseStatus.Active);
		List<CourseDetailsDto> dto= new ArrayList<>();
		
		for(CourseDetails dt : cd)
		{
			CourseDetailsDto cdd= new CourseDetailsDto();
			cdd.setCatagory(dt.getCatagory());
			cdd.setCourseId(dt.getId());
			cdd.setCreatedAt(dt.getCreatedAt());
			cdd.setDescription(dt.getDescription());
			cdd.setDuration(dt.getDuration());
			cdd.setFullName(dt.getInstructor().getFullName());
			cdd.setInstructorId(dt.getInstructor().getId());
			cdd.setLevel(dt.getLevel());
			cdd.setRating(dt.getRating());
			cdd.setStatus(dt.getStatus());
			cdd.setTitle(dt.getTitle());
			cdd.setUserName(dt.getInstructor().getUserName().getUserName());
			
			dto.add(cdd);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	public ResponseEntity<String> updateCourseStatus(StatusUpdateDto sud)
	{
		CourseDetails cd=cr.findById(sud.getId()).orElseThrow();
		cd.setStatus(sud.getStatus());
		
		cr.save(cd);
		
		return ResponseEntity.status(HttpStatus.OK).body("Updated");
		
	}

	public ResponseEntity<String> putCourseContent(String name)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Course Content Added !");
	}

	public ResponseEntity<List<EnrollmentDataDto>> getCourseUsers(int courseid)
	{
		List<EnrollmentData> enroll= er.findByCourse_Id(courseid);
		List<EnrollmentDataDto> dto= new ArrayList<>();
		
		for(EnrollmentData dt : enroll)
		{
			EnrollmentDataDto otd= new EnrollmentDataDto();
			otd.setCourseId(dt.getCourse().getId());
			otd.setCourseName(dt.getCourse().getTitle());
			otd.setEnrolledAt(dt.getEnrollment_date());
			otd.setStatus(dt.getStatus());
			otd.setUserId(dt.getUser().getId());
			otd.setUserName(dt.getUser().getUserName());
			
			dto.add(otd);
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
	}
	
	public ResponseEntity<List<StudentsDTO>> getStudentsforInstructor(int courseid)
	{
		CourseDetails cd= cr.findById(courseid).orElseThrow();
		
		if(cd.getInstructor().getUserName().getUserName().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
		{
			List<EnrollmentData> ed=er.findByCourse_Id(courseid);
			List<StudentsDTO> stu=new ArrayList<StudentsDTO>();
			
			for(EnrollmentData data : ed)
			{
				StudentsDTO st=new StudentsDTO();
				st.setCourseid(data.getCourse().getId());
				st.setUserName(data.getUser().getUserName());
				st.setEmail(data.getUser().getEmail());
				st.setUserId(data.getUser().getId());
				
				stu.add(st);
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(stu);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(List.of(new StudentsDTO()));
		}
		
	}

	@Transactional
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

	public ResponseEntity<List<CourseContentsDto>> getCourseContents(int courseid)
	{
		List<CourseContents> contents = ccr.findByCourse_Id(courseid);
		List<CourseContentsDto> dto= new ArrayList<>();
		
		for(CourseContents cc : contents)
		{
			CourseContentsDto dt= new CourseContentsDto();
			dt.setCourseId(cc.getCourseid().getId());
			dt.setDescription(cc.getDescription());
			dt.setTopic(cc.getTopic());
			dt.setTopicId(cc.getTopicid());
			
			dto.add(dt);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(dto);
	}

	public ResponseEntity<List<StudyMaterialDto>> getMaterialsforTopic(int courseid, int topicid)
	{
		List<MaterialsDto> materialsDto=mr.findByContent_TopicidAndContent_Course_Id(topicid,courseid);
		List<StudyMaterialDto> smd= new ArrayList<>();
		
		for(MaterialsDto dt : materialsDto)
		{
			StudyMaterialDto dto= new StudyMaterialDto();
			dto.setTopicId(dt.getContent().getTopicid());
			dto.setTypeOfMaterial(dt.getType());
			dto.setUrl(dt.getUrl());
			
			smd.add(dto);
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(smd);
	}
	
	@Transactional
	public ResponseEntity<String> addMoreMaterials(int topicid, int courseid, CourseContents cc)
	{
		CourseContents contents=ccr.findByCourse_IdAndTopicid(courseid, topicid).orElseThrow();
		
		List<MaterialsDto> mt=cc.getMaterialsdto();
		for(MaterialsDto md : mt)
		{
			md.setContent(contents);
		}
		
		mr.saveAll(mt);
		return ResponseEntity.status(HttpStatus.CREATED).body("Added");
	}
	
	public ResponseEntity<List<String>> fetchAllCoursesAvailabe()
	{
		return ResponseEntity.status(HttpStatus.OK).body(cr.findAllCourseGenreAvailable());
	}
}
