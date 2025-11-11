package com.harish.quizapp.DataRepos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.quizapp.Model.InstructorApplication;
import com.harish.quizapp.Model.UserRegistration;

public interface InstAppRepo extends JpaRepository<InstructorApplication, Integer>
{
	Optional<InstructorApplication> findByUser(UserRegistration ar);
	
	List<InstructorApplication> findByIsPending(Boolean val);
	
}
