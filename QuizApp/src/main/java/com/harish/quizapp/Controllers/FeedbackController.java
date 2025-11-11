package com.harish.quizapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harish.quizapp.Model.FeedbackTable;
import com.harish.quizapp.Service.FeedbackService;

@RestController
@RequestMapping("/app")
public class FeedbackController
{
	
	@Autowired
	private FeedbackService fs;
	
	@PostMapping("/user/feedback/{courseid}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> submitFeedback(@RequestBody FeedbackTable ft, @PathVariable int courseid)
	{
		return fs.submitFeedback(ft, courseid);
	}
	
	

}
