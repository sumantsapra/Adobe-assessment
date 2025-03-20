package com.adobe.assessment.core.utils;



import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public static <T> String getJsonStringFromObject (final T comObject) throws JsonProcessingException {
		return(null != comObject) ?
				objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(comObject) : StringUtils.EMPTY;
	}

}
