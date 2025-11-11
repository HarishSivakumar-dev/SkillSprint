package com.harish.quizapp.AuthenticationFilters;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.harish.quizapp.Util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter
{

	@Autowired
	private JwtUtil jwt;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		String url=request.getRequestURI();
		if(url.equals("/app/login") || url.equals("/app/register") || url.equals("/app/refresh"))
		{
			System.out.println(url);
			filterChain.doFilter(request, response);
			return;
		}
		else
		{
			String auth=request.getHeader("Authorization");
			String token=auth.substring(7);
			
			if(auth.isEmpty() || token.isEmpty())
			{
				throw new BadCredentialsException("No Authorization Header");
			}
			else
			{
				if(jwt.validateAccess(token))
				{
					List<String> roles= jwt.rolesAccess(token);
					
					String name=jwt.clientAccess(token);
					
					List<SimpleGrantedAuthority> role=roles.stream()
														   .map(rol->new SimpleGrantedAuthority(rol))
														   .collect(Collectors.toList());
					
					Authentication of= new UsernamePasswordAuthenticationToken(name,null,role);
					
					SecurityContextHolder.getContext().setAuthentication(of);
					
					filterChain.doFilter(request, response);
				}
				else
				{
					throw new BadCredentialsException("Invalid Token / Token Expired ");
				}
			}
			
		}
		
	}

}
