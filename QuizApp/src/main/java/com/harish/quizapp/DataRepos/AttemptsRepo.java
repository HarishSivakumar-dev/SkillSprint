package com.harish.quizapp.DataRepos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.Quiz;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.Model.attemptsTable;

@Repository
@Component
public interface AttemptsRepo extends JpaRepository<attemptsTable,Integer>
{

	attemptsTable findByUserAndCourseAndQuiz(UserRegistration user, CourseDetails cd, Quiz quiz);
	
	int countByUser(UserRegistration user);
	
	List<attemptsTable> findByCourse(CourseDetails course);
	
	int countByUserAndStatus(UserRegistration user, String status);
	
}
