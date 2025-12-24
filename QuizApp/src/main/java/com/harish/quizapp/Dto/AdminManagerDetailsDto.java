package com.harish.quizapp.Dto;

public class AdminManagerDetailsDto
{
	private int id;
	private String name;
	private String userName;
	private String email;
	private float tenure;
	private int noofPromotions;
	private int noofSkillsApproved;
	private int noofViolationsHandled;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public float getTenure() {
		return tenure;
	}
	public void setTenure(float tenure) {
		this.tenure = tenure;
	}
	public int getNoofPromotions() {
		return noofPromotions;
	}
	public void setNoofPromotions(int noofPromotions) {
		this.noofPromotions = noofPromotions;
	}
	public int getNoofSkillsApproved() {
		return noofSkillsApproved;
	}
	public void setNoofSkillsApproved(int noofSkillsApproved) {
		this.noofSkillsApproved = noofSkillsApproved;
	}
	public int getNoofViolationsHandled() {
		return noofViolationsHandled;
	}
	public void setNoofViolationsHandled(int noofViolationsHandled) {
		this.noofViolationsHandled = noofViolationsHandled;
	}
	
}
