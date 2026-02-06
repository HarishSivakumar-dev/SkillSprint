package com.harish.quizapp.DataRepos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.UserProfile;
import com.harish.quizapp.Model.UserRegistration;

@Repository
public interface UserProfileRepo extends JpaRepository<UserProfile, Integer>
{

	Optional<UserProfile> findByUserName_UserName(String name);
	
	List<UserProfile> findAllByUserNameIn(List<UserRegistration> rg);
}
