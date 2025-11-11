package com.harish.quizapp.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.AttemptsRepo;
import com.harish.quizapp.DataRepos.CourseCompletionRepo;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.QuestionRepo;
import com.harish.quizapp.DataRepos.QuizQuestionsRepo;
import com.harish.quizapp.DataRepos.QuizRepo;
import com.harish.quizapp.DataRepos.StreakRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Dto.ExistingQuestionsDto;
import com.harish.quizapp.Dto.NewQuestionsDto;
import com.harish.quizapp.Dto.QuizDto;
import com.harish.quizapp.Dto.ResultDto;
import com.harish.quizapp.Dto.ScoresDto;
import com.harish.quizapp.Model.CourseCompletionStatus;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.Questions;
import com.harish.quizapp.Model.QuestionsWrapper;
import com.harish.quizapp.Model.Quiz;
import com.harish.quizapp.Model.Quiz_Questions;
import com.harish.quizapp.Model.StreakLogs;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.Model.attemptsTable;
import com.harish.quizapp.enums.CompletionStatus;


@Service
@Component
public class QuizService
{

	@Autowired
	private QuestionRepo qr;
	@Autowired
	private QuizRepo quizrepo;
	@Autowired 
	private CoursesRepo courses;
	@Autowired
	private UserRepo usr;
	@Autowired 
	private QuizQuestionsRepo bridge;
	@Autowired
	private AttemptsRepo attempts;
	@Autowired
	private CourseCompletionRepo completion;
	@Autowired
	private StreakRepo str;
	
	
	public ResponseEntity<String> deleteQuiz(int quizid)
	{
		
		quizrepo.deleteById(quizid);
		//the logic is still being cooked by the chef harish.
		return ResponseEntity.status(HttpStatus.OK).body("DELETED");
	}
	
	public ResponseEntity<List<Quiz>> getQuizzesforCourse(int courseid)
	{
		CourseDetails cd= courses.findById(courseid).orElseThrow();
		List<Quiz> quiz= quizrepo.findByCourse(cd);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(quiz);
		
	}
	
