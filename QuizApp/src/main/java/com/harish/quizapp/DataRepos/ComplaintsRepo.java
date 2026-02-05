package com.harish.quizapp.DataRepos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.ComplaintsTable;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.enums.ComplaintStatus;

@Repository
public interface ComplaintsRepo extends JpaRepository<ComplaintsTable, Integer>
{
	List<ComplaintsTable> findByUser(UserRegistration ur);

	List<ComplaintsTable> findByStatus(ComplaintStatus cs);
	
	int countByCreatedAtAndStatus(LocalDate date, ComplaintStatus cs);
	
	@Query("SELECT COUNT(u) FROM ComplaintsTable u WHERE createdAt<= :date AND status= :cs")
	int countByOldRecords(@Param(value="date") LocalDate date, @Param(value="cs") ComplaintStatus cs);
	
	List<ComplaintsTable> findByInstructor_IdAndStatus(int id, ComplaintStatus st);
	
	Optional<ComplaintsTable> findByUser_IdAndInstructor_IdAndCourse_IdAndStatus(int uid, int instid, int cid, ComplaintStatus cs);
	
}
