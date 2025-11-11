package com.harish.quizapp.Model;


import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class CourseContents 
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="course_id")
	private CourseDetails course;
	
	private int topicid;
	private String topic;
	private String description;
	
	@Transient
	private List<MaterialsDto> materialsdto;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CourseDetails getCourseid() {
		return course;
	}
	public void setCourseid(CourseDetails course) {
		this.course = course;
	}
	public int getTopicid() {
		return topicid;
	}
	public void setTopicid(int topicid) {
		this.topicid = topicid;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<MaterialsDto> getMaterialsdto() {
		return materialsdto;
	}
	public void setMaterialsdto(List<MaterialsDto> materialsdto) {
		this.materialsdto = materialsdto;
	}
}
