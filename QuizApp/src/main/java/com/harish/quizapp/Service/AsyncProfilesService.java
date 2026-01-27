package com.harish.quizapp.Service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.SuperAdminRepo;
import com.harish.quizapp.Dto.SuperAdminAnalyticsDto;
import com.harish.quizapp.Model.SuperAdminAnalytics;

@Service
public class AsyncProfilesService 
{
	@Autowired
	private SuperAdminRepo sar;
	
	public ResponseEntity<SuperAdminAnalyticsDto> getSuperAdminAnalytics()
	{
		SuperAdminAnalytics huh=sar.findAll().get(0);
		SuperAdminAnalyticsDto dt= new SuperAdminAnalyticsDto(huh.getTotCourses(),huh.getTotInstructors(),huh.getTotStudents(),huh.getTotAdmins(),huh.getTotAdminManagers(),huh.getMonthlyNewRegistrations(),huh.getLastComputedAt());
		
		return ResponseEntity.status(HttpStatus.OK).body(dt);
		
	}
	
	public CompletableFuture<?> getAdminManagerAnalytics()
	{
		return null;
	}
	
	public CompletableFuture<?> getAdminAnalytics()
	{
		return null;
	}

}
