package com.harish.quizapp.DataRepos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.InstructorProfile;

import jakarta.persistence.LockModeType;

@Repository
public interface InstructorRepo extends JpaRepository<InstructorProfile, Integer>
{
	Optional<InstructorProfile> findByUserName_UserName(String username);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT p FROM InstructorProfile p WHERE p.userName.userName= :name")
	Optional<InstructorProfile> findByUserNameLock(@Param(value="name") String username);
}
