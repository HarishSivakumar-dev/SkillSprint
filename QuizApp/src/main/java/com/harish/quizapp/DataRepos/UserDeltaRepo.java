package com.harish.quizapp.DataRepos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.quizapp.Model.UserProfileDelta;

public interface UserDeltaRepo extends JpaRepository<UserProfileDelta, Integer>
{

}
