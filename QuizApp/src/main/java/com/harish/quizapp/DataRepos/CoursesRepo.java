package com.harish.quizapp.DataRepos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.enums.CourseStatus;

@Repository
public interface CoursesRepo extends JpaRepository<CourseDetails,Integer>
{
	List<CourseDetails> findByInstructor(UserRegistration ur);
	
	Optional<CourseDetails> findByTitleAndInstructor(String title, UserRegistration id);
	
	List<CourseDetails> findByStatus(CourseStatus status);
	
	int countByInstructorAndStatus(UserRegistration ur, String status);
	
	int countByInstructor(UserRegistration ur);
	
	List<CourseDetails> findTop3ByInstructor_IdAndStatusOrderByCreated_atDesc(int instructorId, CourseStatus status);

	@Query(value="SELECT DISTINCT catagory FROM CourseDetails", nativeQuery=true)
	List<String> findAllCourseGenreAvailable();
	
	

}
