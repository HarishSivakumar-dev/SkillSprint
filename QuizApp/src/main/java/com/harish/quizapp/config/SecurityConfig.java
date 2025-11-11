package com.harish.quizapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.harish.quizapp.AuthenticationFilters.CustomAuthenticationFilter;
import com.harish.quizapp.AuthenticationFilters.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
	
	@Autowired
	private CustomAuthenticationFilter caf;
	@Autowired
	private JwtFilter jwt;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity sec) throws Exception
	{
		return sec.csrf(csrf->csrf.disable())
				  .authorizeHttpRequests(request->request.requestMatchers("/app/login","/app/register","/app/refresh").permitAll().anyRequest().authenticated())
				  .authenticationProvider(caf)
				  .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				  .formLogin(login->login.disable())
				  .addFilterBefore(jwt,UsernamePasswordAuthenticationFilter.class)
				  .build();
		
	}
	
	@Bean
	public AuthenticationManager authmanager(HttpSecurity sec) throws Exception
	{
		return sec.getSharedObject(AuthenticationManagerBuilder.class)
				  .authenticationProvider(caf)
				  .build();
	}

}
