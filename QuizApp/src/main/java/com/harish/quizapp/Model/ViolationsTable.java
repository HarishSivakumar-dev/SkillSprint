package com.harish.quizapp.Model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class ViolationsTable 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn(name="Instructor")
	private UserRegistration instructor;
	
	@OneToOne
	@JoinColumn(name="Instructor_profile")
	private InstructorProfile instProf;
	
	private int initialViolationCount;
	
	private int finalViolationCount;
	
	private boolean violated;
	
	private LocalDateTime dateOfViolation;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserRegistration getInstructor() {
		return instructor;
	}

	public void setInstructor(UserRegistration instructor) {
		this.instructor = instructor;
	}
	
	public int getInitialViolationCount() {
		return initialViolationCount;
	}

	public void setInitialViolationCount(int initialViolationCount) {
		this.initialViolationCount = initialViolationCount;
	}

	public int getFinalViolationCount() {
		return finalViolationCount;
	}

	public void setFinalViolationCount(int finalViolationCount) {
		this.finalViolationCount = finalViolationCount;
	}

	public boolean isViolated() {
		return violated;
	}

	public void setViolated(boolean violated) {
		this.violated = violated;
	}

	public LocalDateTime getDateOfViolation() {
		return dateOfViolation;
	}

	public void setDateOfViolation(LocalDateTime dateOfViolation) {
		this.dateOfViolation = dateOfViolation;
	}

	public InstructorProfile getInstProf() {
		return instProf;
	}

	public void setInstProf(InstructorProfile instProf) {
		this.instProf = instProf;
	}
	
}
