package com.harish.quizapp.Dto;


public class AllQuizDto
{
	private int topicId;
	private String title;
	private Boolean isfinal;
	private int order;
	private String courseName;
	
	
	public int getTopicId() {
		return topicId;
	}
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getIsfinal() {
		return isfinal;
	}
	public void setIsfinal(Boolean isfinal) {
		this.isfinal = isfinal;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	
}
