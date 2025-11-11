package com.harish.quizapp.DataRepos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harish.quizapp.Model.ComplaintsTable;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.enums.ComplaintStatus;

public interface ComplaintsRepo extends JpaRepository<ComplaintsTable, Integer>
{
	List<ComplaintsTable> findByUser(UserRegistration ur);

	List<ComplaintsTable> findByStatus(ComplaintStatus cs);
}
