package com.harish.quizapp.DataRepos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harish.quizapp.Model.ComplaintAuditTable;

public interface ComplaintAuditRepo extends JpaRepository<ComplaintAuditTable, Integer>
{
	
}
