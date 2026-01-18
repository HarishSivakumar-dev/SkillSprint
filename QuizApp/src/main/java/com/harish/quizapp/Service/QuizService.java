package com.harish.quizapp.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.AttemptsRepo;
import com.harish.quizapp.DataRepos.CourseCompletionRepo;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.InstStatRepo;
import com.harish.quizapp.DataRepos.QuestionRepo;
import com.harish.quizapp.DataRepos.QuizQuestionsRepo;
import com.harish.quizapp.DataRepos.QuizRepo;
import com.harish.quizapp.DataRepos.StreakMainRepo;
import com.harish.quizapp.DataRepos.StreakRepo;
import com.harish.quizapp.DataRepos.UserDeltaRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Dto.ExistingQuestionsDto;
import com.harish.quizapp.Dto.NewQuestionsDto;
import com.harish.quizapp.Dto.QuizDto;
import com.harish.quizapp.Dto.ResultDto;
import com.harish.quizapp.Dto.ScoresDto;
import com.harish.quizapp.Model.CourseCompletionStatus;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.InstructorStatUpdate;
import com.harish.quizapp.Model.Questions;
import com.harish.quizapp.Model.QuestionsWrapper;
import com.harish.quizapp.Model.Quiz;
import com.harish.quizapp.Model.Quiz_Questions;
import com.harish.quizapp.Model.StreakLogs;
import com.harish.quizapp.Model.StreakTable;
import com.harish.quizapp.Model.UserProfileDelta;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.Model.attemptsTable;
import com.harish.quizapp.enums.CompletionStatus;
import com.harish.quizapp.enums.StatUpdateEvent;
import com.harish.quizapp.enums.UserDeltaAction;
import com.harish.quizapp.helpers.EnumHelperClass;

import jakarta.transaction.Transactional;


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
	@Autowired
	private StreakMainRepo smr;
	@Autowired
	private InstStatRepo isr;
	@Autowired
	private UserDeltaRepo udr;
	@Autowired
	private EnumHelperClass cls;
	
	
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
	
	@Transactional
	public ResponseEntity<ResultDto> getScore(String name,String quizname, List<ScoresDto> ls) throws Exception
	{
		UserRegistration user = usr.findByUserName(name).orElseThrow();
		Quiz ques=quizrepo.findByTitle(quizname).orElseThrow();
		List<UserProfileDelta> delsave= new ArrayList<>();
	
		int streak=this.StreakLogicForUser(user, ques);
		
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
		Optional<CourseCompletionStatus> ccs= completion.findByUserAndCourse(user,ques.getCourse());
		
		if((ques.getIsFinal() && at.getAttemptcount()>=1) || !ccs.isEmpty())
		{
			throw new IllegalStateException("Already Attempted !");
		}
		
		UserProfileDelta dl= cls.deltaReturn(UserDeltaAction.QuizAttended,+1,user.getId());
		delsave.add(dl);
		
		at.setAttemptcount(at.getAttemptcount()+1);
		CourseDetails det=ques.getCourse();
		
		InstructorStatUpdate isu= new InstructorStatUpdate();
		CourseCompletionStatus st=new CourseCompletionStatus();
		UserProfileDelta upd= new UserProfileDelta();
		UserProfileDelta upd1= new UserProfileDelta();
		ResultDto res= new ResultDto();
		
		if(right>=(totalmarks/2))
		{
			UserProfileDelta del= cls.deltaReturn(UserDeltaAction.QuizCleared,+1,user.getId());
			delsave.add(del);
			
			at.setStatus("PASSED");
			
			if(ques.getIsFinal() && at.getAttemptcount()==1)
			{

				st.setUser(user);
				st.setCourse(det);
				st.setCourseCompletionStatus(CompletionStatus.CompletedAndCertified);
				
				
				isu.setCreatedAt(LocalDateTime.now());
				isu.setDeltaValue(+1);
				isu.setEventType(StatUpdateEvent.COMPLETION);
				isu.setInstId(det.getInstructor().getId());
				isu.setProceeded(false);
				
				
				upd= cls.deltaReturn(UserDeltaAction.Certificates,+1,user.getId());
				upd1= cls.deltaReturn(UserDeltaAction.Completed,+1,user.getId());
				
				delsave.add(upd);
				delsave.add(upd1);
				
				
				res.setNextquizid(-2);
				res.setScore(right);
				res.setStatus("COMPLETED AND CERTIFIED");
				res.setStreak(streak);
			
			}
			else
			{
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
				
				res.setStreak(streak);
				
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
			}
			
		}
		else
		{
			at.setStatus("FAILED");
			
			if(ques.getIsFinal())
			{
				st.setUser(user);
				st.setCourse(det);
				st.setCourseCompletionStatus(CompletionStatus.Completed);
				
				isu.setCreatedAt(LocalDateTime.now());
				isu.setDeltaValue(+1);
				isu.setEventType(StatUpdateEvent.COMPLETION);
				isu.setInstId(det.getInstructor().getId());
				isu.setProceeded(false);
				
				upd= cls.deltaReturn(UserDeltaAction.Completed,+1, user.getId());
				
				delsave.add(upd);
				
				res.setNextquizid(-2);
				res.setScore(right);
				res.setStatus("COMPLETED");
				res.setStreak(streak);
			
			}
			else
			{
				res.setStreak(streak);
				res.setStatus("FAILED");
				res.setScore(right);
			}
		
		}
		
		attempts.save(at);
		
		if(st!=null)
		{
			completion.save(st);
		}
		
		isr.save(isu);
		udr.saveAll(delsave);
	
		return ResponseEntity.status(HttpStatus.OK).body(res);
				
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
	public int StreakLogicForUser(UserRegistration user, Quiz quiz)
	{
		
		StreakTable st = smr.findByUserId(user);
		
		LocalDate last = str.findByLastActivityDate(user.getId());
		
		if(st.getStreak()==0 && last==null)
		{
				st.setStreak(1);
				st.setUserId(user);
			
				StreakTable tab=smr.save(st);
			
				StreakLogs sl= new StreakLogs();
				sl.setDate(LocalDate.now());
				sl.setQuiz(quiz);
				sl.setUser(user);
			
				this.str.save(sl);
			
				return tab.getStreak();
			
		}
		else if(LocalDate.now().equals(last))
		{
			return smr.findByUserId(user).getStreak();
		}
		else if(last.equals(LocalDate.now().minusDays(1)))
		{
			st.setStreak(st.getStreak()+1);
			StreakTable tab=smr.save(st);
			
			StreakLogs sl= new StreakLogs();
			sl.setDate(LocalDate.now());
			sl.setQuiz(quiz);
			sl.setUser(user);
			
			this.str.save(sl);
			
			return tab.getStreak();
			
		}
		else
		{
			st.setStreak(1);
			StreakTable tb=smr.save(st);
			
			return tb.getStreak();
		}
		
	}
}
