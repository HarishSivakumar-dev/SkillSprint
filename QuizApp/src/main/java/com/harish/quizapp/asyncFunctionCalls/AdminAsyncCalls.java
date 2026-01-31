package com.harish.quizapp.asyncFunctionCalls;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.harish.quizapp.DataRepos.ComplaintAuditRepo;
import com.harish.quizapp.DataRepos.ComplaintsRepo;
import com.harish.quizapp.DataRepos.InstAppRepo;
import com.harish.quizapp.DataRepos.InstructorUpdateRepo;
import com.harish.quizapp.DataRepos.SkillApprovalRepo;
import com.harish.quizapp.DataRepos.UserRepo;
import com.harish.quizapp.Model.UserRegistration;
import com.harish.quizapp.enums.ComplaintStatus;
import com.harish.quizapp.enums.SkillStatus;

@Component
public class AdminAsyncCalls 
{
	@Autowired
	@Qualifier("asyncExecutor")
	private Executor exe;
	
	@Autowired
	private ComplaintsRepo cr;
	@Autowired
	private UserRepo rep;
	@Autowired
	private ComplaintAuditRepo audit;
	@Autowired
	private SkillApprovalRepo sar;
	@Autowired
	private InstAppRepo iar;
	@Autowired
	private InstructorUpdateRepo iur;
	
	public CompletableFuture<Integer> getAllComplaintsToday()
	{
		LocalDate dt= LocalDate.now();
		return CompletableFuture.supplyAsync(()-> cr.countByCreatedAtAndStatus(dt, ComplaintStatus.Pending),exe);
	}
	
	public CompletableFuture<Integer> getOldComplaints()
	{
		LocalDate date = LocalDate.now().minusDays(3);
		return CompletableFuture.supplyAsync(()-> cr.countByOldRecords(date, ComplaintStatus.Pending), exe);	
	}
	
	public CompletableFuture<Integer> getComplaintsHandledToday()
	{
		LocalDateTime st= LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
		LocalDateTime ed= st.plusDays(1);
		
		String usr= SecurityContextHolder.getContext().getAuthentication().getName();
		
		return CompletableFuture.supplyAsync(()->{
				
											UserRegistration ur= rep.findByUserName(usr).orElseThrow();
											int handled= audit.countByAdminTasksToday(st, ed, ur.getId());
											return handled;
													});
	}
	
	public CompletableFuture<Integer> getAllSkillApprovalsToday()
	{
		LocalDate date= LocalDate.now();
		
		return CompletableFuture.supplyAsync(()-> sar.countByStatusAndDate(SkillStatus.Pending, date) , exe);
	}
	
	public CompletableFuture<Integer> getOldSkillApprovals()
	{
		LocalDate date= LocalDate.now().minusDays(3);
		
		return CompletableFuture.supplyAsync(()-> sar.countByOldSkillApproval(date), exe);
	}
	
	public CompletableFuture<Integer> getSkillApprovalsToday()
	{
		LocalDate dt= LocalDate.now();
		String name= SecurityContextHolder.getContext().getAuthentication().getName();
		
		return CompletableFuture.supplyAsync(()->{
			
			UserRegistration ur= rep.findByUserName(name).orElseThrow();
			int handled= sar.countByAdmin_IdAndDate(ur.getId(), dt);
			return handled;
					}, exe);
	}
	
	public CompletableFuture<Integer> getAllInstructorApplications()
	{
		LocalDate date= LocalDate.now();
		
		return CompletableFuture.supplyAsync(()-> iar.countByIsPendingTrueAndAppliedAt(date) , exe);
		
	}
	
	public CompletableFuture<Integer> getOldInstructorApplications()
	{
		LocalDate date= LocalDate.now().minusDays(3);
		
		return CompletableFuture.supplyAsync(()-> iar.countByIsPendingTrueAndAppliedAtIsBefore(date), exe);
	}
	
	public CompletableFuture<Integer> getInstructorApplicationsReviewedToday()
	{
		LocalDateTime dt= LocalDate.now().atStartOfDay();
		LocalDateTime ed= dt.plusDays(1);
		
		String name= SecurityContextHolder.getContext().getAuthentication().getName();
		
		return CompletableFuture.supplyAsync(()->{
			
			UserRegistration ur= rep.findByUserName(name).orElseThrow();
			int handled= iur.countByTodayActivity(dt, ed, ur.getId());
			return handled;
					}, exe);
	}
	
}
