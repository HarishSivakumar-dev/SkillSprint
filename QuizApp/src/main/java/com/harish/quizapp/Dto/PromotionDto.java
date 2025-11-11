package com.harish.quizapp.Dto;

public class PromotionDto
{
	private int userId;
	private boolean Approved;
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public boolean isApproved() {
		return Approved;
	}
	public void setApproved(boolean approved) {
		Approved = approved;
	}

}
