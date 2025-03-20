package com.adobe.assessment.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
	
	private static Logger log = LoggerFactory.getLogger(Constants.class);
	private Constants() {
		log.info("Private constructor");
		
	}
	
	public static final String FIRST_NAME="firstName";
	public static final String LAST_NAME="LastName";
	public static final String EMAIL="email";
	public static final String ID="id";
	public static final String REDIRECT=":redirect";
	public static final String FORMSTART=":formstart";
	
	public static final String ERROR_EXCEPTION_RESPONSE="Something wend wrong";
	public static final String ERROR_BAD_RESPONSE="Form data submitted is not valid";
	public static final String MESSAGE_BAD_RESPONSE="Please see our policy to enter valid data";
	
	public static final String SUCESS_RESPONSE="Sucess";
	public static final String MESSAGE_SUCESS_RESPONSE="Form have successfully been submitted";

}
