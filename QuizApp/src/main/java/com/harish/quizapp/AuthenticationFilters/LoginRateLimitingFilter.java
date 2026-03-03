package com.harish.quizapp.AuthenticationFilters;

import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Model.UserRegistration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class LoginRateLimitingFilter extends OncePerRequestFilter
{
	@Autowired
	@Qualifier(value = "loginFilter")
	private RedisTemplate<String, Object> rt;

	@Autowired
	private UserRepo ur;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		String req= request.getRequestURI();
		String name= request.getHeader("X-Username");
		String ip=request.getHeader("X-Forwarded-For");
		
		
		if(ip==null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
		{
			ip=request.getRemoteAddr();
		}
		
		if(req.equals("/app/login"))
		{
			UserRegistration ureg= ur.findByUserName(name).orElseThrow();
			Object us=rt.opsForHash().get("client:"+ureg.getId()+":"+ip, "login_count");
			if(us==null)
			{
				Map<String, Object> mp= new HashMap<String, Object>();
				mp.put("username", name);
				mp.put("login_count", 1);
				mp.put("user_id", ureg.getId());

				rt.opsForHash().putAll("client:"+ureg.getId()+":"+ip, mp);
				rt.expire("client:"+ureg.getId()+":"+ip, 10, TimeUnit.MINUTES);
				
				filterChain.doFilter(request, response);
				return;
				
			}
			else
			{
				
				long count=rt.opsForHash().increment("client:"+ureg.getId()+":"+ip,"login_count", 1 );
				
				if((count>5))
				{
					response.setStatus(429);
					response.getWriter().write("Too many login attempts. Please try again later.");
					return;
				}
				else 
				{
					rt.expire("client:"+ureg.getId()+":"+ip, 10, TimeUnit.MINUTES);
					filterChain.doFilter(request, response);
					return;
				}
				
			}


		}
		else
		{
			filterChain.doFilter(request, response);
			return;
		}
	}


}
