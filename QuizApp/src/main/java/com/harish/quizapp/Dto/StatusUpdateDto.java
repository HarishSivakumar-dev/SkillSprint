package com.harish.quizapp.Dto;

import com.harish.quizapp.enums.CourseStatus;

public class StatusUpdateDto
{
	private int id;
	private CourseStatus status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CourseStatus getStatus() {
		return status;
	}
	public void setStatus(CourseStatus status) {
		this.status = status;
	}
	
	
	

}
