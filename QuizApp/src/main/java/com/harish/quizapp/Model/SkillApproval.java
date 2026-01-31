package com.harish.quizapp.Model;

import java.time.LocalDate;
import com.harish.quizapp.enums.SkillStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SkillApproval 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name= "instrutor_id")
	private UserRegistration instructor;
	
	@ManyToOne
	@JoinColumn(name= "Handled_admin_id")
	private UserRegistration admin;
	
	private String skillApplied;
	
	@Enumerated(EnumType.STRING)
	private SkillStatus status;
	
	private LocalDate date;
	
	private String comments;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserRegistration getAdmin() {
		return admin;
	}

	public void setAdmin(UserRegistration admin) {
		this.admin = admin;
	}

	public String getSkillApplied() {
		return skillApplied;
	}

	public void setSkillApplied(String skillApplied) {
		this.skillApplied = skillApplied;
	}

	public SkillStatus getStatus() {
		return status;
	}

	public void setStatus(SkillStatus status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public UserRegistration getInstructor() {
		return instructor;
	}

	public void setInstructor(UserRegistration instructor) {
		this.instructor = instructor;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
}
