package com.harish.quizapp.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SuperAdminAnalytics 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int totCourses;
	private long totInstructors;
	private int totStudents;
	private int totAdmins;
	private int totAdminManagers;
	private int monthlyNewRegistrations;
	private LocalDateTime lastComputedAt;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotCourses() {
		return totCourses;
	}
	public void setTotCourses(int totCourses) {
		this.totCourses = totCourses;
	}
	public long getTotInstructors() {
		return totInstructors;
	}
	public void setTotInstructors(long totInstructors) {
		this.totInstructors = totInstructors;
	}
	public int getTotStudents() {
		return totStudents;
	}
	public void setTotStudents(int totStudents) {
		this.totStudents = totStudents;
	}
	public int getTotAdmins() {
		return totAdmins;
	}
	public void setTotAdmins(int totAdmins) {
		this.totAdmins = totAdmins;
	}
	public int getTotAdminManagers() {
		return totAdminManagers;
	}
	public void setTotAdminManagers(int totAdminManagers) {
		this.totAdminManagers = totAdminManagers;
	}
	public int getMonthlyNewRegistrations() {
		return monthlyNewRegistrations;
	}
	public void setMonthlyNewRegistrations(int monthlyNewRegistrations) {
		this.monthlyNewRegistrations = monthlyNewRegistrations;
	}
	public LocalDateTime getLastComputedAt() {
		return lastComputedAt;
	}
	public void setLastComputedAt(LocalDateTime lastComputedAt) {
		this.lastComputedAt = lastComputedAt;
	}
	
	

}
