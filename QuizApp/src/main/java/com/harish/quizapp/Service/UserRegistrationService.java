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
import com.harish.quizapp.DataRepos.UserProfileRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Model.Roles;
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
	private RoleRepo rr;
	@Autowired
	private EmailTokenRepo etr;
	
	BCryptPasswordEncoder enc=new BCryptPasswordEncoder(15);



	public ResponseEntity<String> registerUserIntoDb(UserRegistration ur)
	{
		Roles r=rr.findByRolename("ROLE_USER").orElseThrow(()->new BadCredentialsException("No Roles Found !"));
		ur.getRoles().add(r);
		ur.setJoinedDate(LocalDateTime.now());
		ur.setPassword(enc.encode(ur.getPassword()));
		
		Optional<UserRegistration> reg= this.ur.findByUserName(ur.getUserName());
		UserProfile pro=new UserProfile();
		
		if(etr.findByEmailAndIsVerifiedTrue(ur.getEmail()).isPresent())
		{
			this.ur.save(ur);
			
			pro.setUserName(ur);
			pro.setEmail(ur.getEmail());
			pro.setFullName(ur.getName());
			pro.setJoinedDate(LocalDateTime.now());
			pro.setIsEmailVerified(true);
			
			upr.save(pro);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Registered");
		}
		else if(!reg.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username Already Exists !");
		}
		else
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Not Verified");
		}
	}
}
