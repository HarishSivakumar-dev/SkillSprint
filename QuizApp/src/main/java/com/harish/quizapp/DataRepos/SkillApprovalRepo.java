package com.harish.quizapp.DataRepos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.quizapp.Model.SkillApproval;
import com.harish.quizapp.Model.UserRegistration;

public interface SkillApprovalRepo extends JpaRepository<SkillApproval, Integer>
{
	Optional<List<SkillApproval>> findByInstructor(UserRegistration user);
}
