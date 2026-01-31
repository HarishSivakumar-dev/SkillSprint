package com.harish.quizapp.Controllers;

import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.quizapp.Dto.AdminAnalyticsDto;
import com.harish.quizapp.Dto.AdminManagerAnalyticsDto;
import com.harish.quizapp.Dto.HigherRolesProfileDto;
import com.harish.quizapp.Dto.SuperAdminAnalyticsDto;
import com.harish.quizapp.Service.AsyncProfilesService;

@RestController
@RequestMapping("/app/profile")
public class AnalyticsController
{
	@Autowired
	private AsyncProfilesService aps;
	
	@GetMapping("/analytics/superAdmin")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ResponseEntity<SuperAdminAnalyticsDto> getSuperAdminAnalytics()
	{
		return aps.getSuperAdminAnalytics();
	}
	
	@GetMapping("/analytics/adminManager")
	@PreAuthorize("hasRole('ADMIN_MANAGER')")
	public CompletableFuture<AdminManagerAnalyticsDto> getAdminManagerAnalytics()
	{
		return aps.getAdminManagerAnalytics();
	}
	
	@GetMapping("/analytics/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public CompletableFuture<AdminAnalyticsDto> getAdminAnaytics()
	{
		return aps.getAdminAnalytics();
	}
	
	@GetMapping("/")
	@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN', 'ADMIN_MANAGER')")
	public ResponseEntity<HigherRolesProfileDto> getBasicProfileDetails()
	{
		return aps.getBasicProfileDetails();
	}

}
