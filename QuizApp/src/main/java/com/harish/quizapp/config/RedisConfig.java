package com.harish.quizapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig
{
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisconnectionfactory)
	{
		RedisTemplate<String, Object> rt= new RedisTemplate<String, Object>();
		rt.setKeySerializer(RedisSerializer.string());
		rt.setValueSerializer(RedisSerializer.json());
		rt.setConnectionFactory(redisconnectionfactory);
		rt.setHashKeySerializer(RedisSerializer.string());
		rt.setHashValueSerializer(RedisSerializer.json());
		
		return rt;
	}

}
