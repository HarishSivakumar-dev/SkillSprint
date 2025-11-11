package com.harish.quizapp.DataRepos;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.quizapp.Model.StreakLogs;
import com.harish.quizapp.Model.UserRegistration;

public interface StreakRepo extends JpaRepository<StreakLogs, Integer>
{
	StreakLogs findByUserAndDate(UserRegistration user, LocalDate date);
}
