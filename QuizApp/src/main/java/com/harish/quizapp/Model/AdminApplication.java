package com.harish.quizapp.Model;

import java.time.LocalDateTime;
import com.harish.quizapp.enums.PromotionStatus;
import com.harish.quizapp.enums.PromotionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class AdminApplication 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn(name="Instructor_Id")
	private UserRegistration user;
	
	private String instructorEmail;
	private LocalDateTime appliedDate;
	private int totcourses;
	private float avgrating;
	private int feedbackcount;
	private Boolean isViolated;
	private int studTrained;
	private Long expYears;
	
	@Enumerated(EnumType.STRING)
	private PromotionStatus promotionStatus;
	private Boolean isVerified;
	
	@ManyToOne
	@JoinColumn(name="Super_Admin_Name")
	private UserRegistration superAdmin;
	
	private LocalDateTime reviewedOn;
	private String remarks;
	private String reasonForApplication;
	private String documentsUrl;
	private String achievements;
	private Boolean autoEvaluation;
	
	@Enumerated(EnumType.STRING)
	private PromotionType type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserRegistration getUser() {
		return user;
	}

	public void setUser(UserRegistration user) {
		this.user = user;
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

	public UserRegistration getSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(UserRegistration superAdmin) {
		this.superAdmin = superAdmin;
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

	public Boolean getAutoEvalation() {
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

	public String getReasonForApplication() {
		return reasonForApplication;
	}

	public void setReasonForApplication(String reasonForApplication) {
		this.reasonForApplication = reasonForApplication;
	}
	
}