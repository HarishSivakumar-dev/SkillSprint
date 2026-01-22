package com.harish.quizapp.DataRepos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.harish.quizapp.Model.UserProfileDelta;

public interface UserDeltaRepo extends JpaRepository<UserProfileDelta, Integer>
{
	@Query(value=" SELECT user_id as userId, action as userAction, SUM(delta_value) as totDelta FROM user_profile_delta WHERE is_processed=false GROUP BY user_id,action ", nativeQuery=true)
	List<UserDeltaProjection> findAllPendingDeltas();

	List<UserProfileDelta> findByIsProcessedFalse();
	
}
