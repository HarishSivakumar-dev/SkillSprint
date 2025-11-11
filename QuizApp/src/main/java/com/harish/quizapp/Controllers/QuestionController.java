package com.harish.quizapp.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.quizapp.Model.Questions;
import com.harish.quizapp.Service.QuestionService;

@RestController
@RequestMapping("/questions")
public class QuestionController
{
	
	@Autowired
	private QuestionService questionservice;
	
	@GetMapping("/getall")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Questions>> getallquestions()
	{
		return questionservice.getAllQuestions();
	}
	@PostMapping("/add/{courseid}")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<String> addQuestions(@RequestBody List<Questions> questions, @PathVariable int courseid)
	{
		return questionservice.addQuestions(questions,courseid);
	}
	@GetMapping("/get/instructor/{courseid}")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<List<Questions>> getQuestionsforInstructor(@PathVariable int courseid)
	{
		return questionservice.getQuestionsforInstructor(courseid);
	}
	@GetMapping("/getQuestions/{catagory}/{numQ}")
	public ResponseEntity<List<Questions>> getQuestionsForQuiz(@PathVariable String catagory,@PathVariable int numQ)
	{
		return questionservice.getQuestionsforQuiz(catagory,numQ);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteQuestions(@PathVariable int id)
	{
		return questionservice.deleteQuestions(id);
	}

}
