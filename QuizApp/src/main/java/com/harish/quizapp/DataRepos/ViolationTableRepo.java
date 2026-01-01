package com.harish.quizapp.DataRepos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harish.quizapp.Model.ViolationsTable;

@Repository
public interface ViolationTableRepo extends JpaRepository<ViolationsTable, Integer>
{
	Optional<ViolationsTable> findByInstructor_Id(int id);
	
	List<ViolationsTable> findByViolatedTrue();
	
}
