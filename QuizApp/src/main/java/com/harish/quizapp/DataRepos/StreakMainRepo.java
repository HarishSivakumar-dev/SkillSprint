package com.harish.quizapp.DataRepos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.quizapp.Model.StreakTable;
import com.harish.quizapp.Model.UserRegistration;

public interface StreakMainRepo extends JpaRepository<StreakTable, Integer>
{
	StreakTable findByUserId(UserRegistration user);

}
