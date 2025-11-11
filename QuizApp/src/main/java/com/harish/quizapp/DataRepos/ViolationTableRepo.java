package com.harish.quizapp.DataRepos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.quizapp.Model.ViolationsTable;

public interface ViolationTableRepo extends JpaRepository<ViolationsTable, Integer>
{
	Optional<ViolationsTable> findByInstructor_Id(int id);
	
}
