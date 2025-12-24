package com.harish.quizapp.DataRepos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harish.quizapp.Model.Skills;


@Repository
public interface SkillsRepo extends JpaRepository<Skills, Integer>
{
	Optional<Skills> findBySkillName(String name);
}
