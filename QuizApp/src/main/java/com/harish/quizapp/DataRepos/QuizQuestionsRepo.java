package com.harish.quizapp.DataRepos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harish.quizapp.Model.Quiz_Questions;

@Repository
public interface QuizQuestionsRepo extends JpaRepository<Quiz_Questions, Integer>
{
	List<Quiz_Questions> findByQuiz_Id(int id);
}
