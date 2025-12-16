package com.harish.quizapp.Dto;

import com.harish.quizapp.enums.SkillStatus;

public class SkillApprovalDto
{
	private int id;
	private int instructorId;
	private String skillApplied;
	private SkillStatus status;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInstructorId() {
		return instructorId;
	}
	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
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
	
	

}
