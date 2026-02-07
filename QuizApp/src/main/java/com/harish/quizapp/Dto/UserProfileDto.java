package com.harish.quizapp.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.harish.quizapp.enums.GenderEnum;
import com.harish.quizapp.enums.SkillLevelEnum;

public class UserProfileDto
{
	
	
	private String fullName;
	private String email;//
	private Boolean isEmailVerified;//
	private String phoneNumber;//
	private LocalDate dateOfBirth;//
	private GenderEnum gender;//
	private String userBio;//
	private String place;//
	private LocalDateTime joinedDate;//
	private int totCoursesEnrolled;//
	private int coursesCompleted;//
	private int noOfCertificates;//
	private int quizzesAttended;//
	private int quizzesCleared;//
	private float avgQuizezCleared;//
	private float avgCourseCompletionRate;//
	private float avgCourseCertificationRate;
	private int streakMaintanance;
	private SkillLevelEnum level;
	private String collegeName;
	private String department;
	private int yearOfStudy;
	private int userId;
	private String userName;
	
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getIsEmailVerified() {
		return isEmailVerified;
	}
	public void setIsEmailVerified(Boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public GenderEnum getGender() {
		return gender;
	}
	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}
	public String getUserBio() {
		return userBio;
	}
	public void setUserBio(String userBio) {
		this.userBio = userBio;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public LocalDateTime getJoinedDate() {
		return joinedDate;
	}
	public void setJoinedDate(LocalDateTime joinedDate) {
		this.joinedDate = joinedDate;
	}
	public int getTotCoursesEnrolled() {
		return totCoursesEnrolled;
	}
	public void setTotCoursesEnrolled(int totCoursesEnrolled) {
		this.totCoursesEnrolled = totCoursesEnrolled;
	}
	public int getCoursesCompleted() {
		return coursesCompleted;
	}
	public void setCoursesCompleted(int coursesCompleted) {
		this.coursesCompleted = coursesCompleted;
	}
	public int getNoOfCertificates() {
		return noOfCertificates;
	}
	public void setNoOfCertificates(int noOfCertificates) {
		this.noOfCertificates = noOfCertificates;
	}
	public int getQuizzesAttended() {
		return quizzesAttended;
	}
	public void setQuizzesAttended(int quizzesAttended) {
		this.quizzesAttended = quizzesAttended;
	}
	public int getQuizzesCleared() {
		return quizzesCleared;
	}
	public void setQuizzesCleared(int quizzesCleared) {
		this.quizzesCleared = quizzesCleared;
	}
	public float getAvgQuizezCleared() {
		return avgQuizezCleared;
	}
	public void setAvgQuizezCleared(float avgQuizezCleared) {
		this.avgQuizezCleared = avgQuizezCleared;
	}
	public float getAvgCourseCompletionRate() {
		return avgCourseCompletionRate;
	}
	public void setAvgCourseCompletionRate(float avgCourseCompletionRate) {
		this.avgCourseCompletionRate = avgCourseCompletionRate;
	}
	public float getAvgCourseCertificationRate() {
		return avgCourseCertificationRate;
	}
	public void setAvgCourseCertificationRate(float avgCourseCertificationRate) {
		this.avgCourseCertificationRate = avgCourseCertificationRate;
	}
	public int getStreakMaintanance() {
		return streakMaintanance;
	}
	public void setStreakMaintanance(int streakMaintanance) {
		this.streakMaintanance = streakMaintanance;
	}
	public SkillLevelEnum getLevel() {
		return level;
	}
	public void setLevel(SkillLevelEnum level) {
		this.level = level;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getYearOfStudy() {
		return yearOfStudy;
	}
	public void setYearOfStudy(int yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
