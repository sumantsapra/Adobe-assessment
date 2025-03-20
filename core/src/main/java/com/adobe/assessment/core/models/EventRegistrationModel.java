package com.adobe.assessment.core.models;

import java.util.Map;

import javax.annotation.PostConstruct;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.assessment.core.utils.Constants;


@Model(adaptables = SlingHttpServletRequest.class,
	defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class EventRegistrationModel {
	private static Logger log = LoggerFactory.getLogger(EventRegistrationModel.class);
	
	
	public EventRegistrationModel() {
		super();
	}

	@ValueMapValue
	private String firstName;
	
	@ValueMapValue
	private String lastName;
	
	@ValueMapValue
	private String email;
	
	@ValueMapValue
	private String id;
	
	@ValueMapValue
	private String redirectURL;
	
	@ValueMapValue
	private String formStart;
	
	@SlingObject
    SlingHttpServletRequest slingRequest;
	
	@PostConstruct
	protected void init() {
		Map<String, String[]> parameterMap = slingRequest.getParameterMap();
	    
	    if(null != parameterMap) {
			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
				if(entry.getKey().equalsIgnoreCase(Constants.FIRST_NAME)) {
					log.info("First Name : {}",entry.getValue()[0]);
					this.firstName = entry.getValue()[0];
				}
				if (entry.getKey().equalsIgnoreCase(Constants.LAST_NAME)){
					log.info("Last Name : {}",entry.getValue()[0] );
					this.lastName = entry.getValue()[0];
				}
				if (entry.getKey().equalsIgnoreCase(Constants.EMAIL)){
					log.info("Email : {}",entry.getValue()[0] );
					this.email = entry.getValue()[0];
				}
				if (entry.getKey().equalsIgnoreCase(Constants.ID)){
					log.info("ID : {}",entry.getValue()[0] );
					this.id = entry.getValue()[0];
				}
				if (entry.getKey().equalsIgnoreCase(Constants.REDIRECT)){
					log.info("ID : {}",entry.getValue()[0] );
					this.redirectURL = entry.getValue()[0];
				}
				if (entry.getKey().equalsIgnoreCase(Constants.FORMSTART)){
					log.info("ID : {}",entry.getValue()[0] );
					this.formStart = entry.getValue()[0].split("/jcr:content")[0]+".html";
				}
			}
		}
	}

	

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getId() {
		return id;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public String getFormStart() {
		return formStart;
	}
	
	
}
