package com.harish.quizapp.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="AttemptsTable")
public class attemptsTable
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="userid")
	private UserRegistration user;
	
	@ManyToOne
	@JoinColumn(name="courseid")
	private CourseDetails course;
	
	@ManyToOne
	@JoinColumn(name="quizid")
	private Quiz quiz;
	
	private String status;
	
	private int attemptcount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserRegistration getUser() {
		return user;
	}

	public void setUser(UserRegistration user) {
		this.user = user;
	}

	public CourseDetails getCourse() {
		return course;
	}

	public void setCourse(CourseDetails course) {
		this.course = course;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAttemptcount() {
		return attemptcount;
	}

	public void setAttemptcount(int attemptcount) {
		this.attemptcount = attemptcount;
	}
	
}
