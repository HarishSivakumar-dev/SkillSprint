package com.harish.quizapp.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.AdminPromotionRepo;
import com.harish.quizapp.DataRepos.ComplaintAuditRepo;
import com.harish.quizapp.DataRepos.ComplaintsRepo;
import com.harish.quizapp.DataRepos.InstAppRepo;
import com.harish.quizapp.DataRepos.InstructorUpdateRepo;
import com.harish.quizapp.DataRepos.RoleRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.DataRepos.ViolationTableRepo;
import com.harish.quizapp.Dto.AdminPromotionDto;
import com.harish.quizapp.Dto.PromotionDto;
import com.harish.quizapp.Dto.UpdateStatusDto;
import com.harish.quizapp.Model.AdminApplication;
import com.harish.quizapp.Model.ComplaintAuditTable;
import com.harish.quizapp.Model.ComplaintsTable;
import com.harish.quizapp.Model.InstructorApplication;
import com.harish.quizapp.Model.InstructorUpdatedTable;
import com.harish.quizapp.Model.Roles;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.Model.ViolationsTable;
import com.harish.quizapp.enums.ComplaintStatus;
import com.harish.quizapp.enums.PromotionStatus;

@Service
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
	
	public ResponseEntity<String> updateUserRoles(PromotionDto dto)
	{
		InstructorUpdatedTable iut=new InstructorUpdatedTable();
		UserRegistration ur= rep.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new BadCredentialsException("No Admin Found"));
		UserRegistration user=rep.findById(dto.getUserId()).orElseThrow();
		InstructorApplication app=iar.findByUser(user).orElseThrow();
		
		app.setIsPending(false);
		if(!dto.isApproved())
		{
			app.setIsRejected(true);
			iar.save(app);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Update Success !");
		}
		
		Roles r= rr.findByRolename("ROLE_INSTRUCTOR").orElseThrow();
		user.getRoles().add(r);
		rep.save(user);
		
		iut.setAdmin(ur);
		iut.setUser(user);
		iut.setDate(LocalDate.now());
		iut.setTime(LocalDateTime.now());
		
		iur.save(iut);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("User Promoted !");
	}
	public ResponseEntity<List<InstructorApplication>> getAllApplications()
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(iar.findByIsPending(true));
	}
	
	public ResponseEntity<List<ComplaintsTable>> getAllComplaints()
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(comp.findByStatus(ComplaintStatus.Pending));
	}
	public ResponseEntity<List<AdminApplication>> getAllPendingApplications()
	{
		return ResponseEntity.status(HttpStatus.OK).body(apr.findByPromotionStatus(PromotionStatus.Pending));
	}
	public ResponseEntity<String> promoteInsttoAdmin(AdminPromotionDto pd)
	{
		UserRegistration supadmin= rep.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
		
		AdminApplication aa= apr.findByUser_Id(pd.getUserid()).orElseThrow();
		aa.setReviewedOn(LocalDateTime.now());
		aa.setSuperAdmin(supadmin);
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
	public ResponseEntity<String> updateComplaintStatus(UpdateStatusDto usd, int complaintid)
	{
		ComplaintsTable ct= comp.findById(complaintid).orElseThrow();
		
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
				}
				
			}
			else
			{
				ViolationsTable vio=new ViolationsTable();
				vio.setFinalViolationCount(0);
				vio.setInitialViolationCount(1);
				vio.setViolated(false);
				vio.setInstructor(ct.getInstructor());
				
				vtr.save(vio);
			}
			
		}
		comp.save(ct);
		
		ComplaintAuditTable cat=new ComplaintAuditTable();
		cat.setAdmin(ur);
		cat.setUser(ct.getUser());
		cat.setComments(usd.getComments());
		cat.setTime(LocalDateTime.now());
		
		car.save(cat);
		
		return ResponseEntity.status(HttpStatus.OK).body("UPDATED");
	}
	
}
