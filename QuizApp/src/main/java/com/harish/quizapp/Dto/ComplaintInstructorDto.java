package com.harish.quizapp.Dto;

import java.time.LocalDate;
import com.harish.quizapp.enums.ComplaintStatus;

public class ComplaintInstructorDto
{
	private int complaintId;
	private int instructorId;
	private String instructorUsername;
	private ComplaintStatus status;
	private String reason;
	private String comments;
	private LocalDate createdAt;
	
	public int getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}
	public int getInstructorId() {
		return instructorId;
	}
	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}
	public String getInstructorUsername() {
		return instructorUsername;
	}
	public void setInstructorUsername(String instructorUsername) {
		this.instructorUsername = instructorUsername;
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
