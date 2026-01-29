package com.harish.quizapp.DataRepos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.AdminApplication;
import com.harish.quizapp.enums.PromotionStatus;

@Repository
public interface AdminPromotionRepo extends JpaRepository<AdminApplication, Integer>
{
	List<AdminApplication> findByPromotionStatus(PromotionStatus ps);
	
	Optional<AdminApplication> findByUser_Id(int id);
	
	int countByPromotionStatus(PromotionStatus ps);
	
	@Query(value="SELECT COUNT(u) FROM AdminApplication u WHERE u.reviewedOn>= :start AND u.reviewedOn< :end AND u.adminManager.id=:id")
	int countByWindow(@Param(value="start") LocalDateTime start,@Param(value="end") LocalDateTime end, @Param(value="id") int id);
	
	@Query(value="SELECT COUNT(u) FROM AdminApplication u WHERE u.appliedDate<= :today")
	int countByOldApplications(@Param(value="today") LocalDateTime today);
	
}
