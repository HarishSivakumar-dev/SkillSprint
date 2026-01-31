package com.harish.quizapp.DataRepos;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.harish.quizapp.Model.ComplaintAuditTable;

@Repository
public interface ComplaintAuditRepo extends JpaRepository<ComplaintAuditTable, Integer>
{
	int countByAdmin_Id(int id);
	
	@Query("SELECT COUNT(u) FROM ComplaintAuditTable u WHERE time>= :timestart AND time< :timeend AND u.admin.id= :adminid")
	int countByAdminTasksToday(@Param(value="timestart") LocalDateTime timeStart, @Param(value="timeend") LocalDateTime timeEnd, @Param(value="adminid") int id);
}
