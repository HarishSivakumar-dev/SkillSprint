package com.harish.quizapp.Dto;

import java.time.LocalDateTime;
import com.harish.quizapp.enums.PromotionStatus;
import com.harish.quizapp.enums.PromotionType;

public class AdminApplicationDto
{
	private String instructorEmail;
	private LocalDateTime appliedDate;
	private int totcourses;
	private float avgrating;
	private int feedbackcount;
	private Boolean isViolated;
	private int studTrained;
	private Long expYears;
	private PromotionStatus promotionStatus;
	private Boolean isVerified;
	private LocalDateTime reviewedOn;
	private String remarks;
	private String reasonForApplication;
	private String documentsUrl;
	private String achievements;
	private Boolean autoEvaluation;
	private PromotionType type;
	private int intructorId;
	private String instName;
	private int adminManagerId;
	private String adminManagerName;
	
	
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
	public int getTotcourses() {
		return totcourses;
	}
	public void setTotcourses(int totcourses) {
		this.totcourses = totcourses;
	}
	public float getAvgrating() {
		return avgrating;
	}
	public void setAvgrating(float avgrating) {
		this.avgrating = avgrating;
	}
	public int getFeedbackcount() {
		return feedbackcount;
	}
	public void setFeedbackcount(int feedbackcount) {
		this.feedbackcount = feedbackcount;
	}
	public Boolean getIsViolated() {
		return isViolated;
	}
	public void setIsViolated(Boolean isViolated) {
		this.isViolated = isViolated;
	}
	public int getStudTrained() {
		return studTrained;
	}
	public void setStudTrained(int studTrained) {
		this.studTrained = studTrained;
	}
	public Long getExpYears() {
		return expYears;
	}
	public void setExpYears(Long expYears) {
		this.expYears = expYears;
	}
	public PromotionStatus getPromotionStatus() {
		return promotionStatus;
	}
	public void setPromotionStatus(PromotionStatus promotionStatus) {
		this.promotionStatus = promotionStatus;
	}
	public Boolean getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}
	public LocalDateTime getReviewedOn() {
		return reviewedOn;
	}
	public void setReviewedOn(LocalDateTime reviewedOn) {
		this.reviewedOn = reviewedOn;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getReasonForApplication() {
		return reasonForApplication;
	}
	public void setReasonForApplication(String reasonForApplication) {
		this.reasonForApplication = reasonForApplication;
	}
	public String getDocumentsUrl() {
		return documentsUrl;
	}
	public void setDocumentsUrl(String documentsUrl) {
		this.documentsUrl = documentsUrl;
	}
	public String getAchievements() {
		return achievements;
	}
	public void setAchievements(String achievements) {
		this.achievements = achievements;
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
	public int getIntructorId() {
		return intructorId;
	}
	public void setIntructorId(int intructorId) {
		this.intructorId = intructorId;
	}
	public String getInstName() {
		return instName;
	}
	public void setInstName(String instName) {
		this.instName = instName;
	}
	public int getAdminManagerId() {
		return adminManagerId;
	}
	public void setAdminManagerId(int adminManagerId) {
		this.adminManagerId = adminManagerId;
	}
	public String getAdminManagerName() {
		return adminManagerName;
	}
	public void setAdminManagerName(String adminManagerName) {
		this.adminManagerName = adminManagerName;
	}
	
		
}
