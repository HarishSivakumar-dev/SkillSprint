package com.harish.quizapp.AuthenticationFilters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Model.UserRegistration;


@Component
public class CustomAuthenticationFilter implements AuthenticationProvider
{
	@Autowired
	private UserRepo ur;
	
	BCryptPasswordEncoder enc=new BCryptPasswordEncoder(15);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		String name=authentication.getName();
		String pass=authentication.getCredentials().toString();
		
		
		
		UserRegistration encode=ur.findByUserName(name).orElseThrow(()-> new BadCredentialsException("NO USERS FOUND"));
		String encoded=encode.getPassword();
		
		if(enc.matches(pass, encoded))
		{
			return new UsernamePasswordAuthenticationToken(name,null);
		}
		else
		{
			throw new BadCredentialsException("Password Mismatch");
		}
	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
}
