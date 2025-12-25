package com.harish.quizapp.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.InstructorRepo;
import com.harish.quizapp.DataRepos.SkillApprovalRepo;
import com.harish.quizapp.DataRepos.SkillsRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.DataRepos.ViolationTableRepo;
import com.harish.quizapp.Dto.InstructorDto;
import com.harish.quizapp.Dto.SkillResponseDto;
import com.harish.quizapp.Dto.ViolationResultDto;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.SkillApproval;
import com.harish.quizapp.Model.Skills;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.Model.ViolationsTable;
import com.harish.quizapp.enums.CourseStatus;
import com.harish.quizapp.enums.SkillStatus;

@Service
public class InstructorProfileService
{
	@Autowired
	private SkillsRepo skr;
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
	
	
	public ResponseEntity<InstructorProfile> getInstructorProfile()
	{
		InstructorProfile prof= ir.findByUserName_UserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
		prof.setCourseDetails(cr.findTop3ByInstructor_IdAndStatusOrderByCreatedAtDesc(prof.getId(), CourseStatus.Active));
		 
		return ResponseEntity.status(HttpStatus.OK).body(prof);
	}
	
	public ResponseEntity<String> setInstructorProfile(InstructorDto instdt)
	{
		int approvedNo=0;
		int pendingNo=0;
		String user= SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration reg= ur.findByUserName(user).orElseThrow();
		InstructorProfile ip= ir.findByUserName_UserName(user).orElseThrow();
		
		if(instdt.getAboutSec()!=null) ip.setAboutSec(instdt.getAboutSec());
		if(instdt.getHeadLine()!=null) instdt.setHeadLine(instdt.getHeadLine());
		if(instdt.getPhone()!=null) ip.setPhone(instdt.getPhone());
		if(instdt.getLinkedinUrl()!=null) ip.setLinkedinUrl(instdt.getLinkedinUrl());
		if(instdt.getShortBio()!=null) ip.setShortBio(instdt.getShortBio());
		if(instdt.getWebUrl()!=null) ip.setWebUrl(instdt.getWebUrl());
		if(instdt.getGithubUrl()!=null) ip.setGithubUrl(instdt.getGithubUrl());
		if(instdt.getPortfolioUrl()!=null) ip.setPortfolioUrl(instdt.getPortfolioUrl());
		if(!instdt.getSkills().isEmpty())
		{
			List<String> skills=skr.findAll()
								   .stream()
								   .map(r-> r.getSkillName())
								   .toList();
			List<String> usrskl= instdt.getSkills();
			
			for(String st : usrskl)
			{
				int flag=0;
				for(int i=0; i<skills.size(); i++)
				{
					if(st.equals(skills.get(i)))
					{
						flag=1;
						break;
					}
				}
				
				if(flag==1)
				{
					ip.getSkills().add(skr.findBySkillName(st).get());
					approvedNo++;
					
				}
				else
				{
					SkillApproval sa= new SkillApproval();
					sa.setInstrutor(reg);
					sa.setStatus(SkillStatus.Pending);
					sa.setSkillApplied(st);
					
					rep.save(sa);
					
					pendingNo++;
				}
			}
		
		ir.save(ip);
		
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(approvedNo + "Updated !!" + pendingNo + "Pending for Admin Verification. Check over the Skills Section for complete Information.");
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
