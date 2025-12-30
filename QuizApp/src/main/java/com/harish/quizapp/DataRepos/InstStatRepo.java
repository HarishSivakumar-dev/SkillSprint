package com.harish.quizapp.DataRepos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.quizapp.Model.InstructorStatUpdate;

public interface InstStatRepo extends JpaRepository<InstructorStatUpdate, Integer>
{

}
