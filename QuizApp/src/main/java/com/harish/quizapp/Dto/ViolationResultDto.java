package com.harish.quizapp.Dto;

public class ViolationResultDto
{
	private int initialViolationCount;
	private int finalViolationCount;
	private Boolean isViolated;
	
	
	public int getInitialViolationCount() {
		return initialViolationCount;
	}
	public void setInitialViolationCount(int initialViolationCount) {
		this.initialViolationCount = initialViolationCount;
	}
	public int getFinalViolationCount() {
		return finalViolationCount;
	}
	public void setFinalViolationCount(int finalViolationCount) {
		this.finalViolationCount = finalViolationCount;
	}
	public Boolean getIsViolated() {
		return isViolated;
	}
	public void setIsViolated(Boolean isViolated) {
		this.isViolated = isViolated;
	}
	
	
}
