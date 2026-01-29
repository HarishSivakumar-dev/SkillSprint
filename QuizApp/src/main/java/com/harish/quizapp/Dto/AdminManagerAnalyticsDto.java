package com.harish.quizapp.Dto;

public class AdminManagerAnalyticsDto
{
	private int totAdmins;
	private int activeAdminsToday;
	private int pendingApplications;
	private int applicationsApprovedToday;
	private int allApplications;
	
	
	public int getTotAdmins() {
		return totAdmins;
	}
	public void setTotAdmins(int totAdmins) {
		this.totAdmins = totAdmins;
	}
	public int getActiveAdminsToday() {
		return activeAdminsToday;
	}
	public void setActiveAdminsToday(int activeAdminsToday) {
		this.activeAdminsToday = activeAdminsToday;
	}
	public int getPendingApplications() {
		return pendingApplications;
	}
	public void setPendingApplications(int pendingApplications) {
		this.pendingApplications = pendingApplications;
	}
	public int getApplicationsApprovedToday() {
		return applicationsApprovedToday;
	}
	public void setApplicationsApprovedToday(int applicationsApprovedToday) {
		this.applicationsApprovedToday = applicationsApprovedToday;
	}
	public int getAllApplications() {
		return allApplications;
	}
	public void setAllApplications(int allApplications) {
		this.allApplications = allApplications;
	}
	
	
	
}
