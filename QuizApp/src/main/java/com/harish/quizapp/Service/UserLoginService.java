package com.harish.quizapp.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Dto.ForgotPasswordDto;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.Util.JwtUtil;
import com.harish.quizapp.asyncFunctionCalls.UserLoginAsync;

@Service
public class UserLoginService
{
	@Autowired
	private JwtUtil jwt;
	@Autowired 
	private AuthenticationManager am;
	@Autowired
	private UserLoginAsync ula;
	@Autowired
	private UserRepo ur;
	@Autowired
	private RedisTemplate<String, String> redisTemp;

	public ResponseEntity<String> loginAuthenticationService(UserRegistration rd)
	{
		
		String name=rd.getUserName();
		String pass=rd.getPassword();
		
		am.authenticate(new UsernamePasswordAuthenticationToken(name,pass));
		
		Object lastlogin= redisTemp.opsForHash().get("client:"+name, "lastlogin");
		LocalDateTime lastLoginTime= LocalDateTime.now();
		
		if(lastlogin==null)
		{
			UserRegistration usr= ur.findByUserName(name).orElseThrow();
			usr.setLastLogin(lastLoginTime);
			ur.save(usr);
			
			
			Map<String, String> clientData= new HashMap<>();
			clientData.put("userid", String.valueOf(usr.getId()));
			clientData.put("lastlogin", lastLoginTime.toString());
			
			redisTemp.opsForHash().putAll("client:"+name,clientData);
			redisTemp.expire("client:"+name, 1, TimeUnit.DAYS);
		}
		else
		{
			redisTemp.opsForHash().put("client:"+name, "lastlogin", lastLoginTime.toString());
			ula.loginUser(name, lastLoginTime);
		}
		
		
		String access=jwt.generateToken(name);
		String refresh=jwt.generateRefresh(name,pass);
		
		return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Access Token:    "+ access +"Refresh Token:   "+ refresh );
		
	}
	
	public ResponseEntity<String> forgotPassword(ForgotPasswordDto fp)
	{
		BCryptPasswordEncoder enc=new BCryptPasswordEncoder(10);
		
		UserRegistration usr= ur.findByUserName(fp.getUserName()).orElseThrow();
		usr.setPassword(enc.encode(fp.getNewPassword()));
		ur.save(usr);
		
		return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Password Updated Successfully");
		
	}
}
