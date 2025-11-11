package com.harish.quizapp.DataRepos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.quizapp.Model.InstructorUpdatedTable;

public interface InstructorUpdateRepo extends JpaRepository<InstructorUpdatedTable, Integer>
{

}
