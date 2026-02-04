package com.harish.quizapp.DataRepos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.CourseCompletionStatus;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.enums.CompletionStatus;

@Repository
public interface CourseCompletionRepo extends JpaRepository<CourseCompletionStatus, Integer>
{
	Optional<CourseCompletionStatus> findByUserAndCourse(UserRegistration user, CourseDetails cd);
	
	int countDistinctUserByCourse_Instructor_Id(int id);
	
	int countByCourse_InstructorAndCourseCompletionStatusIn(InstructorProfile ip, List<CompletionStatus> col);
	
	int countByUser(UserRegistration usr);

	int countByUserAndCourseCompletionStatus(UserRegistration userRegistration, CompletionStatus completedandcertified);
	
}
