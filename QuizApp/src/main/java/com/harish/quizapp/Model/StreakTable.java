package com.harish.quizapp.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class StreakTable 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn(name="USER_ID")
	private UserRegistration userId;
	
	private int streak;
	
	private LocalDate lastQuizDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserRegistration getUserId() {
		return userId;
	}

	public void setUserId(UserRegistration userId) {
		this.userId = userId;
	}

	public int getStreak() {
		return streak;
	}

	public void setStreak(int streak) {
		this.streak = streak;
	}

	public LocalDate getLastQuizDate() {
		return lastQuizDate;
	}

	public void setLastQuizDate(LocalDate lastQuizDate) {
		this.lastQuizDate = lastQuizDate;
	}
	
	
}
