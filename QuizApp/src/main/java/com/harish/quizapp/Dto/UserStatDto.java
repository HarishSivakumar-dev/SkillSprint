package com.harish.quizapp.Dto;

import com.harish.quizapp.enums.SkillLevelEnum;

public class UserStatDto
{
	private int totCoursesEnrolled;
	private int coursesCompleted;
	private int noOfCertificates;
	private int quizzesAttended;
	private int avgQuizScore;
	private String streakMaintanance;
	private SkillLevelEnum level;
	
	
	public int getTotCoursesEnrolled() {
		return totCoursesEnrolled;
	}
	public void setTotCoursesEnrolled(int totCoursesEnrolled) {
		this.totCoursesEnrolled = totCoursesEnrolled;
	}
	public int getCoursesCompleted() {
		return coursesCompleted;
	}
	public void setCoursesCompleted(int coursesCompleted) {
		this.coursesCompleted = coursesCompleted;
	}
	public int getNoOfCertificates() {
		return noOfCertificates;
	}
	public void setNoOfCertificates(int noOfCertificates) {
		this.noOfCertificates = noOfCertificates;
	}
	public int getQuizzesAttended() {
		return quizzesAttended;
	}
	public void setQuizzesAttended(int quizzesAttended) {
		this.quizzesAttended = quizzesAttended;
	}
	public int getAvgQuizScore() {
		return avgQuizScore;
	}
	public void setAvgQuizScore(int avgQuizScore) {
		this.avgQuizScore = avgQuizScore;
	}
	public String getStreakMaintanance() {
		return streakMaintanance;
	}
	public void setStreakMaintanance(String streakMaintanance) {
		this.streakMaintanance = streakMaintanance;
	}
	public SkillLevelEnum getLevel() {
		return level;
	}
	public void setLevel(SkillLevelEnum level) {
		this.level = level;
	}
	
	

}
