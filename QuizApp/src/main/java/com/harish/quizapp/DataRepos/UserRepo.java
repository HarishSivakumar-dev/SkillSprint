package com.harish.quizapp.DataRepos;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.UserRegistration;

@Repository
public interface UserRepo extends JpaRepository<UserRegistration, Integer>
{
	Optional<UserRegistration> findByUserName(String name);
	Optional<UserRegistration> findByEmail(String email);
	
	
	@Query(value="SELECT COUNT(u) from UserRegistration u JOIN u.roles r WHERE r.rolename= : rolename ")
	int countByRoles(@Param(value="rolename") String role);
	
	@Query(value="SELECT COUNT(u) from UserRegistration u WHERE u.joinedDate >=:start AND u.joinedDate< :end")
	int countByMonthlyRegistrations(@Param(value="start") LocalDateTime start, @Param(value="end") LocalDateTime end);

}
