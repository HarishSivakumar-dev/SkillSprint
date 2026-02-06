package com.harish.quizapp.DataRepos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.enums.CourseStatus;

@Repository
public interface CoursesRepo extends JpaRepository<CourseDetails,Integer>
{
	List<CourseDetails> findByInstructor(InstructorProfile ur);
	
	Optional<CourseDetails> findByTitleAndInstructor(String title, InstructorProfile id);
	
	List<CourseDetails> findByStatus(CourseStatus status);
	
	int countByStatus(CourseStatus st);
	
	int countByInstructorAndStatus(InstructorProfile ur,CourseStatus status);
	
	int countByInstructor(InstructorProfile ur);
	
	List<CourseDetails> findTop3ByInstructor_IdAndStatusOrderByCreatedAtDesc(int instructorId, CourseStatus status);

	@Query(value="SELECT DISTINCT catagory FROM course_details", nativeQuery=true)
	List<String> findAllCourseGenreAvailable();
	
	
}
