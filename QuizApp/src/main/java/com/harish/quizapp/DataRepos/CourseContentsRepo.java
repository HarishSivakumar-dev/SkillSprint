package com.harish.quizapp.DataRepos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.harish.quizapp.Model.CourseContents;

@Repository
public interface CourseContentsRepo extends JpaRepository<CourseContents, Integer>
{
	
	@Query(value="SELECT COALESCE(MAX(topicid),0) FROM CourseContents WHERE course_id=:courseid", nativeQuery=true)
	int findByMaxCourseId(@Param(value="courseid") int courseid);
	
	List<CourseContents> findByCourse_Id(int courseid);

}
