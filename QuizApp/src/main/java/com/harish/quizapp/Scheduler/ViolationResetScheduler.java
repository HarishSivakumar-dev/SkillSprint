package com.harish.quizapp.Scheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.harish.quizapp.DataRepos.ViolationTableRepo;
import com.harish.quizapp.Model.ViolationsTable;

@Component
public class ViolationResetScheduler
{
	@Autowired
	private ViolationTableRepo vtr;
	
	@Scheduled(cron="0 0 0 * * * ")
	public void resetViolationField()
	{
		List<ViolationsTable> tbl= vtr.findAll();
		
		List<ViolationsTable> newrec= new ArrayList<>();
		
		for(ViolationsTable vt : tbl)
		{
			if(vt.isViolated() && vt.getDateOfViolation().isBefore(LocalDateTime.now().minusDays(30)))
			{
				vt.setDateOfViolation(null);
				vt.setFinalViolationCount(0);
				vt.setInitialViolationCount(0);
				vt.setViolated(false);
				
				newrec.add(vt);	
			}
		}
		
		vtr.saveAll(newrec);
	}	
}
