package com.harish.quizapp.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.AdminPromotionRepo;
import com.harish.quizapp.DataRepos.ComplaintAuditRepo;
import com.harish.quizapp.DataRepos.ComplaintsRepo;
import com.harish.quizapp.DataRepos.InstAppRepo;
import com.harish.quizapp.DataRepos.InstructorRepo;
import com.harish.quizapp.DataRepos.InstructorUpdateRepo;
import com.harish.quizapp.DataRepos.RoleRepo;
import com.harish.quizapp.DataRepos.SkillApprovalRepo;
import com.harish.quizapp.DataRepos.SkillsRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.DataRepos.ViolationTableRepo;
import com.harish.quizapp.Dto.AdminManagerDetailsDto;
import com.harish.quizapp.Dto.AdminPromotionDto;
import com.harish.quizapp.Dto.PromotionApplicationUserDto;
import com.harish.quizapp.Dto.PromotionDto;
import com.harish.quizapp.Dto.SkillApprovalDto;
import com.harish.quizapp.Dto.UpdateStatusDto;
import com.harish.quizapp.Model.AdminApplication;
import com.harish.quizapp.Model.ComplaintAuditTable;
import com.harish.quizapp.Model.ComplaintsTable;
import com.harish.quizapp.Model.InstructorApplication;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.InstructorUpdatedTable;
import com.harish.quizapp.Model.Roles;
import com.harish.quizapp.Model.SkillApproval;
import com.harish.quizapp.Model.Skills;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.Model.ViolationsTable;
import com.harish.quizapp.enums.ComplaintStatus;
import com.harish.quizapp.enums.PromotionStatus;
import com.harish.quizapp.enums.SkillStatus;

import jakarta.transaction.Transactional;

@Service
@Component 
public class AdminService
{
	@Autowired
	private ViolationTableRepo vtr;
	@Autowired 
	private UserRepo rep;
	@Autowired
	private ComplaintsRepo comp;
	@Autowired
	private InstructorUpdateRepo iur;
	@Autowired 
	private InstAppRepo iar;
	@Autowired
	private RoleRepo rr;
	@Autowired
	private ComplaintAuditRepo car;
	@Autowired 
	private AdminPromotionRepo apr;
	@Autowired
	private InstructorRepo intrep;
	@Autowired
	private SkillApprovalRepo sar;
	@Autowired 
	private SkillsRepo sklrep;
	
	public ResponseEntity<String> updateUserRoles(PromotionDto dto)
	{
		InstructorUpdatedTable iut=new InstructorUpdatedTable();
		UserRegistration ur= rep.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new BadCredentialsException("No Admin Found"));
		UserRegistration user=rep.findById(dto.getUserId()).orElseThrow();
		InstructorApplication app=iar.findByUser(user).orElseThrow();
		
		iut.setAdmin(ur);
		iut.setUser(user);
		iut.setDate(LocalDate.now());
		iut.setTime(LocalDateTime.now());

		
		app.setIsPending(false);
		
		System.out.println(dto.isApproved());
		
		if(!dto.isApproved())
		{
			app.setIsRejected(true);
			iut.setIsPromoted(false);
			iar.delete(app);
			iur.save(iut);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Update Success !");
		}
		
		Roles r= rr.findByRolename("ROLE_INSTRUCTOR").orElseThrow();
		user.getRoles().add(r);
		rep.save(user);
		
		app.setIsRejected(false);
		iut.setIsPromoted(true);
		iar.save(app);
		iur.save(iut);
		
