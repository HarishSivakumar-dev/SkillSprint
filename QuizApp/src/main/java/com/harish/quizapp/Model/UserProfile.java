package com.harish.quizapp.Model;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.harish.quizapp.enums.GenderEnum;
import com.harish.quizapp.enums.SkillLevelEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class UserProfile
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String fullName;//
	
	@OneToOne
	@JoinColumn(name="user_name", referencedColumnName="userName")
	private UserRegistration userName;//
	
	private String email;//
	private Boolean isEmailVerified;//
	private String phoneNumber;//
	private LocalDate dateOfBirth;//
	
	@Enumerated(EnumType.STRING)
	private GenderEnum gender;//
	private String userBio;//
	private String place;//
	
	private LocalDateTime joinedDate;//
	
	private int totCoursesEnrolled;//
	private int coursesCompleted;//
	private int noOfCertificates;//
	private int quizzesAttended;//
	private int avgQuizezCleared;//
	private float avgClearingRate;//
	private String streakMaintanance;
	
	@Enumerated(EnumType.STRING)
	private SkillLevelEnum level;
	
	private String collegeName;
	private String department;
	private int yearOfStudy;
	private URL linkedIn;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public UserRegistration getUserName() {
		return userName;
	}
	public void setUserName(UserRegistration userName) {
		this.userName = userName;
	}
	public float getAvgClearingRate() {
		return avgClearingRate;
	}
	public void setAvgClearingRate(float avgClearingRate) {
		this.avgClearingRate = avgClearingRate;
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
	public int getAvgQuizezCleared() {
		return avgQuizezCleared;
	}
	public void setAvgQuizezCleared(int avgQuizezCleared) {
		this.avgQuizezCleared = avgQuizezCleared;
	}
	public String getStreakMaintanance() {
		return streakMaintanance;
	}
	public void setStreakMaintanance(String streakMaintanance) {
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
	public URL getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(URL linkedIn) {
		this.linkedIn = linkedIn;
	}
}
