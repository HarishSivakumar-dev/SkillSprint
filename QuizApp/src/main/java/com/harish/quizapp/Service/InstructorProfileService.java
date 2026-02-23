package com.harish.quizapp.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.InstructorRepo;
import com.harish.quizapp.DataRepos.SkillApprovalRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.DataRepos.ViolationTableRepo;
import com.harish.quizapp.Dto.CourseDetailsDto;
import com.harish.quizapp.Dto.InstructorDto;
import com.harish.quizapp.Dto.InstructorProfileDto;
import com.harish.quizapp.Dto.SkillResponseDto;
import com.harish.quizapp.Dto.ViolationResultDto;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.SkillApproval;
import com.harish.quizapp.Model.Skills;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.Model.ViolationsTable;
import com.harish.quizapp.asyncFunctionCalls.InstructorAsyncCalls;
import com.harish.quizapp.enums.CourseStatus;
import com.harish.quizapp.enums.SkillStatus;

@Service
public class InstructorProfileService
{
	@Autowired
	private InstructorRepo ir;
	@Autowired
	private CoursesRepo cr;
	@Autowired
	private UserRepo ur;
	@Autowired
	private SkillApprovalRepo rep;
	@Autowired 
	private ViolationTableRepo vtr;
	@Autowired
	private InstructorAsyncCalls isc;
	
	@Autowired
	@Qualifier(value="Instructor_Template")
	private RedisTemplate<String, InstructorProfile> rt;
	
	
	public ResponseEntity<InstructorProfileDto> getInstructorProfile()
	{
		InstructorProfile ip= rt.opsForValue().get(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if(ip==null)
		{
			ip= ir.findByUserName_UserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
			rt.opsForValue().set(ip.getUserName().getUserName(),ip,10, TimeUnit.MINUTES);
		}
		
		List<CourseDetails> cd =cr.findTop3ByInstructor_IdAndStatusOrderByCreatedAtDesc(ip.getId(), CourseStatus.Active);
		List<CourseDetailsDto> ew= new ArrayList<>();
		
		for(CourseDetails det : cd)
		{
			CourseDetailsDto dto= new CourseDetailsDto();
			
			dto.setUserName(det.getInstructor().getUserName().getUserName());
			dto.setCourseId(det.getId());
			dto.setInstructorId(det.getInstructor().getId());
			dto.setTitle(det.getTitle());
			dto.setCatagory(det.getCatagory());
			dto.setDescription(det.getDescription());
			dto.setDuration(det.getDuration());
			dto.setLevel(det.getLevel());
			dto.setFullName(det.getInstructor().getFullName());
			dto.setStatus(det.getStatus());
			dto.setRating(det.getRating());
			dto.setCreatedAt(det.getCreatedAt());
			
			ew.add(dto);
		}
				
		Period general= Period.between(ip.getJoinedDate(), LocalDate.now());
		int yearOfExp= general.getYears();
		int monthOfExp= general.getMonths();
		int days=general.getDays();
		String totExp= yearOfExp+" "+" Years" +monthOfExp+" "+" Months" +days+" "+" Days";
		
		isc.saveInstructorDetails(ip, totExp);
		
		InstructorProfileDto dt= new InstructorProfileDto(ip.getUserName().getUserName(), ip.getUserName().getName(),ip.getMail(), ip.getJoinedDate(), ip.getHeadLine(), ip.getShortBio(), ip.getAboutSec(), ip.getPhone(), ip.getIsViolated(), ip.getLinkedinUrl(), ip.getGithubUrl(), ip.getWebUrl(), ip.getPortfolioUrl(), ip.getTotCourses(), ip.getTotalRegistered(),ip.getTrainedStud(), ip.getTotReviews(), ip.getAvgRating(), ip.getCompletionRate(), ip.getTotExp(), ip.getSkills(),ew);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(dt);
	}
	
	public ResponseEntity<String> setInstructorProfile(InstructorDto instdt)
	{
		String user= SecurityContextHolder.getContext().getAuthentication().getName();
		InstructorProfile ip= rt.opsForValue().get(user);
		
		if(ip==null)
		{
			ip= ir.findByUserName_UserName(user).orElseThrow();
		}
		
		
		if(instdt.getAboutSec()!=null) ip.setAboutSec(instdt.getAboutSec());
		if(instdt.getHeadLine()!=null) ip.setHeadLine(instdt.getHeadLine());
		if(instdt.getPhone()!=null) ip.setPhone(instdt.getPhone());
		if(instdt.getLinkedinUrl()!=null) ip.setLinkedinUrl(instdt.getLinkedinUrl());
		if(instdt.getShortBio()!=null) ip.setShortBio(instdt.getShortBio());
		if(instdt.getWebUrl()!=null) ip.setWebUrl(instdt.getWebUrl());
		if(instdt.getGithubUrl()!=null) ip.setGithubUrl(instdt.getGithubUrl());
		if(instdt.getPortfolioUrl()!=null) ip.setPortfolioUrl(instdt.getPortfolioUrl());
		if(instdt.getSkills()!=null && !instdt.getSkills().isEmpty())
		{
			isc.saveInstructorProfileDetails(instdt, user);
		}
		
		InstructorProfile pr=ir.save(ip);
		
		rt.opsForValue().set(user,pr,10, TimeUnit.MINUTES);	
		
		return ResponseEntity.status(HttpStatus.OK).body("UPDATED");
	}
	
	public ResponseEntity<List<SkillResponseDto>> searchInstructorSkills()
	{
		UserRegistration user= ur.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
		
		List<Skills> sk= ir.findByUserName_UserName(user.getUserName()).get().getSkills();
		Optional<List<SkillApproval>>sa= rep.findByInstructor(user);
		
		List<SkillResponseDto> response1= new ArrayList<SkillResponseDto>();
		for(Skills k : sk )
		{
			SkillResponseDto dto= new SkillResponseDto();
			dto.setSkillName(k.getSkillName());
			dto.setStatus(SkillStatus.Approved);
			
			response1.add(dto);
		}
		
		if(sa.isEmpty())
		{	
			return ResponseEntity.status(HttpStatus.OK).body(response1);
		}
		else
		{
			List<SkillApproval> app=sa.get();
			
			List<SkillResponseDto> response= new ArrayList<SkillResponseDto>();
			
			for(SkillApproval approv : app)
			{
				SkillResponseDto dto= new SkillResponseDto();
				dto.setSkillName(approv.getSkillApplied());
				dto.setStatus(approv.getStatus());
				
				response.add(dto);
			}
			
			response.addAll(response1);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		
	}

	public ResponseEntity<ViolationResultDto> getViolationDetails()
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<ViolationsTable> vt= vtr.findByInstructor_Id(ur.findByUserName(name).get().getId());
		ViolationResultDto vrd = new ViolationResultDto();
		
		if(vt.isEmpty())
		{
			vrd.setFinalViolationCount(0);
			vrd.setInitialViolationCount(0);
			vrd.setIsViolated(false);
		}  
		else
		{
			vrd.setFinalViolationCount(vt.get().getFinalViolationCount());
			vrd.setInitialViolationCount(vt.get().getInitialViolationCount());
			vrd.setIsViolated(true);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(vrd);
	}
	
}
