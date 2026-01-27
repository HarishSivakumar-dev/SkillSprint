package com.harish.quizapp.asyncFunctionCalls;

import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import com.harish.quizapp.DataRepos.AdminPromotionRepo;
import com.harish.quizapp.enums.PromotionStatus;

public class AdminManagerAsyncCalls
{
	@Autowired
	private AdminPromotionRepo apr;
	
	@Async
	public CompletableFuture<Integer> getAllAdmins()
	{
		return CompletableFuture.supplyAsync(()-> apr.countByPromotionStatus(PromotionStatus.Promoted), null);
	}

}
