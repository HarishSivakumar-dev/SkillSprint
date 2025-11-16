package com.harish.quizapp.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.EmailTokenRepo;
import com.harish.quizapp.DataRepos.RoleRepo;
import com.harish.quizapp.DataRepos.StreakMainRepo;
import com.harish.quizapp.DataRepos.UserProfileRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Dto.RegistrationDto;
import com.harish.quizapp.Model.Roles;
import com.harish.quizapp.Model.StreakTable;
import com.harish.quizapp.Model.UserProfile;
import com.harish.quizapp.Model.UserRegistration;


@Service
public class UserRegistrationService
{
	@Autowired
	private UserRepo ur;
	@Autowired
	private UserProfileRepo upr;
	@Autowired 
	private StreakMainRepo smr;
	@Autowired 
	private RoleRepo rr;
	@Autowired
	private EmailTokenRepo etr;
	
	BCryptPasswordEncoder enc=new BCryptPasswordEncoder(15);



	public ResponseEntity<String> registerUserIntoDb(RegistrationDto ru)
	{		
		Optional<UserRegistration> reg= this.ur.findByUserName(ru.getUserName());
		
		if(!reg.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username Already Exists !");
		}
		
		if(etr.findByEmailAndIsVerifiedTrue(ru.getEmail()).isPresent())
		{
			UserRegistration ur=new UserRegistration();
			UserProfile pro=new UserProfile();
			StreakTable st= new StreakTable();
			
			Roles r=rr.findByRolename("ROLE_USER").orElseThrow(()->new BadCredentialsException("No Roles Found !"));
			ur.getRoles().add(r);
			ur.setJoinedDate(LocalDateTime.now());
			ur.setEmail(ru.getEmail());
			ur.setName(ru.getName());
			ur.setPassword(enc.encode(ru.getPassword()));
			ur.setIsEmailVerified(true);
			ur.setUserName(ru.getUserName());
			
			
			UserRegistration regi=this.ur.save(ur);
			
			pro.setUserName(regi);
			pro.setEmail(regi.getEmail());
			pro.setFullName(regi.getName());
			pro.setJoinedDate(LocalDateTime.now());
			pro.setIsEmailVerified(true);
			
			upr.save(pro);
			
			st.setStreak(0);
			st.setUserId(ur);
			
			smr.save(st);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Registered");
		}
		else
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Not Verified");
		}
	}
}
