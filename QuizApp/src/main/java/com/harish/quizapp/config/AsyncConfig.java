package com.harish.quizapp.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig 
{
	
	@Bean
	public Executor asyncExecutor()
	{
		ThreadPoolTaskExecutor ex= new ThreadPoolTaskExecutor();
		
		ex.setCorePoolSize(20);
		ex.setBeanName("async-executor");
		ex.setMaxPoolSize(30);
		ex.setQueueCapacity(150);
		ex.setThreadNamePrefix("async-");
		ex.initialize();
		
		return ex;
		
	}
	

}
