package com.harish.quizapp.Dto;

import java.time.LocalDate;
import com.harish.quizapp.enums.ComplaintStatus;

public class UserComplaintsDto
{
	private int complaintId;
	private String reportedIntructor;
	private String reason;
	private String comments;
	private ComplaintStatus status;
	private LocalDate createdDate;
	
	public int getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}
	public String getReportedIntructor() {
		return reportedIntructor;
	}
	public void setReportedIntructor(String reportedIntructor) {
		this.reportedIntructor = reportedIntructor;
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
	public ComplaintStatus getStatus() {
		return status;
	}
	public void setStatus(ComplaintStatus status) {
		this.status = status;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	
}