	public ResponseEntity<List<QuestionsWrapper>> getQuestionsforQuiz(String quizname)
	{
		Quiz ques=quizrepo.findByTitle(quizname).orElseThrow();
		
		List<Quiz_Questions> quiz= bridge.findByQuiz_Id(ques.getId());
		
		List<Questions> allques=new ArrayList<>();
		
		for(Quiz_Questions qz : quiz)
		{
			allques.add(qz.getQuestions());
		}
		
		List<QuestionsWrapper> qw=new ArrayList<>();
	
		for(Questions q : allques)
		{
			QuestionsWrapper qz=new QuestionsWrapper(q.getQuestion(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4(),q.getOption5());
			qw.add(qz);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(qw);
	}
	public ResponseEntity<ResultDto> getScore(String name,String quizname, List<ScoresDto> ls)
	{
		UserRegistration user = usr.findByUserName(name).orElseThrow();
		Quiz ques=quizrepo.findByTitle(quizname).orElseThrow();
		
		StreakLogs streak= new StreakLogs();
		streak.setDate(LocalDate.now());
		streak.setQuiz(ques);
		streak.setUser(user);

		str.save(streak);
		
		
		List<Quiz_Questions> quiz= bridge.findByQuiz_Id(ques.getId());
		
		int totalmarks=0;
		List<Questions>abq=new ArrayList<>();
		
		for(Quiz_Questions qq : quiz)
		{
			abq.add(qq.getQuestions());
			totalmarks=totalmarks+qq.getMarks();
		}
		
		int i=0;
		int right=0;
		for(Questions q : abq)
		{
			if(q.getRightans().equals(ls.get(i).getUseroption()))
			{
				right=right+quiz.get(i).getMarks();
			}
			i++;
		}
		
		attemptsTable at= attempts.findByUserAndCourseAndQuiz(user,ques.getCourse(),ques);
		at.setAttemptcount(at.getAttemptcount()+1);
		CourseDetails det=ques.getCourse();
		
		if(right>=(totalmarks/2))
		{
			
			if(ques.getIsFinal())
			{
				CourseCompletionStatus st=new CourseCompletionStatus();
				st.setUser(user);
				st.setCourse(det);
				st.setCourseCompletionStatus(CompletionStatus.CompletedAndCertified);
				
				completion.save(st);
			}
			
			at.setStatus("PASSED");
			attempts.save(at);
			
			List<Quiz> all=quizrepo.findByCourseOrderByIdAsc(det);
			
			int eligible=0;
			for(Quiz qz : all)
			{
				if(qz.getId()==ques.getId())
				{
					eligible=all.indexOf(qz)+1;
					break;
				}
			}
			
			ResultDto res= new ResultDto();
			
			if(eligible<all.size())
			{
				int quizid= all.get(eligible).getId();
				
				res.setNextquizid(quizid);
				res.setStatus("PASSED");
				res.setScore(right);
			}
			else
			{
				res.setNextquizid(-1);
				res.setScore(right);
				res.setStatus("OPEN FINAL QUIZ");
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
		else
		{
			if(ques.getIsFinal())
			{
				CourseCompletionStatus st=new CourseCompletionStatus();
				st.setUser(user);
				st.setCourse(det);
				st.setCourseCompletionStatus(CompletionStatus.Completed);
				
				completion.save(st);
			}
			
			at.setStatus("FAILED");
			
			attempts.save(at);
			
			ResultDto res= new ResultDto();
			
			res.setStatus("FAILED");
			res.setScore(right);
			
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
				
	}
	public ResponseEntity<String> createQuiz(QuizDto dto, int courseid)
	{
			List<Questions> qnew=new ArrayList<Questions>();
			List<Integer> ids=new ArrayList<Integer>();
			List<Questions> tempList=new ArrayList<Questions>();
			
			CourseDetails course= courses.findById(courseid).orElseThrow();
			
			if(!dto.getQuestions().isEmpty() && dto.getQuestions()!=null)
			{
				for(NewQuestionsDto nw : dto.getQuestions())
				{
					Questions qa=new Questions(nw.getDifficuty(),nw.getCatagory(),nw.getQuestion(),nw.getOption1(),nw.getOption2(),nw.getOption3(),nw.getOption4(),nw.getOption5(),nw.getRightans(),course);
					tempList.add(qa);
				}
				qnew.addAll(qr.saveAll(tempList));
			}
			
			for(ExistingQuestionsDto ex : dto.getQuestionid())
			{
				ids.add(ex.getQuestionId());
			}
			
			List<Questions> qold= qr.findAllById(ids);
			
			Quiz qes=new Quiz();
			
			qes.setTitle(dto.getTitle());
			qes.setCourse(course);
			qes.setTopicid(dto.getTopicid());
			qes.setInstructor(course.getInstructor());
			
			if(dto.getIsFinal())
			{
				if(quizrepo.existsByCourseAndIsfinalTrue(course))
				{
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Final Quiz Already Exists");
				}
				else
				{
					qes.setIsFinal(true);
				}
			}
			else
			{
				qes.setIsFinal(false);
			}
			
			Quiz q=quizrepo.save(qes);
			
			List<attemptsTable> users= attempts.findByCourse(course);
			List<attemptsTable> newQuizzes= new ArrayList<attemptsTable>();
			
			for(attemptsTable at : users)
			{
				attemptsTable rec= new attemptsTable();
				rec.setAttemptcount(0);
				rec.setCourse(course);
				rec.setQuiz(q);
				rec.setStatus("NOT_COMPLETED");
				rec.setUser(at.getUser());
				
				newQuizzes.add(rec);
			}
			attempts.saveAll(newQuizzes);
			
			List<Quiz_Questions> bridgeval=new ArrayList<Quiz_Questions>();
			
			int i=0;
			for(ExistingQuestionsDto qu : dto.getQuestionid())
			{
				Quiz_Questions qs=new Quiz_Questions();
				qs.setQuiz(q);
				qs.setQuestions(qold.get(i));
				qs.setMarks(qu.getMarks());
				bridgeval.add(qs);
				i++;
			}
			
			if(!dto.getQuestions().isEmpty() && dto.getQuestions()!=null)
			{
				i=0;
				for(NewQuestionsDto newdata : dto.getQuestions())
				{
					Quiz_Questions qs=new Quiz_Questions();
					qs.setQuiz(q);
					qs.setQuestions(qnew.get(i));
					qs.setMarks(newdata.getMarks());
					bridgeval.add(qs);
					i++;
				}
			}        
			bridge.saveAll(bridgeval);
			return ResponseEntity.status(HttpStatus.CREATED).body("Quiz Added");
	}
}
