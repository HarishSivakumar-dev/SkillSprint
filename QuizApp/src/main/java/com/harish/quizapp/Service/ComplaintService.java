package com.harish.quizapp.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.ComplaintsRepo;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.InstructorRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.DataRepos.ViolationTableRepo;
import com.harish.quizapp.Dto.ComplaintInstructorDto;
import com.harish.quizapp.Dto.ComplaintsDto;
import com.harish.quizapp.Dto.ViolationResultDto;
import com.harish.quizapp.Model.ComplaintsTable;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.Model.ViolationsTable;
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
	@Autowired
	private InstructorRepo ir;
	@Autowired
	private ViolationTableRepo rp;

	public ResponseEntity<String> addUserReport(ComplaintsDto ct)
	{
		String user=SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration us= ur.findByUserName(user).orElseThrow();
		CourseDetails cd=cr.findById(ct.getId()).orElseThrow();
		Optional<ViolationsTable> vt=rp.findByInstructor_Id(cd.getInstructor().getId());
		Optional<ComplaintsTable> cmp= comrep.findByUser_IdAndInstructor_IdAndCourse_IdAndStatus(us.getId(),cd.getInstructor().getId(),cd.getId(),ComplaintStatus.Pending);
		
		if((vt.isPresent() && vt.get().isViolated()) || (cmp.isPresent()))
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body("INSTRUCTOR ALREADY UNDER VIOLATION ! / A COMPLAINT IS STILL PENDING !");
		}
	
		ComplaintsTable com=new ComplaintsTable();
		com.setComments(ct.getComments());
		com.setInstructor(cd.getInstructor());
		com.setUser(us);
		com.setReason(ct.getReason());
		com.setCreatedAt(LocalDate.now());
		com.setStatus(ComplaintStatus.Pending);
		com.setCourse(cd);
		
		comrep.save(com);
		
		return ResponseEntity.status(HttpStatus.OK).body("Submitted");
	}
	
	public ResponseEntity<List<ComplaintsTable>> getAllUserSubmitted()
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration us= ur.findByUserName(name).orElseThrow();
		
		return ResponseEntity.status(HttpStatus.FOUND).body(comrep.findByUser(us));
	}
	
	public ResponseEntity<List<ComplaintInstructorDto>> getComplaintsInstructor()
	{
		String name= SecurityContextHolder.getContext().getAuthentication().getName();
		InstructorProfile ip= ir.findByUserName_UserName(name).orElseThrow(()-> new NoSuchElementException());
		
		List<ComplaintsTable> tb = comrep.findByInstructor_IdAndStatus(ip.getId(), ComplaintStatus.Pending);
		List<ComplaintInstructorDto> dto= new ArrayList<>();
		
		if(tb.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		}
		
		dto= tb.stream()
			   .map(r->{
						ComplaintInstructorDto bl= new ComplaintInstructorDto();
						bl.setComments(r.getComments());
					    bl.setComplaintId(r.getId());
						bl.setCreatedAt(r.getCreatedAt());
						bl.setInstructorId(r.getInstructor().getId());
						bl.setInstructorUsername(r.getInstructor().getUserName().getUserName());
					    bl.setReason(r.getReason());
						bl.setStatus(r.getStatus());
												
						return bl;	
						})
				.collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.FOUND).body(dto);
		
		
	}
	
	public ResponseEntity<ViolationResultDto> getViolationOfMe()
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		int id= ir.findByUserName_UserName(name).get().getId();
		
		Optional<ViolationsTable> vt= rp.findByInstructor_Id(id);
		ViolationResultDto dt = new ViolationResultDto();
		
		if(vt.isEmpty())
		{
			dt.setFinalViolationCount(0);
			dt.setInitialViolationCount(0);
			dt.setIsViolated(false);
			
			return ResponseEntity.status(HttpStatus.OK).body(dt);
		}
		else 
		{
			dt.setFinalViolationCount(vt.get().getFinalViolationCount());
			dt.setInitialViolationCount(vt.get().getInitialViolationCount());
			dt.setIsViolated(vt.get().isViolated());
			dt.setDateOfViolation(vt.get().getDateOfViolation());
			
			return ResponseEntity.status(HttpStatus.OK).body(dt);
		}
	}
}
