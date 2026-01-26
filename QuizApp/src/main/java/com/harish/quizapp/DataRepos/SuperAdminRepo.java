package com.harish.quizapp.DataRepos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harish.quizapp.Model.SuperAdminAnalytics;

public interface SuperAdminRepo extends JpaRepository<SuperAdminAnalytics, Integer>
{

}
