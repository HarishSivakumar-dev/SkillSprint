package com.harish.quizapp.DataRepos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.harish.quizapp.Model.StreakLogs;


public interface StreakRepo extends JpaRepository<StreakLogs, Integer>
{
	@Query(value="SELECT MAX(date) WHERE user_id =:user", nativeQuery=true)
	StreakLogs findByLastActivityDate(@Param(value = "user") int userid);
}
