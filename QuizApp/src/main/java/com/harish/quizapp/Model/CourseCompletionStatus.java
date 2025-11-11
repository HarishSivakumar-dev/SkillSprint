package com.harish.quizapp.Model;


import com.harish.quizapp.enums.CompletionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CourseCompletionStatus 
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
	
	@Enumerated(EnumType.STRING)
	private CompletionStatus courseCompletionStatus;

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

	public CompletionStatus getCourseCompletionStatus() {
		return courseCompletionStatus;
	}

	public void setCourseCompletionStatus(CompletionStatus courseCompletionStatus) {
		this.courseCompletionStatus = courseCompletionStatus;
	}
	


}
