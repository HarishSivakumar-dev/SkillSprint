package com.harish.quizapp.Service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.SuperAdminRepo;
import com.harish.quizapp.Dto.AdminManagerAnalyticsDto;
import com.harish.quizapp.Dto.SuperAdminAnalyticsDto;
import com.harish.quizapp.Model.SuperAdminAnalytics;
import com.harish.quizapp.asyncFunctionCalls.AdminManagerAsyncCalls;

@Service
public class AsyncProfilesService 
{
	@Autowired
	private SuperAdminRepo sar;
	@Autowired
	private AdminManagerAsyncCalls calls;
	
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
	
	public CompletableFuture<?> getAdminAnalytics()
	{
		return null;
	}

}
