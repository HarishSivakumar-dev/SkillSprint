package com.harish.quizapp.asyncFunctionCalls;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Model.UserRegistration;

@Component
public class UserLoginAsync
{
	@Autowired
	private UserRepo ur;
	
	@Async("asyncExecutor")
	public void loginUser(String name,LocalDateTime lastlogin)
	{
		UserRegistration usr= ur.findByUserName(name).orElseThrow();
		usr.setLastLogin(LocalDateTime.parse(lastlogin.toString()));
		ur.save(usr);
	}

}
