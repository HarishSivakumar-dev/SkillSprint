package com.harish.quizapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.harish.quizapp.Model.UserProfile;

@Configuration
public class RedisConfig
{
	@Bean
	RedisTemplate<String, UserProfile> redisTemplate(RedisConnectionFactory redisconnectionfactory)
	{
		ObjectMapper om= new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		
		Jackson2JsonRedisSerializer<UserProfile> seri= new Jackson2JsonRedisSerializer<>(UserProfile.class);
		seri.setObjectMapper(om);
		
		RedisTemplate<String, UserProfile> rt= new RedisTemplate<String, UserProfile>();
		rt.setKeySerializer(RedisSerializer.string());
		rt.setValueSerializer(seri);
		rt.setConnectionFactory(redisconnectionfactory);
		rt.setHashKeySerializer(RedisSerializer.string());
		rt.setHashValueSerializer(seri);
		
		return rt;
	}

}
