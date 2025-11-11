package com.harish.quizapp.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.quizapp.Dto.AdminPromotionDto;
import com.harish.quizapp.Dto.PromotionDto;
import com.harish.quizapp.Dto.UpdateStatusDto;
import com.harish.quizapp.Model.AdminApplication;
import com.harish.quizapp.Model.ComplaintsTable;
import com.harish.quizapp.Model.InstructorApplication;
import com.harish.quizapp.Service.AdminService;

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
	
	@PostMapping("/complaints/update/status/{complaintid}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> updateComplaintStatus(@RequestBody UpdateStatusDto usd, @PathVariable int complaintid)
	{
		return as.updateComplaintStatus(usd,complaintid);
	}
	
	@GetMapping("superadmin/get/all/admin/applications")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ResponseEntity<List<AdminApplication>> getAllPendingRequestForAdmin()
	{
		return as.getAllPendingApplications();
	}
	
	@PostMapping("/superadmin/make/changes/role/instructor")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ResponseEntity<String> updateChangesToApplication(@RequestBody AdminPromotionDto apd)
	{
		return as.promoteInsttoAdmin(apd);
	}
}
