package com.harish.quizapp.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

@Service
public class DashBoardService
{
	@Autowired
	@Qualifier("Dashboard_Template")
	private RedisTemplate<String, String> rt;
	
	public Map<String, Double> getall()
	{
		Set<TypedTuple<String>> dash=rt.opsForZSet().reverseRangeWithScores("LeaderBoard:overall", 0, -1);
		Map<String, Double> map=new HashMap<>();
		
		for(TypedTuple<String> t:dash)
		{
			map.put(t.getValue(), t.getScore());
		}
		
		return map;
		
	}

	public Map<String, Double> getDaily()
	{
		Set<TypedTuple<String>> dash= rt.opsForZSet().reverseRangeWithScores("LeaderBoard:daily", 0, -1);
		Map<String, Double> map=new HashMap<>();
		
		for(TypedTuple<String> t:dash)
		{
			map.put(t.getValue(), t.getScore());
		}
		
		return map;
	}
}
