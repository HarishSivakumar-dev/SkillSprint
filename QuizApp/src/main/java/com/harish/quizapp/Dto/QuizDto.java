package com.harish.quizapp.Dto;

import java.util.List;


public class QuizDto
{
	private String title;
	private int topicid;
	private List<ExistingQuestionsDto> questionid;
	private List<NewQuestionsDto> questions;
	private Boolean isFinal;
	
	public Boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}

	public int getTopicid() {
		return topicid;
	}

	public void setTopicid(int topicid) {
		this.topicid = topicid;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public List<ExistingQuestionsDto> getQuestionid() {
		return questionid;
	}

	public void setQuestionid(List<ExistingQuestionsDto> questionid) {
		this.questionid = questionid;
	}

	public List<NewQuestionsDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<NewQuestionsDto> questions) {
		this.questions = questions;
	}
	
	
}
