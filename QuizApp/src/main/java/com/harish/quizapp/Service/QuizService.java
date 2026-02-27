package com.harish.quizapp.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.harish.quizapp.DataRepos.AttemptsRepo;
import com.harish.quizapp.DataRepos.CourseCompletionRepo;
import com.harish.quizapp.DataRepos.CoursesRepo;
import com.harish.quizapp.DataRepos.EnrollmentRepo;
import com.harish.quizapp.DataRepos.InstStatRepo;
import com.harish.quizapp.DataRepos.QuestionRepo;
import com.harish.quizapp.DataRepos.QuizQuestionsRepo;
import com.harish.quizapp.DataRepos.QuizRepo;
import com.harish.quizapp.DataRepos.StreakMainRepo;
import com.harish.quizapp.DataRepos.UserDeltaRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Dto.AllQuizDto;
import com.harish.quizapp.Dto.ExistingQuestionsDto;
import com.harish.quizapp.Dto.NewQuestionsDto;
import com.harish.quizapp.Dto.QuizDto;
import com.harish.quizapp.Dto.ResultDto;
import com.harish.quizapp.Dto.ScoresDto;
import com.harish.quizapp.Dto.UserQuizDto;
import com.harish.quizapp.Model.CourseCompletionStatus;
import com.harish.quizapp.Model.CourseDetails;
import com.harish.quizapp.Model.EnrollmentData;
import com.harish.quizapp.Model.InstructorStatUpdate;
import com.harish.quizapp.Model.Questions;
import com.harish.quizapp.Model.QuestionsWrapper;
import com.harish.quizapp.Model.Quiz;
import com.harish.quizapp.Model.Quiz_Questions;
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
	private StreakMainRepo smr;
	@Autowired
	private InstStatRepo isr;
	@Autowired
	private UserDeltaRepo udr;
	@Autowired
	private EnumHelperClass cls;
	@Autowired
	private EnrollmentRepo er;
	
	
	public ResponseEntity<String> deleteQuiz(int quizid)
	{
		
		quizrepo.deleteById(quizid);
		return ResponseEntity.status(HttpStatus.OK).body("DELETED");
	}
	
	public ResponseEntity<List<AllQuizDto>> getQuizzesforCourse(int courseid)
	{
		CourseDetails cd= courses.findById(courseid).orElseThrow();
		List<Quiz> quiz= quizrepo.findByCourse(cd);
		
		List<AllQuizDto> dto= new ArrayList<>();
		
		for(Quiz qz : quiz)
		{
			AllQuizDto ot= new AllQuizDto();
			ot.setCourseName(qz.getCourse().getTitle());
			ot.setIsfinal(qz.getIsFinal());
			ot.setOrder(qz.getSequenceNumber());
			ot.setTitle(qz.getTitle());
			ot.setTopicId(qz.getTopicid());
			
			dto.add(ot);
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
		
	}
	
	public ResponseEntity<List<UserQuizDto>> getUserQuizzesforCourse(int courseid)
	{
		CourseDetails cd= courses.findById(courseid).orElseThrow();
		List<Quiz> quiz= quizrepo.findByCourse(cd);
		
		
		List<UserQuizDto> dto= new ArrayList<>();
		List<attemptsTable> tb= attempts.findByUser_UserNameAndCourse(SecurityContextHolder.getContext().getAuthentication().getName(), cd);
		
		Map<Quiz, attemptsTable> at= new HashMap<>();
		
		for(attemptsTable tbl : tb)
		{
			at.put(tbl.getQuiz(), tbl);
		}
		
		for(Quiz qz : quiz)
		{
			UserQuizDto ot= new UserQuizDto();
			ot.setCourseName(qz.getCourse().getTitle());
			ot.setIsfinal(qz.getIsFinal());
			ot.setOrder(qz.getSequenceNumber());
			ot.setTitle(qz.getTitle());
			ot.setTopicId(qz.getTopicid());
			ot.setUserStatus(at.get(qz).getStatus());
			
			dto.add(ot);
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
		
	}
	
	
	
	@Transactional
	public ResponseEntity<List<QuestionsWrapper>> getQuestionsforQuiz(String quizname,int courseid)
	{
		Quiz ques=quizrepo.findByTitleAndCourse_Id(quizname,courseid).orElseThrow();
		String user= SecurityContextHolder.getContext().getAuthentication().getName();
		int usrid= usr.findByUserName(user).orElseThrow().getId();
		
		List<QuestionsWrapper> qw=new ArrayList<>();
		
		if(ques.getSequenceNumber()!=1)
		{
			int seq= ques.getSequenceNumber()-1;
			int prvquiz= quizrepo.findBySequenceNumberAndCourse_Id(seq,ques.getCourse().getId()).get().getId();
			
			boolean res= attempts.existsByUser_IdAndQuiz_IdAndStatus(usrid,prvquiz,"PASSED");
			if(res==true)
			{
				qw=this.generateQuestionWrapper(ques.getId());
			}
			else
			{
				return ResponseEntity.status(HttpStatus.LOCKED).body(qw);
			}
		}
		else
		{
			qw=this.generateQuestionWrapper(ques.getId());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(qw);
	}
	
	@Transactional
	public ResponseEntity<ResultDto> getScore(String name,String quizname, List<ScoresDto> ls, int courseid) throws Exception
	{
		UserRegistration user = usr.findByUserName(name).orElseThrow();
		Quiz ques=quizrepo.findByTitleAndCourse_Id(quizname,courseid).get();
		
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
		
		if((ques.getIsFinal() && at.getAttemptcount()>=1) || !ccs.isEmpty() || at.getStatus().equals("PASSED"))
		{
			throw new IllegalStateException("Already Attempted !");
		}
		
		if(!(at.getAttemptcount()>=1))
		{
			UserProfileDelta dl= cls.deltaReturn(UserDeltaAction.QuizAttended,+1,user.getId());
			delsave.add(dl);
		}
		
		at.setAttemptcount(at.getAttemptcount()+1);
		CourseDetails det=ques.getCourse();
		
		InstructorStatUpdate isu= new InstructorStatUpdate();
		CourseCompletionStatus st=new CourseCompletionStatus();
		UserProfileDelta upd= new UserProfileDelta();
		UserProfileDelta upd1= new UserProfileDelta();
		ResultDto res= new ResultDto();
		EnrollmentData dat=er.findByUserAndCourse(user, det).orElseThrow(()->new NoSuchElementException());
		
		if(right>=(totalmarks/2))
		{
			UserProfileDelta del= cls.deltaReturn(UserDeltaAction.QuizCleared,+1,user.getId());
			delsave.add(del);
			
			at.setStatus("PASSED");
			
			if(ques.getIsFinal() && at.getAttemptcount()==1)
			{
				dat.setStatus("Completed");
				
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
				dat.setStatus("Completed");
				
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
		er.save(dat);
	
		return ResponseEntity.status(HttpStatus.OK).body(res);
				
	}
	
	@Transactional
	public ResponseEntity<String> createQuiz(QuizDto dto, int courseid)
	{
			List<Questions> qnew=new ArrayList<Questions>();
			List<Integer> ids=new ArrayList<Integer>();
			List<Questions> tempList=new ArrayList<Questions>();
			List<Questions> qold= new ArrayList<Questions>();
			
			if(quizrepo.findByTitleAndCourse_Id(dto.getTitle(), courseid).isEmpty())
			{
				CourseDetails course= courses.findById(courseid).orElseThrow();
				
				if(dto.getQuestions()!=null  && !dto.getQuestions().isEmpty())
				{
					for(NewQuestionsDto nw : dto.getQuestions())
					{
						Questions qa=new Questions(nw.getDifficuty(),nw.getCatagory(),nw.getQuestion(),nw.getOption1(),nw.getOption2(),nw.getOption3(),nw.getOption4(),nw.getOption5(),nw.getRightans(),course.getInstructor().getId());
						tempList.add(qa);
					}
					qnew.addAll(qr.saveAll(tempList));
				}
				
				if(dto.getQuestionid()!=null && !dto.getQuestionid().isEmpty())
				{
					for(ExistingQuestionsDto ex : dto.getQuestionid())
					{
						ids.add(ex.getQuestionId());
					}
					
					qold= qr.findAllById(ids);
				}
				
				Optional<Quiz> first= quizrepo.findBySequenceNumberAndCourse_Id(1,courseid);
				
				Quiz qes=new Quiz();
				qes.setTitle(dto.getTitle());
				qes.setCourse(course);
				qes.setTopicid(dto.getTopicid());
				qes.setInstructor(course.getInstructor());
				
				if(first.isEmpty())
				{
					qes.setSequenceNumber(1);
				}
				else 
				{
					int seq= quizrepo.findMaxOfSequenceNumber(courseid);
					qes.setSequenceNumber(seq+1);
				}
				
				
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
				
				List<EnrollmentData> users= er.findByCourse_Id(courseid);
				List<attemptsTable> newQuizzes= new ArrayList<attemptsTable>();
				
				for(EnrollmentData at : users)
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
				 if(dto.getQuestionid()!=null && !dto.getQuestionid().isEmpty())
				 {
					 int j=0;
					 for(ExistingQuestionsDto qu : dto.getQuestionid())
						{
							Quiz_Questions qs=new Quiz_Questions();
							qs.setQuiz(q);
							qs.setQuestions(qold.get(j));
							qs.setMarks(qu.getMarks());
							bridgeval.add(qs);
							j++;
						}
				 }			
				 bridge.saveAll(bridgeval);
				 
				return ResponseEntity.status(HttpStatus.CREATED).body("Quiz Added");
			}
			else 
			{
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Title already exists in the added quizzes !");
			}
			
			
	}
	
	public int StreakLogicForUser(UserRegistration user, Quiz quiz)
	{
		
		StreakTable st = smr.findByUserId(user);
		
		LocalDate last = st.getLastQuizDate();
		
		if(st.getStreak()==0 || last==null)
		{
				st.setStreak(1);
				st.setUserId(user);
				st.setLastQuizDate(LocalDate.now());
			
				StreakTable tab=smr.save(st);
			
				return tab.getStreak();
			
		}
		else if(LocalDate.now().equals(last))
		{
			return st.getStreak();
		}
		else if(last.equals(LocalDate.now().minusDays(1)))
		{
			st.setStreak(st.getStreak()+1);
			st.setLastQuizDate(LocalDate.now());
			StreakTable tab=smr.save(st);
			
			return tab.getStreak();
			
		}
		else
		{
			st.setStreak(1);
			st.setLastQuizDate(LocalDate.now());
			StreakTable tb=smr.save(st);
			
			return tb.getStreak();
		}
		
	}
	
	public List<QuestionsWrapper> generateQuestionWrapper(int quesid)
	{
		List<Quiz_Questions> quiz= bridge.findByQuiz_Id(quesid);
		List<QuestionsWrapper> qw= new ArrayList<QuestionsWrapper>();
		List<Questions> allques=new ArrayList<>();
		
		for(Quiz_Questions qz : quiz)
		{
			allques.add(qz.getQuestions());
		}
	
		for(Questions q : allques)
		{
			QuestionsWrapper qz=new QuestionsWrapper(q.getId(),q.getQuestion(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4(),q.getOption5());
			qw.add(qz);
		}
		
		return qw;
	}
	
}
