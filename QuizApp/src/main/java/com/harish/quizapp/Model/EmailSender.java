package com.harish.quizapp.Model;

import java.util.ArrayList;
import java.util.List;

public class EmailSender 
{
	private String subject;
	private String body;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	List<Integer> enroll=new ArrayList<Integer>();

	public List<Integer> getEnroll()
	{
		return enroll;
	}
	public void setEnroll(List<Integer> enroll)
	{
		this.enroll = enroll;
	}
	
		
}
