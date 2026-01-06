package com.harish.quizapp.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class FeedbackTable
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private UserRegistration user;
	
	@ManyToOne
	@JoinColumn(name="instructor_id")
	private InstructorProfile instructor;
	
	private int courseId;
	
	private int rating;
	
	private String comments;
	
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
	public InstructorProfile getInstructor() {
		return instructor;
	}
	public void setInstructor(InstructorProfile instructor) {
		this.instructor = instructor;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}	
}
