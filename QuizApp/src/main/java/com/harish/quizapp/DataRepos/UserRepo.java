package com.harish.quizapp.DataRepos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.UserRegistration;


@Repository
public interface UserRepo extends JpaRepository<UserRegistration,Integer>
{
	Optional<UserRegistration> findByUserName(String name);
}
