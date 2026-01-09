package com.harish.quizapp.Model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="EnrollmentData")
public class EnrollmentData
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserRegistration user;
	
	@ManyToOne
	@JoinColumn(name="course_id")
	private CourseDetails course;
	
	private LocalDateTime enrollment_date;
	private String status;
	
	
	
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
	public LocalDateTime getEnrollment_date() {
		return enrollment_date;
	}
	public void setEnrollment_date(LocalDateTime enrollment_date) {
		this.enrollment_date = enrollment_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
