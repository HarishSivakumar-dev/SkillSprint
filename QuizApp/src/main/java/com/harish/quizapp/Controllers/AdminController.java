package com.harish.quizapp.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harish.quizapp.Dto.AdminManagerDetailsDto;
import com.harish.quizapp.Dto.AdminPromotionDto;
import com.harish.quizapp.Dto.PromotionDto;
import com.harish.quizapp.Dto.SkillApprovalDto;
import com.harish.quizapp.Dto.UpdateStatusDto;
import com.harish.quizapp.Model.AdminApplication;
import com.harish.quizapp.Model.ComplaintsTable;
import com.harish.quizapp.Model.InstructorApplication;
import com.harish.quizapp.Model.SkillApproval;
import com.harish.quizapp.Service.AdminService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController
{
	@Autowired
	private AdminService as;
	
	
	@PostMapping("/promote/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> updateUserRole(@RequestBody PromotionDto dto)
	{
		return as.updateUserRoles(dto);
	}
	
	@GetMapping("/get/instructor/applications")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<InstructorApplication>> getApplications()
	{
		return as.getAllApplications();
	}
	
	@GetMapping("/getallcomplaints")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ComplaintsTable>> getAllComplaints()
	{
		return as.getAllComplaints();
	}
	
	@PostMapping("/complaints/update/status")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> updateComplaintStatus(@RequestBody UpdateStatusDto usd)
	{
		return as.updateComplaintStatus(usd);
	}
	
	@GetMapping("/get/all/admin/applications")
	@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_MANAGER')")
	public ResponseEntity<List<AdminApplication>> getAllPendingRequestForAdmin()
	{
		return as.getAllPendingApplications();
	}
	
	@PostMapping("/make/changes/role/instructor")
	@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_MANAGER')")
	public ResponseEntity<String> updateChangesToApplication(@RequestBody AdminPromotionDto apd)
	{
		return as.promoteInsttoAdmin(apd);
	}
	
	@GetMapping("/admin/get/approval/skills")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<SkillApproval>> getAllSkillApprovalRequests()
	{
		return as.getAllSkillApprovalRequests();
	}
	
	@PostMapping("/admin/set/approval/skills")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> setSkillApprovalStatus( @Valid @RequestBody List<SkillApprovalDto> dto)
	{
		return as.setSkillApprovalRequests(dto);
	}
	
	@GetMapping("/admin/get/allAdmins/select/AdminManager")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ResponseEntity<List<AdminManagerDetailsDto>> getAllAdminforAdminManager()
	{
		return as.getAllAdminManagerDetails();
	}
	
	@PostMapping("/admin/set/new/adminmanager")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ResponseEntity<String> setNewAdminManager(@RequestBody AdminManagerDetailsDto amd)
	{
		return as.setNewAdminManager(amd);
	}
}
