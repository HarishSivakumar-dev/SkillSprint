package com.harish.quizapp.Model;

import java.time.LocalDateTime;
import com.harish.quizapp.enums.StatUpdateEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InstructorStatUpdate 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int instId;
	private StatUpdateEvent eventType;
	private int deltaValue;
	private Boolean proceeded;
	private LocalDateTime createdAt;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInstId() {
		return instId;
	}
	public void setInstId(int instId) {
		this.instId = instId;
	}
	public StatUpdateEvent getEventType() {
		return eventType;
	}
	public void setEventType(StatUpdateEvent eventType) {
		this.eventType = eventType;
	}
	public int getDeltaValue() {
		return deltaValue;
	}
	public void setDeltaValue(int deltaValue) {
		this.deltaValue = deltaValue;
	}
	public Boolean getProceeded() {
		return proceeded;
	}
	public void setProceeded(Boolean proceeded) {
		this.proceeded = proceeded;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
