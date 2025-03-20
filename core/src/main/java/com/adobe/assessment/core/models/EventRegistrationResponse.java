package com.adobe.assessment.core.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventRegistrationResponse {
	
	private static Logger log = LoggerFactory.getLogger(EventRegistrationResponse.class);
	
	private int statusCode;
	private String error;
	private String message;
	private String timestamp;
	private String redirectURL;
	private String formStart;
	
	public int getStatusCode() {
		return statusCode;
	}
	public EventRegistrationResponse() {
		log.info("Private constructor");
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getRedirectURL() {
		return redirectURL;
	}
	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}
	public String getFormStart() {
		return formStart;
	}
	public void setFormStart(String formStart) {
		this.formStart = formStart;
	}
	
}
