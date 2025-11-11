package com.harish.quizapp.DataRepos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harish.quizapp.Model.MaterialsDto;


@Repository
public interface MaterialsRepo extends JpaRepository<MaterialsDto, Integer>
{

		List<MaterialsDto> findByContent_Id(int topicid);
}
