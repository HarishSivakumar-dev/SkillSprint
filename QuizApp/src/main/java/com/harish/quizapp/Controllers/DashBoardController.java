package com.harish.quizapp.Controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.quizapp.Service.DashBoardService;

@RestController
@RequestMapping("/app")
public class DashBoardController
{
	@Autowired
	private DashBoardService dbs;
	
	@GetMapping("/dashboard")
	@PreAuthorize("hasAnyRole('USER','INSTRUCTOR','ADMIN', 'ADMIN_MANAGER', 'SUPER_ADMIN')")	
	public Map<String, Double> dashboardController()
	{
		return dbs.getall();
	}
	
	@GetMapping("/dashboard/daily")
	@PreAuthorize("hasAnyRole('USER','INSTRUCTOR','ADMIN', 'ADMIN_MANAGER', 'SUPER_ADMIN')")	
	public Map<String, Double> dashboardDailyController()
	{
		return dbs.getDaily();
	}

}


