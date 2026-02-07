package com.harish.quizapp.Dto;

import java.time.LocalDate;
import com.harish.quizapp.enums.ComplaintStatus;

public class AdminCompliantsDto
{
	private int reportedInstructorId;
	private String reportedInstructorUserName;
	private int reportingUserId;
	private String reportingUserName;
	private int reportedCourseId;
	private String reportedCourseName;
	private ComplaintStatus  status;
	private String reason;
	private String comments;
	private LocalDate createdAt;
	
	public int getReportedInstructorId() {
		return reportedInstructorId;
	}
	public void setReportedInstructorId(int reportedInstructorId) {
		this.reportedInstructorId = reportedInstructorId;
	}
	public String getReportedInstructorUserName() {
		return reportedInstructorUserName;
	}
	public void setReportedInstructorUserName(String reportedInstructorUserName) {
		this.reportedInstructorUserName = reportedInstructorUserName;
	}
	public int getReportingUserId() {
		return reportingUserId;
	}
	public void setReportingUserId(int reportingUserId) {
		this.reportingUserId = reportingUserId;
	}
	public String getReportingUserName() {
		return reportingUserName;
	}
	public void setReportingUserName(String reportingUserName) {
		this.reportingUserName = reportingUserName;
	}
	public int getReportedCourseId() {
		return reportedCourseId;
	}
	public void setReportedCourseId(int reportedCourseId) {
		this.reportedCourseId = reportedCourseId;
	}
	public String getReportedCourseName() {
		return reportedCourseName;
	}
	public void setReportedCourseName(String reportedCourseName) {
		this.reportedCourseName = reportedCourseName;
	}
	public ComplaintStatus getStatus() {
		return status;
	}
	public void setStatus(ComplaintStatus status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

}
