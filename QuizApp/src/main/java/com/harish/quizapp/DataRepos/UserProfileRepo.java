package com.harish.quizapp.DataRepos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.UserProfile;

@Repository
public interface UserProfileRepo extends JpaRepository<UserProfile, Integer>
{

	Optional<UserProfile> findByUserName(String name);
	
}
