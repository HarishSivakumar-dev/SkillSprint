package com.harish.quizapp.DataRepos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.SkillApproval;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.enums.SkillStatus;

@Repository
public interface SkillApprovalRepo extends JpaRepository<SkillApproval, Integer>
{
	Optional<List<SkillApproval>> findByInstructor(UserRegistration user);
	
	int countByAdmin_Id(int id);
	
	int countByStatusAndDate(SkillStatus ss, LocalDate date );
	
	@Query("SELECT COUNT(u) FROM SkillApproval u WHERE date< :date")
	int countByOldSkillApproval(@Param(value="date") LocalDate date);
	
	int countByAdmin_IdAndDate(int id, LocalDate date);
}
