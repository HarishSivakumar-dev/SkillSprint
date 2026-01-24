package com.harish.quizapp.Model;

import java.time.LocalDate;
import java.util.List;
import org.hibernate.validator.constraints.URL;
import com.harish.quizapp.Dto.CourseDetailsDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
public class InstructorProfile
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private UserRegistration userName;
	private String fullName;
	private String mail;
	private LocalDate joinedDate;
	private String headLine;
	private String shortBio;
	private String aboutSec;
	private String phone;
	private Boolean isViolated;
	
	@Transient
	private int trainedStud;
	
	@URL
	private String linkedinUrl;
	@URL
	private String githubUrl;
	@URL
	private String webUrl;
	@URL
	private String portfolioUrl;
	
	//overall course datum
	private int totCourses;
	private int totReviews;
	private float avgRating;
	private float completionRate;
	private String totExp;
	
	private int rateSum;
	private int rateCount;
	private int totalRegistered;
	private int totalCleared;
	
	@ManyToMany
	@JoinTable
	(
			name="Instructor_Skills",
			joinColumns= @JoinColumn(name="Inst_Id"),
			inverseJoinColumns=@JoinColumn(name="Skill_Id")
	)
	private List<Skills> skills;
	
	@Transient
	private List<CourseDetailsDto> courseDetails;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserRegistration getUserName() {
		return userName;
	}

	public void setUserName(UserRegistration userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getHeadLine() {
		return headLine;
	}

	public void setHeadLine(String headLine) {
		this.headLine = headLine;
	}

	public String getShortBio() {
		return shortBio;
	}

	public void setShortBio(String shortBio) {
		this.shortBio = shortBio;
	}

	public String getAboutSec() {
		return aboutSec;
	}

	public void setAboutSec(String aboutSec) {
		this.aboutSec = aboutSec;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLinkedinUrl() {
		return linkedinUrl;
	}

	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
	}

	public String getGithubUrl() {
		return githubUrl;
	}

	public void setGithubUrl(String githubUrl) {
		this.githubUrl = githubUrl;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getPortfolioUrl() {
		return portfolioUrl;
	}

	public void setPortfolioUrl(String portfolioUrl) {
		this.portfolioUrl = portfolioUrl;
	}

	public int getTotCourses() {
		return totCourses;
	}

	public void setTotCourses(int totCourses) {
		this.totCourses = totCourses;
	}
	
	public int getTotReviews() {
		return totReviews;
	}

	public void setTotReviews(int totReviews) {
		this.totReviews = totReviews;
	}

	public float getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}

	public float getCompletionRate() {
		return completionRate;
	}

	public void setCompletionRate(float completionRate) {
		this.completionRate = completionRate;
	}
	
	public String getTotExp() {
		return totExp;
	}

	public void setTotExp(String totExp) {
		this.totExp = totExp;
	}

	public LocalDate getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(LocalDate joinedDate) {
		this.joinedDate = joinedDate;
	}

	public List<Skills> getSkills() {
		return skills;
	}

	public void setSkills(List<Skills> skills) {
		this.skills = skills;
	}

	public List<CourseDetailsDto> getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(List<CourseDetailsDto> courseDetails) {
		this.courseDetails = courseDetails;
	}

	public Boolean getIsViolated() {
		return isViolated;
	}

	public void setIsViolated(Boolean isViolated) {
		this.isViolated = isViolated;
	}

	public int getRateSum() {
		return rateSum;
	}

	public void setRateSum(int rateSum) {
		this.rateSum = rateSum;
	}

	public int getRateCount() {
		return rateCount;
	}

	public void setRateCount(int rateCount) {
		this.rateCount = rateCount;
	}

	public int getTotalRegistered() {
		return totalRegistered;
	}

	public void setTotalRegistered(int totalRegistered) {
		this.totalRegistered = totalRegistered;
	}

	public int getTotalCleared() {
		return totalCleared;
	}

	public void setTotalCleared(int totalCleared) {
		this.totalCleared = totalCleared;
	}

	public int getTrainedStud() {
		return trainedStud;
	}

	public void setTrainedStud(int trainedStud) {
		this.trainedStud = trainedStud;
	}
	
	
}
