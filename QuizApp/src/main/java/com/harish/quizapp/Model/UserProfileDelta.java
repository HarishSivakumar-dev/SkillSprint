package com.harish.quizapp.Model;

import com.harish.quizapp.enums.UserDeltaAction;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserProfileDelta 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private Boolean isProcessed;
	private int deltaValue;
	private int userId;
	
	@Enumerated(EnumType.STRING)
	private UserDeltaAction action;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getIsProcessed() {
		return isProcessed;
	}
	public void setIsProcessed(Boolean isProcessed) {
		this.isProcessed = isProcessed;
	}
	public int getDeltaValue() {
		return deltaValue;
	}
	public void setDeltaValue(int deltaValue) {
		this.deltaValue = deltaValue;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public UserDeltaAction getAction() {
		return action;
	}
	public void setAction(UserDeltaAction action) {
		this.action = action;
	}
	
	

}
