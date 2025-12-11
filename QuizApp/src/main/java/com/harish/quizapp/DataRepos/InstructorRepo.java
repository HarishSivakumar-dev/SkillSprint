package com.harish.quizapp.DataRepos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.InstructorProfile;

@Repository
public interface InstructorRepo extends JpaRepository<InstructorProfile, Integer>
{
	Optional<InstructorProfile> findByUserName_UserName(String username);
}
