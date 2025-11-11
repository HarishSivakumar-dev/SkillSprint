package com.harish.quizapp.Util;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import com.harish.quizapp.Model.Roles;
import com.harish.quizapp.Model.UserRegistration;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil
{
	
	
	String accesstokenkey="iuiouo*7(**64%^$^HFJgkuyiukNNBCx%^$^&*))*(JOJKkvhcgfeazh}{][)*)&(*^&*%64";
	String refreshtokenkey="poplmwndkj&^&*%#$#)((**(OJmbbgcvxewecgck(*)(890Ijmnnvxr@%$^%*&^897895878(}{}?><,**^&%";
	SecretKey access_key=Keys.hmacShaKeyFor(accesstokenkey.getBytes());
	SecretKey refresh_key=Keys.hmacShaKeyFor(refreshtokenkey.getBytes());

	@SuppressWarnings("deprecation")
	public String generateToken(String name)
	{		
		UserRegistration ur=new UserRegistration();
		List<Roles>roles=ur.getRoles();
		
		List<String> role=roles.stream()
							   .map(rol->rol.getRolename())
							   .toList();
		
		Instant is=Instant.now();

		return Jwts.builder()
				   .setSubject(name)
				   .claim("roles",role)
				   .setIssuedAt(Date.from(is))
				   .signWith(access_key, SignatureAlgorithm.HS256)
				   .setExpiration(Date.from(is.plusSeconds(300)))
				   .compact();
	}

	@SuppressWarnings("deprecation")
	public String generateRefresh(String name, String pass)
	{
		
		UserRegistration ur=new UserRegistration();
		List<Roles> rl=ur.getRoles();
		
		List<String> roles=rl.stream()
							 .map(role->role.getRolename())
							 .toList();
		
		Instant is=Instant.now();

		return Jwts.builder()
				   .setSubject(name)
				   .claim("roles", roles)
				   .setIssuedAt(Date.from(is))
				   .signWith(refresh_key, SignatureAlgorithm.HS256)
				   .setExpiration(Date.from(is.plusSeconds(604800)))
				   .compact();
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<String> rolesAccess(String token)
	{
		return (List<String>) Jwts.parser().setSigningKey(access_key).build().parseClaimsJws(token).getBody().get("roles");
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<String> rolesRefresh(String token)
	{
		return (List<String>) Jwts.parser().setSigningKey(refresh_key).build().parseClaimsJws(token).getBody().get("roles");
	}
	@SuppressWarnings("deprecation")
	public String clientAccess(String token)
	{
		return Jwts.parser().setSigningKey(access_key).build().parseClaimsJws(token).getBody().getSubject();
	}
	@SuppressWarnings("deprecation")
	public String clientRefresh(String token)
	{
		return Jwts.parser().setSigningKey(refresh_key).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	@SuppressWarnings("deprecation")
	public boolean validateAccess(String token)
	{
		try
		{
			Jwts.parser().setSigningKey(access_key).build().parseClaimsJws(token);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	@SuppressWarnings("deprecation")
	public boolean validateRefresh(String token)
	{
		try
		{
			Jwts.parser().setSigningKey(refresh_key).build().parseClaimsJws(token);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
}
