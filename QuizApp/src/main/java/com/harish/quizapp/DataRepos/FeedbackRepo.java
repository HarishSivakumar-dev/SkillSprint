package com.harish.quizapp.DataRepos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.FeedbackTable;
import com.harish.quizapp.Model.UserRegistration;

@Repository
public interface FeedbackRepo extends JpaRepository<FeedbackTable, Integer>
{
	int countByInstructor(UserRegistration ur);
	List<FeedbackTable> findByInstructor(UserRegistration ur);
	
}
