package com.harish.quizapp.DataRepos;

import com.harish.quizapp.enums.UserDeltaAction;

public interface UserDeltaProjection
{
	int getTotDelta();
	int getUserId();
	UserDeltaAction getUserAction();
}
