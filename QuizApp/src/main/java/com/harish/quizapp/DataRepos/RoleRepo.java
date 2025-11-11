package com.harish.quizapp.DataRepos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harish.quizapp.Model.Roles;


@Repository
public interface RoleRepo extends JpaRepository<Roles,Integer>
{

	Optional<Roles> findByRolename(String name);
	
}
