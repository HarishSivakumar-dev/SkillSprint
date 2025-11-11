package com.harish.quizapp.DataRepos;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.CourseCompletionStatus;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.enums.CompletionStatus;

@Repository
public interface CourseCompletionRepo extends JpaRepository<CourseCompletionStatus, Integer>
{
	
	List<CourseCompletionStatus> findByCourse_Instructor_Id(int id);
	
	int countByUser(UserRegistration usr);

	int countByUserAndCourseCompletionStatus(UserRegistration userRegistration, CompletionStatus completedandcertified);
	
}
