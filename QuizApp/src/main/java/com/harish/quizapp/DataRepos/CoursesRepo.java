package com.harish.quizapp.DataRepos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.UserRegistration;

@Repository
public interface CoursesRepo extends JpaRepository<CourseDetails,Integer>
{
	List<CourseDetails> findByInstructor(UserRegistration ur);
	
	Optional<CourseDetails> findByTitleAndInstructor(String title, UserRegistration id);
	
	List<CourseDetails> findByStatus(String name);
	
	int countByInstructorAndStatus(UserRegistration ur, String status);

}
