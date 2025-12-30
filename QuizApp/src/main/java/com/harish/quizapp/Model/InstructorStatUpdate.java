package com.harish.quizapp.Model;

import java.time.LocalDate;

import com.harish.quizapp.enums.StatUpdateEvent;

public class InstructorStatUpdate 
{
	private int instId;
	private StatUpdateEvent eventType;
	private int deltaValue;
	private Boolean proceeded;
	private LocalDate createdAt;
	
	
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
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

}
