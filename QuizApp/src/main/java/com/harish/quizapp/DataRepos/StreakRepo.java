package com.harish.quizapp.DataRepos;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.harish.quizapp.Model.StreakLogs;


public interface StreakRepo extends JpaRepository<StreakLogs, Integer>
{
	@Query(value="SELECT MAX(date) FROM streak_logs WHERE user_id =:user", nativeQuery=true)
	LocalDate findByLastActivityDate(@Param(value = "user") int userid);
}
