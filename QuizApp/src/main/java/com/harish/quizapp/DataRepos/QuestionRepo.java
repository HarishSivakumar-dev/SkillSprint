package com.harish.quizapp.DataRepos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.Questions;

@Repository
public interface QuestionRepo extends JpaRepository<Questions,Integer>
{

	@Query(value="SELECT*FROM Questions WHERE catagory =:catagory ORDER BY RAND()", nativeQuery=true)
	List<Questions> getQuestionsforQuestions(@Param("catagory") String catagory);
	
	List<Questions> findByCourse_Id(int courseid);

}