		InstructorProfile ip= new InstructorProfile();
		ip.setUserName(user);
		ip.setJoinedDate(LocalDate.now());
		ip.setMail(user.getEmail());
		ip.setFullName(user.getName());
		ip.setIsViolated(false);
		intrep.save(ip);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("User Status Updated !");
	}
	public ResponseEntity<List<PromotionApplicationUserDto>> getAllApplications()
	{
		List<InstructorApplication> lst =iar.findByIsPending(true);
		List<PromotionApplicationUserDto> dto= new ArrayList<>();
		
		for(InstructorApplication app : lst)
		{
			PromotionApplicationUserDto usr = new PromotionApplicationUserDto();
			
			usr.setId(app.getId());
			usr.setEmail(app.getUser().getEmail());
			usr.setUserId(app.getUser().getId());
			usr.setLinkedin(app.getLinkedin());
			usr.setName(app.getUser().getName());
			usr.setQualification(app.getQualification());
			usr.setReason(app.getReason());
			usr.setResumeUrl(app.getResumeUrl());
			usr.setIsPending(app.getIsPending());
			
			dto.add(usr);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	public ResponseEntity<List<ComplaintsTable>> getAllComplaints()
	{
		return ResponseEntity.status(HttpStatus.OK).body(comp.findByStatus(ComplaintStatus.Pending));
	}
	public ResponseEntity<List<AdminApplication>> getAllPendingApplications()
	{
		return ResponseEntity.status(HttpStatus.OK).body(apr.findByPromotionStatus(PromotionStatus.Pending));
	}
	public ResponseEntity<String> promoteInsttoAdmin(AdminPromotionDto pd)
	{
		UserRegistration adminManager= rep.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
		
		AdminApplication aa= apr.findByUser_Id(pd.getUserid()).orElseThrow();
		aa.setReviewedOn(LocalDateTime.now());
		aa.setAdminManager(adminManager);
		aa.setRemarks(pd.getRemarks());
		
		if(pd.getPromotionStatus().equals(PromotionStatus.Promoted.toString()))
		{
			Roles r= rr.findByRolename("ROLE_ADMIN").orElseThrow();
			UserRegistration regs= rep.findById(pd.getUserid()).orElseThrow();
			regs.getRoles().add(r);
			
			rep.save(regs);
			
			aa.setPromotionStatus(PromotionStatus.Promoted);
			apr.save(aa);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Changes Updated !");
		}
		else if(pd.getPromotionStatus().equals(PromotionStatus.Rejected.toString()))
		{
			aa.setPromotionStatus(PromotionStatus.Rejected);
			apr.save(aa);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Changes Updated !");
		}
		else
		{
			aa.setPromotionStatus(PromotionStatus.Pending);
			apr.save(aa);
	
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Changes Updated !");
		}
	
	}
	
	@Transactional
	public ResponseEntity<String> updateComplaintStatus(UpdateStatusDto usd)
	{
		ComplaintsTable ct= comp.findById(usd.getComplaintId()).orElseThrow();
		
		UserRegistration ur= rep.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new BadCredentialsException("No Admin Found"));
		
		if(ct.getStatus().equals(ComplaintStatus.Rejected) || ct.getStatus().equals(ComplaintStatus.Resolved))
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Already Updated by Another Admin");
		}
		
		if(usd.getStatus().equals(ComplaintStatus.Pending.toString()))
		{
			ct.setStatus(ComplaintStatus.Pending);
		}
		else if(usd.getStatus().equals(ComplaintStatus.Rejected.toString()))
		{
			ct.setStatus(ComplaintStatus.Rejected);
		}
		else
		{
			ct.setStatus(ComplaintStatus.Resolved);
			
			Optional<ViolationsTable> vt= vtr.findByInstructor_Id(ct.getInstructor().getId());
			
			if(vt.isPresent())
			{
				if(vt.get().getInitialViolationCount()<3)
				{
					vt.get().setInitialViolationCount(vt.get().getInitialViolationCount()+1);
					vtr.save(vt.get());
				}
				else if(vt.get().getFinalViolationCount()<3)
				{
					vt.get().setFinalViolationCount(vt.get().getInitialViolationCount()+1);
					vtr.save(vt.get());
				}
				else
				{
					vt.get().setViolated(true);
					vt.get().setDateOfViolation(LocalDateTime.now());
					
					vtr.save(vt.get());
					
					InstructorProfile ip=vt.get().getInstProf();
					ip.setIsViolated(true);
					intrep.save(ip);
				}
				
			}
			else
			{
				InstructorProfile ip= intrep.findByUserName_UserName(ct.getInstructor().getUserName()).get();
				
				ViolationsTable vio=new ViolationsTable();
				vio.setFinalViolationCount(0);
				vio.setInitialViolationCount(1);
				vio.setViolated(false);
				vio.setInstProf(ip);
				vio.setInstructor(ct.getInstructor());
				
				vtr.save(vio);
			}
			
		}
		comp.save(ct);
		
		ComplaintAuditTable cat=new ComplaintAuditTable();
		cat.setAdmin(ur);
		cat.setUser(ct.getUser());
		cat.setComplaintHandled(true);
		cat.setComments(usd.getComments());
		cat.setTime(LocalDateTime.now());
		
		car.save(cat);
		
		return ResponseEntity.status(HttpStatus.OK).body("UPDATED");
	}
	
	public ResponseEntity<List<SkillApproval>> getAllSkillApprovalRequests()
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(sar.findAll());
	}
	
	public ResponseEntity<String> setSkillApprovalRequests(List<SkillApprovalDto> dto)
	{
		UserRegistration user= rep.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
		
		List<Integer> sl= dto.stream()
							 .map(r-> r.getId())
							 .toList();
		List<SkillApproval> sk= sar.findAllById(sl);
		
		List<Integer> inst= dto.stream()
							   .map(r->r.getInstructorId())
							   .toList();
		List<InstructorProfile> dbinst= intrep.findAllById(inst);
		
		Map<Integer, SkillApprovalDto> map= new HashMap<>();
		Map<Integer, InstructorProfile> map1= new HashMap<>();
		
		for(SkillApprovalDto dt : dto)
		{
			map.put(dt.getId(), dt);
		}
		for(InstructorProfile ip : dbinst)
		{
			map1.put(ip.getUserName().getId(), ip);
		}
		
		for(SkillApproval sv : sk)
		{
			if(map.get(sv.getId()).getStatus()==SkillStatus.Approved)
			{
				Optional<Skills> opt=sklrep.findBySkillName(sv.getSkillApplied());
				InstructorProfile ip= map1.get(sv.getInstrutor().getId());
				
				if(opt.isEmpty())
				{
					Skills skls= new Skills();
					skls.setSkillName(sv.getSkillApplied());
					Skills sd= sklrep.save(skls);
					ip.getSkills().add(sd);
				}
				else
				{
					ip.getSkills().add(opt.get());
				}
				
				intrep.save(ip);
				
				sv.setStatus(SkillStatus.Approved);
				sv.setAdmin(user);
			}
			else
			{
				sv.setStatus(map.get(sv.getId()).getStatus());
				sv.setAdmin(user);
			}
		}
		sar.saveAll(sk);
		
		return ResponseEntity.status(HttpStatus.OK).body("Update Successfull ");
	}
	
	public ResponseEntity<List<AdminManagerDetailsDto>> getAllAdminManagerDetails()
	{
		
		List<AdminApplication> aa= apr.findAll();
		aa.removeIf(app ->app.getPromotionStatus()!=PromotionStatus.Promoted);
		
		List<AdminManagerDetailsDto> amd= new ArrayList<>();
		
		for(AdminApplication app : aa)
		{
			UserRegistration user =app.getUser();
			
			long exp=ChronoUnit.MONTHS.between(app.getReviewedOn(), LocalDate.now());
			float exp1=exp/12;
			int noofPromotionsHandled=iur.countByAdmin_Id(user.getId());
			int noofSkillsHandled= sar.countByAdmin_Id(user.getId());
			int noofViolationsHandled= car.countByAdmin_Id(user.getId());
			String email=user.getEmail();
			String userName=user.getUserName();
			String name=user.getName();
			int id=user.getId();
			
			AdminManagerDetailsDto dt= new AdminManagerDetailsDto();
			dt.setEmail(email);
			dt.setId(id);
			dt.setNoofPromotions(noofPromotionsHandled);
			dt.setNoofSkillsApproved(noofSkillsHandled);
			dt.setNoofViolationsHandled(noofViolationsHandled);
			dt.setTenure(exp1);
			dt.setUserName(userName);
			dt.setName(name);
			
			amd.add(dt);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(amd);
		
	}
	
	public ResponseEntity<String> setNewAdminManager(AdminManagerDetailsDto amd) 
	{
		UserRegistration user= rep.findByUserName(amd.getUserName()).orElseThrow();
		Roles rol= rr.findByRolename("ROLE_ADMIN_MANAGER").orElseThrow();
		
		user.getRoles().add(rol);
		
		rep.save(user);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Promoted !");
	}
	
}
