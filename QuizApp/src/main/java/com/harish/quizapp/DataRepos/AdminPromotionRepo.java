package com.harish.quizapp.DataRepos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harish.quizapp.Model.AdminApplication;
import com.harish.quizapp.enums.PromotionStatus;

@Repository
public interface AdminPromotionRepo extends JpaRepository<AdminApplication, Integer>
{
	List<AdminApplication> findByPromotionStatus(PromotionStatus ps);
	
	Optional<AdminApplication> findByUser_Id(int id);
	
	int countByPromotionStatus(PromotionStatus ps);
	
}
