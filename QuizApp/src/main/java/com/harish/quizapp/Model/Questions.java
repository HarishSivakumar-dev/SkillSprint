package com.harish.quizapp.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Questions
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	private String difficuty;
	private String Catagory;
	private String question;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String option5;
	
	public Questions(String difficuty, String catagory, String question, String option1, String option2,
			String option3, String option4, String option5, String rightans, CourseDetails course) {
		super();
		this.difficuty = difficuty;
		Catagory = catagory;
		this.question = question;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.option5 = option5;
		this.rightans = rightans;
		this.course = course;
	}
	private String rightans;
	
	@ManyToOne
	@JoinColumn(name="course_id")
	private CourseDetails course;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDifficuty() {
		return difficuty;
	}
	public void setDifficuty(String difficuty) {
		this.difficuty = difficuty;
	}
	public String getCatagory() {
		return Catagory;
	}
	public void setCatagory(String catagory) {
		Catagory = catagory;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public String getOption4() {
		return option4;
	}
	public void setOption4(String option4) {
		this.option4 = option4;
	}
	public String getOption5() {
		return option5;
	}
	public void setOption5(String option5) {
		this.option5 = option5;
	}
	public String getRightans() {
		return rightans;
	}
	public void setRightans(String rightans) {
		this.rightans = rightans;
	}
	public CourseDetails getCourse() {
		return course;
	}
	public void setCourse(CourseDetails course) {
		this.course = course;
	}

}
