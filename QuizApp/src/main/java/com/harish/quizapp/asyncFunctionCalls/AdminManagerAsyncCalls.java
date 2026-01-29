package com.harish.quizapp.asyncFunctionCalls;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import com.harish.quizapp.DataRepos.AdminLogsRepo;
import com.harish.quizapp.DataRepos.AdminPromotionRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.enums.PromotionStatus;

public class AdminManagerAsyncCalls
{
	@Autowired
	private AdminPromotionRepo apr;
	@Autowired
	private Executor exe;
	@Autowired
	private UserRepo ur;
	@Autowired
	private AdminLogsRepo alr;
	
	
	public CompletableFuture<Integer> getAllAdmins()
	{
		return CompletableFuture.supplyAsync(()-> apr.countByPromotionStatus(PromotionStatus.Promoted), exe);
	}
	
	public CompletableFuture<Integer> getAllAdminApplications()
	{
		return CompletableFuture.supplyAsync(()-> apr.countByPromotionStatus(PromotionStatus.Pending), exe);
	}
	
	public CompletableFuture<Integer> getallApplicationsApprovedToday()
	{
		LocalDateTime yt= LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
		LocalDateTime tm= LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).plusDays(1);
		
		String reg= SecurityContextHolder.getContext().getAuthentication().getName();
		
		
		return CompletableFuture.supplyAsync(()-> 
		{	
			int id=ur.findByUserName(reg).get().getId();
			return apr.countByWindow(yt, tm, id);
			
		}, exe);
		
	}
	public CompletableFuture<Integer> getOldAdminApplications()
	{
		LocalDateTime td= LocalDateTime.now().minusDays(3);
		
		return CompletableFuture.supplyAsync(()-> apr.countByOldApplications(td), exe);
	}
	public CompletableFuture<Integer> getActiveAdminsToday()
	{
		LocalDate dat= LocalDate.now();
		
		return CompletableFuture.supplyAsync(()-> alr.countByActiveAdmins(dat));
	}
	
}
