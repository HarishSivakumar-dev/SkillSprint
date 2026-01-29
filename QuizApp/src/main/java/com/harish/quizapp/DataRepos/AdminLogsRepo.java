package com.harish.quizapp.DataRepos;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.AdminLogs;

@Repository
public interface AdminLogsRepo extends JpaRepository<AdminLogs, Integer>
{
	@Query("SELECT COUNT(u) FROM AdminLogs u WHERE lastActive= :date")
	int countByActiveAdmins(@Param(value="date") LocalDate date);
	
	Optional<AdminLogs> findByAdminId(int adminid);

}
