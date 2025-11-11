package com.harish.quizapp.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.QuestionRepo;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.Questions;


@Service
public class QuestionService
{
	@Autowired
	private QuestionRepo qr;
	
	@Autowired
	private CoursesRepo cr;
	
	public ResponseEntity<List<Questions>> getAllQuestions()
	{
		return ResponseEntity.status(HttpStatus.OK).body(qr.findAll());
	}
	public ResponseEntity<String> addQuestions(List<Questions> questions, int courseid)
	{
		CourseDetails det = cr.findById(courseid).orElseThrow();
		
		for(Questions ques : questions)
		{
			ques.setCourse(det);
		}
		
		qr.saveAll(questions);
		return ResponseEntity.status(HttpStatus.CREATED).body("SUCCESS !");
	}
	public ResponseEntity<List<Questions>> getQuestionsforQuiz(String catagory, Integer numQ)
	{
		List<Questions> all=qr.getQuestionsforQuestions(catagory);
		List<Questions> ques=all.subList(0, Math.min(numQ, all.size()));
		
		return ResponseEntity.status(HttpStatus.OK).body(ques);
	}
	public ResponseEntity<String> deleteQuestions(int id)
	{
		qr.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body("DELETED !");
	}
	public ResponseEntity<List<Questions>> getQuestionsforInstructor(int courseid)
	{
		List<Questions> ques=qr.findByCourse_Id(courseid);
		return ResponseEntity.status(HttpStatus.CONTINUE).body(ques);
	}

}
