package com.harish.quizapp.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.SuperAdminRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Dto.AdminAnalyticsDto;
import com.harish.quizapp.Dto.AdminManagerAnalyticsDto;
import com.harish.quizapp.Dto.HigherRolesProfileDto;
import com.harish.quizapp.Dto.SuperAdminAnalyticsDto;
import com.harish.quizapp.Model.Roles;
import com.harish.quizapp.Model.SuperAdminAnalytics;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.asyncFunctionCalls.AdminAsyncCalls;
import com.harish.quizapp.asyncFunctionCalls.AdminManagerAsyncCalls;

@Service
public class AsyncProfilesService 
{
	@Autowired
	private SuperAdminRepo sar;
	@Autowired
	private AdminManagerAsyncCalls calls;
	@Autowired
	private AdminAsyncCalls admn;
	@Autowired
	private UserRepo ur;
	
	public ResponseEntity<SuperAdminAnalyticsDto> getSuperAdminAnalytics()
	{
		SuperAdminAnalytics huh=sar.findAll().get(0);
		SuperAdminAnalyticsDto dt= new SuperAdminAnalyticsDto(huh.getTotCourses(),huh.getTotInstructors(),huh.getTotStudents(),huh.getTotAdmins(),huh.getTotAdminManagers(),huh.getMonthlyNewRegistrations(),huh.getLastComputedAt());
		
		return ResponseEntity.status(HttpStatus.OK).body(dt);
		
	}
	
	public CompletableFuture<AdminManagerAnalyticsDto> getAdminManagerAnalytics()
	{
		CompletableFuture<Integer> adminno= calls.getAllAdmins();
		CompletableFuture<Integer> actadminstod= calls.getActiveAdminsToday();
		CompletableFuture<Integer> pending= calls.getOldAdminApplications();
		CompletableFuture<Integer> approved= calls.getallApplicationsApprovedToday();
		CompletableFuture<Integer> application= calls.getAllAdminApplications();
		
		return CompletableFuture.allOf(adminno,actadminstod,pending,approved,application)
								.thenApply(v->
											{
												AdminManagerAnalyticsDto aad= new AdminManagerAnalyticsDto();
												aad.setActiveAdminsToday(actadminstod.join());
												aad.setApplicationsApprovedToday(approved.join());
												aad.setTotAdmins(adminno.join());
												aad.setPendingApplications(pending.join());
												aad.setAllApplications(application.join());
												
												return aad;
											
											}
												);
		
	}
	
	public CompletableFuture<AdminAnalyticsDto> getAdminAnalytics()
	{
		CompletableFuture<Integer> allComplaintsToday= admn.getAllComplaintsToday();
		CompletableFuture<Integer> oldComplaints= admn.getOldComplaints();
		CompletableFuture<Integer> complaintsHandledToday= admn.getComplaintsHandledToday();
		CompletableFuture<Integer> allSkillApprovalsToday= admn.getAllSkillApprovalsToday();
		CompletableFuture<Integer> oldSkillApprovals= admn.getOldSkillApprovals();
		CompletableFuture<Integer> skillApprovalsToday= admn.getSkillApprovalsToday();
		CompletableFuture<Integer> allInstructorApplications= admn.getAllInstructorApplications();
		CompletableFuture<Integer> oldInstructorApplications= admn.getOldInstructorApplications();
		CompletableFuture<Integer> instructorApplicationsToday= admn.getInstructorApplicationsReviewedToday();
		
		
		return CompletableFuture.allOf(allComplaintsToday, oldComplaints, complaintsHandledToday, allSkillApprovalsToday, oldSkillApprovals, skillApprovalsToday, allInstructorApplications, oldInstructorApplications, instructorApplicationsToday)
				 .thenApply(v-> {
					 
					 AdminAnalyticsDto dto = new AdminAnalyticsDto();
					 dto.setComplaintsHandledToday(complaintsHandledToday.join());
					 dto.setComplaintsPendingToday(allComplaintsToday.join());
					 dto.setOlderComplaints(oldComplaints.join());
					 dto.setSkillApprovalRequestsToday(allSkillApprovalsToday.join());
					 dto.setOlderSkillApprovals(oldSkillApprovals.join());
					 dto.setSkillApprovalsHandledToday(skillApprovalsToday.join());
					 dto.setInstructorApplicationsToday(allInstructorApplications.join());
					 dto.setOlderInstructorApplications(oldInstructorApplications.join());
					 dto.setInstructorApplicationsHandledToday(instructorApplicationsToday.join());
					 
					 return dto;
					 
				 }); 
	}
	
	public ResponseEntity<HigherRolesProfileDto> getBasicProfileDetails()
	{
		String user= SecurityContextHolder.getContext().getAuthentication().getName();
		
		UserRegistration usr= ur.findByUserName(user).orElseThrow();
		
		HigherRolesProfileDto dto = new HigherRolesProfileDto();
		dto.setUserId(usr.getId());
		dto.setUsername(user);
		dto.setRole(this.roleClassification(usr.getRoles()));
		dto.setEmail(usr.getEmail());
		dto.setAccountStatus(usr.getAccountStatus());
		dto.setLastLogin(usr.getLastLogin());
		
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	public String roleClassification(List<Roles> role)
	{
		List<Roles> rl= role;
		List<String> roles= rl.stream()
							  .map(r->r.getRolename())
							  .toList();
		
		if(roles.contains("ROLE_SUPER_ADMIN"))
		{
			return "ROLE_SUPER_ADMIN";
		}
		else if(roles.contains("ROLE_ADMIN_MANAGER"))
		{
			return "ROLE_ADMIN_MANAGER";
		}
		else
		{
			return "ROLE_ADMIN";
		}
		
	}

}
