package com.harish.quizapp.DataRepos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.harish.quizapp.Model.InstructorStatUpdate;

public interface InstStatRepo extends JpaRepository<InstructorStatUpdate, Integer>
{
	@Query(value="""
			SELECT i.inst_id AS instId,
			 i.event_type AS eventType, 
			 SUM(i.delta_value) AS totChange
			 FROM instructor_stat_update i
			 WHERE proceeded=false
			 GROUP BY i.inst_id, i.event_type
			 """, nativeQuery=true)
	List<InstructorStatUpdateProjection> findByRecordsForStat();
	
	
	@Query(value="SELECT * FROM instructor_stat_update WHERE processed=false", nativeQuery=true)
	List<InstructorStatUpdate> findallPending();

}
