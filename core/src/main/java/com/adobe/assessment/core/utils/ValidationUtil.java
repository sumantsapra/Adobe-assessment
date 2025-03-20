package com.adobe.assessment.core.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationUtil {

	private static Logger log = LoggerFactory.getLogger(ValidationUtil.class);

	public static Boolean validateRequest(Map<String, String[]> parameterMap) {

		Boolean valid = false;
		Boolean validField = true;
		log.info("paramter map: {}",parameterMap );

		if (null != parameterMap) {
			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
				log.info("Key ,{} and Value {}",entry.getKey(), entry.getValue()[0]);
				if (entry.getKey().equalsIgnoreCase(Constants.FIRST_NAME)) {
					log.info("First Name : {}", entry.getValue()[0]);
					valid = isValidString(entry.getValue()[0]) && isSafeString(entry.getValue()[0]);
					validField = valid;
				}
				if (entry.getKey().equalsIgnoreCase(Constants.LAST_NAME)) {
					log.info("Last Name : {}", entry.getValue()[0]);
					valid = isValidString(entry.getValue()[0]) && isSafeString(entry.getValue()[0]);
					validField = valid;
				}
				if (entry.getKey().equalsIgnoreCase(Constants.EMAIL)) {
					log.info("Email : {}", entry.getValue()[0]);
					valid = isValidEmail(entry.getValue()[0]) && isSafeString(entry.getValue()[0]);
					validField = valid;
				}
				if (entry.getKey().equalsIgnoreCase(Constants.ID)) {
					log.info("ID : {}", entry.getValue()[0]);
					valid = isValidNumber(entry.getValue()[0]) && isSafeString(entry.getValue()[0]);
					validField = valid;
				}
				if(!validField) {
					return false;
				}
			}
		}
		return valid;
	}

	public static boolean isValidString(String name) {
		try {
			log.info("Name entered, {}", name);
			final String NAME_PATTERN = "^[A-Z][a-zA-Z ]*$";

			Pattern pattern = Pattern.compile(NAME_PATTERN);
			if (name == null) {
				return true;
			}
			Matcher matcher = pattern.matcher(name);
			log.info("String matches,{}", matcher.matches());
			return matcher.matches();
		} catch (Exception e) {
			log.error("Exception occurred while validating ", e);
		}
		return false;
	}

	public static boolean isValidEmail(String email) {
		try {
			log.info("Email entered, {}", email);
			final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
			Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			if (email == null) {
				return true;
			}
			Matcher matcher = pattern.matcher(email);
			log.info("Email matches,{}", matcher.matches());
			return matcher.matches();
		} catch (Exception e) {
			log.error("Exception occurred while validating ", e);
		}
		return false;
	}

	public static boolean isValidNumber(String input) {
		try {
			final String NUMBER_PATTERN = "^\\d+$";
			Pattern pattern = Pattern.compile(NUMBER_PATTERN);
			if (input == null || input.equalsIgnoreCase("")) {
				return true;
			}
			log.info("Number entered, {}", input);
			Matcher matcher = pattern.matcher(input);
			log.info("Number matches,{}", matcher.matches());
			return matcher.matches();
		} catch (Exception e) {
			log.error("Exception occurred while validating ", e);
		}
		return false;
	}

	public static boolean isSafeString(String input) {
		try {
			log.info("String entered, {}", input);
			final String UNSAFE_PATTERN = "<.*?>|<script.*?>.*?</script>";

			Pattern pattern = Pattern.compile(UNSAFE_PATTERN, Pattern.CASE_INSENSITIVE);
			if (input == null) {
				return true;
			}
			Matcher matcher = pattern.matcher(input);
			return !matcher.find();
		} catch (Exception e) {
			log.error("Exception occurred while validating ", e);
		}
		return false;
	}

}
