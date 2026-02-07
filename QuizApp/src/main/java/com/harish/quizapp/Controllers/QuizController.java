package com.harish.quizapp.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.quizapp.Dto.AllQuizDto;
import com.harish.quizapp.Dto.QuizDto;
import com.harish.quizapp.Dto.ResultDto;
import com.harish.quizapp.Dto.ScoresDto;
import com.harish.quizapp.Dto.UserQuizDto;
import com.harish.quizapp.Model.QuestionsWrapper;
import com.harish.quizapp.Service.QuizService;

@RestController
@RequestMapping("/quiz")
public class QuizController
{
	
	@Autowired
	private QuizService qs;
	
	@PostMapping("/createQuiz/{courseid}")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<String> createQuiz(@RequestBody QuizDto dto, @PathVariable int courseid)
	{
		return qs.createQuiz(dto,courseid);
	}
	
	@GetMapping("courses/{courseid}/getQuiz/{quizname}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR','USER','ADMIN')")
	public ResponseEntity<List<QuestionsWrapper>> getQuizQuestions(@PathVariable int courseid, @PathVariable String quizname)
	{
		return qs.getQuestionsforQuiz(quizname,courseid);
	}
	
	@DeleteMapping("/courses/quizzes/quiz/{quizid}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
	public ResponseEntity<String> deleteQuizzesforCourse(@PathVariable int quizid)
	{
		return qs.deleteQuiz(quizid);
	}
	
	@GetMapping("/courses/get/quizzes/{courseid}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
	public ResponseEntity<List<AllQuizDto>> getQuizforCourse(@PathVariable int courseid)
	{
		return qs.getQuizzesforCourse(courseid);
	}
	
	@GetMapping("/user/courses/quizzes/{courseid}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<UserQuizDto>> getQuizForCoursesUser(@PathVariable int courseid)
	{
		return qs.getUserQuizzesforCourse(courseid);
	}
	
	@PostMapping("{quizname}/course/{courseid}/result")
	@PreAuthorize("hasAnyRole('INSTRUCTOR','USER')")
	public ResponseEntity<ResultDto> getResult(@RequestBody List<ScoresDto> scores, @PathVariable String quizname, @PathVariable int courseid) throws Exception
	{
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		return qs.getScore(name,quizname, scores, courseid);
	}
	
	@PostMapping("/create/finalquiz/{courseid}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
	public ResponseEntity<String> createFinalQuiz(@RequestBody QuizDto dto, @PathVariable int courseid)
	{
		return qs.createQuiz(dto, courseid);
	}
	
	@PostMapping("{quizname}/course/{courseid}/result/final")
	@PreAuthorize("hasAnyRole('INSTRUCTOR','USER')")
	public ResponseEntity<ResultDto> getScoresFinal( @RequestBody List<ScoresDto> scores, @PathVariable String quizname, @PathVariable int courseid) throws Exception
	{
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		return qs.getScore(name,quizname, scores, courseid);
	}
}
