package com.harish.quizapp.asyncFunctionCalls;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.harish.quizapp.DataRepos.InstructorRepo;
import com.harish.quizapp.DataRepos.SkillApprovalRepo;
import com.harish.quizapp.DataRepos.SkillsRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Dto.InstructorDto;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.SkillApproval;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.enums.SkillStatus;
import jakarta.transaction.Transactional;

@Component 
public class InstructorAsyncCalls
{
	@Autowired
	private InstructorRepo ir;
	@Autowired
	private SkillsRepo skr;
	@Autowired
	private UserRepo ur;
	@Autowired
	private SkillApprovalRepo rep;
	@Autowired
	@Qualifier(value="Instructor_Template")
	private RedisTemplate<String, InstructorProfile> rt;
	
	@Async("asyncExecutor")
	public void saveInstructorDetails(InstructorProfile ip, String totexp)
	{
		ip.setTotExp(totexp);
		ir.save(ip);
	}
	
	@Async("asyncExecutor")
	@Transactional
	public void saveInstructorProfileDetails(InstructorDto instdt, String name)
	{
		UserRegistration reg= ur.findByUserName(name).orElseThrow();
		InstructorProfile ip= ir.findByUserName_UserName(name).orElseThrow();
		
		List<String> skills=skr.findAll()
				   .stream()
				   .map(r-> r.getSkillName())
				   .toList();
		List<String> usrskl= instdt.getSkills();

		int approvedNo=0;
		int pendingNo=0;
		
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
				sa.setInstructor(reg);
				sa.setStatus(SkillStatus.Pending);
				sa.setSkillApplied(st);
	
				rep.save(sa);
	
				pendingNo++;
			}
		}
		
		ir.save(ip);
		rt.opsForValue().set(name, ip, 10, TimeUnit.MINUTES);
	}
}
