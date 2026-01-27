package com.harish.quizapp.Dto;

import java.time.LocalDateTime;

public class SuperAdminAnalyticsDto
{
	private int totCourses;
	private long totInstructors;
	private int totStudents;
	private int totAdmins;
	private int totAdminManagers;
	private int monthlyNewRegistrations;
	private LocalDateTime lastComputedAt;
	
	public SuperAdminAnalyticsDto(int totCourses, long totInstructors, int totStudents, int totAdmins,
			int totAdminManagers, int monthlyNewRegistrations, LocalDateTime lastComputedAt) {
		super();
		this.totCourses = totCourses;
		this.totInstructors = totInstructors;
		this.totStudents = totStudents;
		this.totAdmins = totAdmins;
		this.totAdminManagers = totAdminManagers;
		this.monthlyNewRegistrations = monthlyNewRegistrations;
		this.lastComputedAt = lastComputedAt;
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
