package com.harish.quizapp.Dto;



public class ResultDto
{
	
	private int score;
	private String status;
	private int nextquizid;
	private int streak;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNextquizid() {
		return nextquizid;
	}

	public void setNextquizid(int nextquizid) {
		this.nextquizid = nextquizid;
	}

	public int getStreak() {
		return streak;
	}

	public void setStreak(int streak) {
		this.streak = streak;
	}
	
}
