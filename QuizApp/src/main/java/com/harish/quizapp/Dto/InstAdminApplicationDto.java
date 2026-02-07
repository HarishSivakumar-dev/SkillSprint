package com.harish.quizapp.Dto;

import java.time.LocalDateTime;
import com.harish.quizapp.enums.PromotionStatus;
import com.harish.quizapp.enums.PromotionType;

public class InstAdminApplicationDto
{
	private int instId;
	private String instructorEmail;
	private LocalDateTime appliedDate;
	private PromotionStatus promotionStatus;
	private String instName;
	private Boolean autoEvaluation;
	private PromotionType type;
	
	public int getInstId() {
		return instId;
	}
	public void setInstId(int instId) {
		this.instId = instId;
	}
	public String getInstructorEmail() {
		return instructorEmail;
	}
	public void setInstructorEmail(String instructorEmail) {
		this.instructorEmail = instructorEmail;
	}
	public LocalDateTime getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(LocalDateTime appliedDate) {
		this.appliedDate = appliedDate;
	}
	public PromotionStatus getPromotionStatus() {
		return promotionStatus;
	}
	public void setPromotionStatus(PromotionStatus promotionStatus) {
		this.promotionStatus = promotionStatus;
	}
	public String getInstName() {
		return instName;
	}
	public void setInstName(String instName) {
		this.instName = instName;
	}
	public Boolean getAutoEvaluation() {
		return autoEvaluation;
	}
	public void setAutoEvaluation(Boolean autoEvaluation) {
		this.autoEvaluation = autoEvaluation;
	}
	public PromotionType getType() {
		return type;
	}
	public void setType(PromotionType type) {
		this.type = type;
	}	
	
}
