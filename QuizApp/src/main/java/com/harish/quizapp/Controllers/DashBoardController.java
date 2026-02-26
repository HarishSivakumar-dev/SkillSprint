package com.harish.quizapp.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class DashBoardController
{
	@GetMapping("/dashboard")
	public String dashboardController()
	{
		return "Welcome to the Dashboard";
	}

}
