package com.harish.quizapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.UserProfile;


@Configuration
public class RedisConfig
{
	@Bean(value="redisTemplate")
	RedisTemplate<String, UserProfile> redisTemplate(RedisConnectionFactory redisconnectionfactory)
	{
		ObjectMapper om= new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		
		Jackson2JsonRedisSerializer<UserProfile> seri= new Jackson2JsonRedisSerializer<>(om,UserProfile.class);
		
		RedisTemplate<String, UserProfile> rt= new RedisTemplate<String, UserProfile>();
		rt.setKeySerializer(RedisSerializer.string());
		rt.setValueSerializer(seri);
		rt.setConnectionFactory(redisconnectionfactory);
		rt.setHashKeySerializer(RedisSerializer.string());
		rt.setHashValueSerializer(seri);
		
		return rt;
	}
	
	@Bean(value="Instructor_Template")
	RedisTemplate<String, InstructorProfile> redisTemplateInstructor(RedisConnectionFactory rcf)
	{
		ObjectMapper om= new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		
		Jackson2JsonRedisSerializer<InstructorProfile> jj= new Jackson2JsonRedisSerializer<>(om,InstructorProfile.class);
		
		RedisTemplate<String, InstructorProfile> rt= new RedisTemplate<String, InstructorProfile>();
		rt.setKeySerializer(RedisSerializer.string());
		rt.setValueSerializer(jj);
		rt.setHashKeySerializer(RedisSerializer.string());
		rt.setHashValueSerializer(jj);
		rt.setConnectionFactory(rcf);
		
		return rt;
	}

}
