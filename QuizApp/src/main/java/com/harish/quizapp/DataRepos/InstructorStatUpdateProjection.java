package com.harish.quizapp.DataRepos;

import com.harish.quizapp.enums.StatUpdateEvent;

public interface InstructorStatUpdateProjection
{
	int getInstId();
	StatUpdateEvent getEventType();
	int getTotChange();
}
