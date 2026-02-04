package com.harish.quizapp.Model;


import org.springframework.stereotype.Component;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@Component
public class Quiz 
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int topicid;

	private String title;

	@ManyToOne
	private CourseDetails course;
	
	@ManyToOne
	private InstructorProfile instructor;
	
	private Boolean isfinal;
	private int sequenceNumber;
	
	
	public Boolean getIsFinal() {
		return isfinal;
	}

	public void setIsFinal(Boolean isfinal) {
		this.isfinal = isfinal;
	}

	public int getTopicid() {
		return topicid;
	}

	public void setTopicid(int topicid) {
		this.topicid = topicid;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CourseDetails getCourse() {
		return course;
	}

	public void setCourse(CourseDetails course) {
		this.course = course;
	}

	public InstructorProfile getInstructor() {
		return instructor;
	}

	public void setInstructor(InstructorProfile instructor) {
		this.instructor = instructor;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
}
