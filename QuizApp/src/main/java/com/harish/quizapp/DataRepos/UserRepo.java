package com.harish.quizapp.DataRepos;

import java.time.LocalDateTime;
import java.util.List;
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
	
	
	@Query("SELECT COUNT(u) from UserRegistration u JOIN u.roles r WHERE r.rolename= :rolename AND SIZE(u.roles)=1")
	int countByRoles(@Param(value="rolename") String role);
	
	@Query("SELECT u FROM UserRegistration u JOIN u.roles r WHERE r.rolename= :rolename AND NOT EXISTS (SELECT 1 FROM u.roles r2 WHERE r2.rolename IN :excludedRoles)")
	List<UserRegistration> getAllUsersByRole(@Param(value="rolename") String role, @Param(value="excludedRoles") List<String> roles);
	
	@Query("SELECT COUNT(u) from UserRegistration u JOIN u.roles r WHERE u.joinedDate >=:start AND u.joinedDate< :end AND r.rolename= :rolename AND SIZE(u.roles)=1")
	int countByMonthlyRegistrations(@Param(value="start") LocalDateTime start, @Param(value="end") LocalDateTime end, @Param(value="rolename") String rolename );

}
