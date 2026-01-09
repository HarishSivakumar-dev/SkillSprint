package com.harish.quizapp.DataRepos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.EnrollmentData;
import com.harish.quizapp.Model.InstructorProfile;
import com.harish.quizapp.Model.UserRegistration;


@Repository
public interface EnrollmentRepo extends JpaRepository<EnrollmentData,Integer>
{

	Optional<EnrollmentData> findByUserAndCourse_Id(UserRegistration user, CourseDetails course);

	List<EnrollmentData> findByUser(UserRegistration usr);
	
	int countByUser(UserRegistration usr);
	
	int countDistinctUserByCourse_Instructor(InstructorProfile ip);
	
	int countByCourse_Instructor(InstructorProfile ip);

	int deleteByUser_IdAndCourse_Id(int id, int courseid);
	
	Optional<EnrollmentData> findByUser_IdAndCourse_Id(int id, int courseid);

	List<EnrollmentData> findByCourse_Id(int id);
	
}
