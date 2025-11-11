package com.harish.quizapp.DataRepos;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
