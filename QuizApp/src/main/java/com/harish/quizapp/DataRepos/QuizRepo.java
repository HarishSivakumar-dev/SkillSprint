package com.harish.quizapp.DataRepos;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.Quiz;

@Repository
@Component
public interface QuizRepo extends JpaRepository<Quiz,Integer>
{
	Optional<Quiz> findByTitle(String topic);
	
	List<Quiz> findByCourse(CourseDetails cd);
	
	List<Quiz> findByCourseOrderByIdAsc(CourseDetails cd);
	
	Boolean existsByCourseAndIsfinalTrue(CourseDetails cd);
	
	Optional<Quiz> findBySequenceNumberAndCourse_Id(int num, int id);
	
	@Query("SELECT MAX(u.sequenceNumber) FROM Quiz u WHERE u.course.id= :id")
	int findMaxOfSequenceNumber(@Param(value="id") int id);

	Optional<Quiz> findByTitleAndCourse_Id(String quizname,int id);
}
