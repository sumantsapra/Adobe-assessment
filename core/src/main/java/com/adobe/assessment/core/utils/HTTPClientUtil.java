package com.adobe.assessment.core.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPClientUtil {
	
	private static Logger log = LoggerFactory.getLogger(HTTPClientUtil.class);
	
	public String httPRequestPOST (final String endpointURL, final Map<String,String> paramValuePairs) throws ClientProtocolException, IOException {
		
		String result = StringUtils.EMPTY;
		CloseableHttpClient httpClient = createHttpClient();
		if(null == httpClient) {
			log.error("Failed to create HttpClient");
			return StringUtils.EMPTY;
		}
		
		result = buildRequest(httpClient, RequestBuilder.post(endpointURL),paramValuePairs);
		
		return result;
	}

	private String buildRequest(CloseableHttpClient httpClient, RequestBuilder post,
			Map<String, String> paramValuePairs) throws ClientProtocolException, IOException {
		
		setParameterForRequest(post, paramValuePairs);
		
		HttpUriRequest request = post.build();
		
		CloseableHttpResponse response = httpClient.execute(request);
		
		if(null == response) {
			log.error("Failed to get response for the HTTP Post request");
			return StringUtils.EMPTY;
		}
		
		log.info("Response Status: {}",response.getStatusLine());
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
	}
	
	private static void setParameterForRequest(RequestBuilder builder, Map<String, String> paramValuePairs) {
		
		log.info("Setting up parameters for Request");
		if(null != paramValuePairs) {
			paramValuePairs.entrySet().forEach(
					paramValuePair -> builder.addParameter(new BasicNameValuePair(paramValuePair.getKey(), paramValuePair.getValue())));
		}
	}

	private static CloseableHttpClient createHttpClient() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		return httpclient;
	}
}
