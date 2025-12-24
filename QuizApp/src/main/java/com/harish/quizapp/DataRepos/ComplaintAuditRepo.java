package com.harish.quizapp.DataRepos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harish.quizapp.Model.ComplaintAuditTable;

@Repository
public interface ComplaintAuditRepo extends JpaRepository<ComplaintAuditTable, Integer>
{
	int countByAdmin_Id(int id);
}
