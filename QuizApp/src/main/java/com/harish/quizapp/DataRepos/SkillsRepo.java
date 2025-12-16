package com.harish.quizapp.DataRepos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.quizapp.Model.Skills;

public interface SkillsRepo extends JpaRepository<Skills, Integer>
{

	Skills findBySkillName(String name);
}
