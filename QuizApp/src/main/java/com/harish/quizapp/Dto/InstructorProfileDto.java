package com.harish.quizapp.Dto;

import java.time.LocalDate;
import java.util.List;
import com.harish.quizapp.Model.Skills;

public class InstructorProfileDto
{
	private String userName;
	private String fullName;
	private String mail;
	private LocalDate joinedDate;
	private String headLine;
	private String shortBio;
	private String aboutSec;
	private String phone;
	private Boolean isViolated;
	private String linkedinUrl;
	private String githubUrl;
	private String webUrl;
	private String portfolioUrl;
	private int totCourses;
	private int totEnrollments;
	private int totReviews;
	private float avgRating;
	private float completionRate;
	private String totExp;
	private List<Skills> skills;
	private List<CourseDetailsDto> courseDetails;
	private int trainedStudents;


	public InstructorProfileDto(String userName, String fullName, String mail, LocalDate joinedDate, String headLine,
			String shortBio, String aboutSec, String phone, Boolean isViolated, String linkedinUrl, String githubUrl,
			String webUrl, String portfolioUrl, int totCourses, int totEnrollments, int trainedStudents, int totReviews, float avgRating,
			float completionRate, String totExp, List<Skills> skills, List<CourseDetailsDto> courseDetails) {
		super();
		this.userName = userName;
		this.fullName = fullName;
		this.mail = mail;
		this.joinedDate = joinedDate;
		this.headLine = headLine;
		this.shortBio = shortBio;
		this.aboutSec = aboutSec;
		this.phone = phone;
		this.isViolated = isViolated;
		this.linkedinUrl = linkedinUrl;
		this.githubUrl = githubUrl;
		this.webUrl = webUrl;
		this.portfolioUrl = portfolioUrl;
		this.totCourses = totCourses;
		this.totEnrollments = totEnrollments;
		this.totReviews = totReviews;
		this.avgRating = avgRating;
		this.completionRate = completionRate;
		this.totExp = totExp;
		this.skills = skills;
		this.courseDetails = courseDetails;
		this.trainedStudents=trainedStudents;
	}




	public String getUserName() {
		return userName;
	}
	public String getFullName() {
		return fullName;
	}
	public String getMail() {
		return mail;
	}
	public LocalDate getJoinedDate() {
		return joinedDate;
	}
	public String getHeadLine() {
		return headLine;
	}
	public String getShortBio() {
		return shortBio;
	}
	public String getAboutSec() {
		return aboutSec;
	}
	public String getPhone() {
		return phone;
	}
	public Boolean getIsViolated() {
		return isViolated;
	}
	public String getLinkedinUrl() {
		return linkedinUrl;
	}
	public String getGithubUrl() {
		return githubUrl;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public String getPortfolioUrl() {
		return portfolioUrl;
	}
	public int getTotCourses() {
		return totCourses;
	}
	public int getTotEnrollments() {
		return totEnrollments;
	}
	public int getTotReviews() {
		return totReviews;
	}
	public float getAvgRating() {
		return avgRating;
	}
	public float getCompletionRate() {
		return completionRate;
	}
	public String getTotExp() {
		return totExp;
	}
	public List<Skills> getSkills() {
		return skills;
	}
	public List<CourseDetailsDto> getCourseDetails() {
		return courseDetails;
	}	
	public int getTrainedStudents() {
		return trainedStudents;
	}
}
