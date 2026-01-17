package com.harish.quizapp.helpers;

import org.springframework.stereotype.Component;

import com.harish.quizapp.Model.UserProfileDelta;
import com.harish.quizapp.enums.UserDeltaAction;

@Component
public class EnumHelperClass 
{
	
	public UserProfileDelta deltaReturn(UserDeltaAction act, int deltaval, int userid)
	{
		UserProfileDelta del= new UserProfileDelta();
		del.setAction(act);
		del.setDeltaValue(deltaval);
		del.setIsProcessed(false);
		del.setUserId(userid);
		
		return del;
	}

}
