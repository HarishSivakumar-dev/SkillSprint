package com.harish.quizapp.DataRepos;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.harish.quizapp.Model.InstructorUpdatedTable;

public interface InstructorUpdateRepo extends JpaRepository<InstructorUpdatedTable, Integer>
{
	int countByAdmin_Id(int id);
	
	@Query("SELECT COUNT(u) FROM InstructorUpdatedTable u WHERE time>= :start AND time< :end AND u.admin.id= :adminid")
	int countByTodayActivity(@Param(value="start") LocalDateTime start,@Param(value="end") LocalDateTime end,@Param(value="adminid") int id);
}
