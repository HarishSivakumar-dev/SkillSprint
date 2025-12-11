package com.harish.quizapp.Model;

import java.time.LocalDate;
import java.util.List;
import org.hibernate.validator.constraints.URL;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

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
	private int totStudents;
	private int totReviews;
	private float avgRating;
	private float completionRate;
	private String totExp;
	
	//datum per course 
	private String catagory;
	private float rating;
	private int totStud;
	private String Status;
	
	@ManyToMany
	@JoinTable
	(
			name="Instructor_Skills",
			joinColumns= @JoinColumn(name="Inst_Id"),
			inverseJoinColumns=@JoinColumn(name="Skill_Id")
	)
	private List<Skills> skills;
	

	
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

	public int getTotStudents() {
		return totStudents;
	}

	public void setTotStudents(int totStudents) {
		this.totStudents = totStudents;
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

	public String getCatagory() {
		return catagory;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public int getTotStud() {
		return totStud;
	}

	public void setTotStud(int totStud) {
		this.totStud = totStud;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public List<Skills> getSkills() {
		return skills;
	}

	public void setSkills(List<Skills> skills) {
		this.skills = skills;
	}

	public LocalDate getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(LocalDate joinedDate) {
		this.joinedDate = joinedDate;
	}
	
	
}
