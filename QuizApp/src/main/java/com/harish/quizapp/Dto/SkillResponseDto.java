package com.harish.quizapp.Dto;

import com.harish.quizapp.enums.SkillStatus;

public class SkillResponseDto
{
	private String skillName;
	
	private SkillStatus status;

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public SkillStatus getStatus() {
		return status;
	}

	public void setStatus(SkillStatus status) {
		this.status = status;
	}

}
