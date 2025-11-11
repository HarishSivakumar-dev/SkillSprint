package com.harish.quizapp.Dto;

import java.net.URL;

public class UserStudyProfileDto
{
	private String collegeName;
	private String department;
	private int yearOfStudy;
	private URL linkedIn;
	
	
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getYearOfStudy() {
		return yearOfStudy;
	}
	public void setYearOfStudy(int yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}
	public URL getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(URL linkedIn) {
		this.linkedIn = linkedIn;
	}
}
