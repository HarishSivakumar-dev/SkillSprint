package com.harish.quizapp.DataRepos;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.FeedbackTable;
import com.harish.quizapp.Model.InstructorProfile;

@Repository
public interface FeedbackRepo extends JpaRepository<FeedbackTable, Integer>
{
	int countByInstructor(InstructorProfile ur);
	
	List<FeedbackTable> findByInstructor(InstructorProfile ur);
	
	@Query(value="SELECT AVG(rating) from feedback_table WHERE instructor_id=:instid", nativeQuery=true)
	BigDecimal getRatingForInstructor(@Param(value = "instid") int instid);
	
}
