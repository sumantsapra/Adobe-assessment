package com.adobe.assessment.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;
import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.assessment.core.models.EventRegistrationModel;
import com.adobe.assessment.core.models.EventRegistrationResponse;
import com.adobe.assessment.core.utils.CommonUtil;
import com.adobe.assessment.core.utils.Constants;
import com.adobe.assessment.core.utils.ValidationUtil;

@Component(service={Servlet.class}, 
	property={"sling.servlet.methods=post", "sling.servlet.paths=/bin/eventRegistration"})
public class EventRegistration extends SlingAllMethodsServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(EventRegistration.class);
	
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		
		EventRegistrationResponse registrationResponse = new EventRegistrationResponse(); 
		String redirectURL = "/content/eventRegistration/au/en/home.html";
		String formData = StringUtils.EMPTY;
		try {
			
			Boolean validRequest = ValidationUtil.validateRequest(request.getParameterMap());
			log.info("Json request after validation: {}" ,validRequest);
			
			
			
			if(validRequest) {
				EventRegistrationModel eventModel = request.adaptTo(EventRegistrationModel.class);
				
				saveDataInJCR(eventModel,request);
				redirectURL = eventModel.getRedirectURL();
				formData = eventModel.getFormStart();
				registrationResponse.setRedirectURL(redirectURL);
				registrationResponse.setFormStart(eventModel.getFormStart());
				
				log.info("First name in request: {}" ,eventModel.getFirstName());
				
				String jsonData = CommonUtil.getJsonStringFromObject(eventModel);
				log.info("jsonData: {}" ,jsonData);
				registrationResponse.setStatusCode(200);
				registrationResponse.setError(Constants.SUCESS_RESPONSE);
				registrationResponse.setMessage(Constants.MESSAGE_SUCESS_RESPONSE);
				LocalDateTime now = LocalDateTime.now();
				registrationResponse.setTimestamp(now.toString());
			} else {
				registrationResponse.setStatusCode(400);
				registrationResponse.setError(Constants.ERROR_BAD_RESPONSE);
				registrationResponse.setMessage(Constants.MESSAGE_BAD_RESPONSE);
				LocalDateTime now = LocalDateTime.now();
				registrationResponse.setTimestamp(now.toString());
				registrationResponse.setRedirectURL(formData);
			}
			
		} catch (IOException ioException) {
			log.error("IO exception occured" ,ioException);
			registrationResponse.setStatusCode(500);
			registrationResponse.setError(Constants.ERROR_EXCEPTION_RESPONSE);
			registrationResponse.setMessage(ioException.getMessage());
			LocalDateTime now = LocalDateTime.now();
			registrationResponse.setTimestamp(now.toString());
			registrationResponse.setRedirectURL(formData);
		} catch (PathNotFoundException pathNotFoundException) {
			log.error("IO exception occured" ,pathNotFoundException);
			registrationResponse.setStatusCode(500);
			registrationResponse.setError(Constants.ERROR_EXCEPTION_RESPONSE);
			registrationResponse.setMessage(pathNotFoundException.getMessage());
			LocalDateTime now = LocalDateTime.now();
			registrationResponse.setTimestamp(now.toString());
			registrationResponse.setRedirectURL(formData);
		} catch (ItemExistsException itemExistsException) {
			log.error("IO exception occured" ,itemExistsException);
			registrationResponse.setStatusCode(500);
			registrationResponse.setError(Constants.ERROR_EXCEPTION_RESPONSE);
			registrationResponse.setMessage(itemExistsException.getMessage());
			LocalDateTime now = LocalDateTime.now();
			registrationResponse.setTimestamp(now.toString());
			registrationResponse.setRedirectURL(formData);
		} catch (VersionException versionException) {
			log.error("IO exception occured" ,versionException);
			registrationResponse.setStatusCode(500);
			registrationResponse.setError(Constants.ERROR_EXCEPTION_RESPONSE);
			registrationResponse.setMessage(versionException.getMessage());
			LocalDateTime now = LocalDateTime.now();
			registrationResponse.setTimestamp(now.toString());
			registrationResponse.setRedirectURL(formData);
		} catch (ConstraintViolationException constraintViolationException) {
			log.error("IO exception occured" ,constraintViolationException);
			registrationResponse.setStatusCode(500);
			registrationResponse.setError(Constants.ERROR_EXCEPTION_RESPONSE);
			registrationResponse.setMessage(constraintViolationException.getMessage());
			LocalDateTime now = LocalDateTime.now();
			registrationResponse.setTimestamp(now.toString());
			registrationResponse.setRedirectURL(formData);
		} catch (LockException lockException) {
			log.error("IO exception occured" ,lockException);
			registrationResponse.setStatusCode(500);
			registrationResponse.setError(Constants.ERROR_EXCEPTION_RESPONSE);
			registrationResponse.setMessage(lockException.getMessage());
			LocalDateTime now = LocalDateTime.now();
			registrationResponse.setTimestamp(now.toString());
			registrationResponse.setRedirectURL(formData);
		} catch (RepositoryException repositoryException) {
			log.error("IO exception occured" ,repositoryException);
			registrationResponse.setStatusCode(500);
			registrationResponse.setError(Constants.ERROR_EXCEPTION_RESPONSE);
			registrationResponse.setMessage(repositoryException.getMessage());
			LocalDateTime now = LocalDateTime.now();
			registrationResponse.setTimestamp(now.toString());
			registrationResponse.setRedirectURL(formData);
		}
		
		response.setContentType("application/json");
		//response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(registrationResponse.getStatusCode());
		
		try {
			//response.sendRedirect(registrationResponse.getRedirectURL());
			PrintWriter out = response.getWriter();
			out.print(CommonUtil.getJsonStringFromObject(registrationResponse));
			out.flush();
		} catch (IOException ioException) {
			log.error("IO exception occured" ,ioException);
		}
		
	}
	
	private void saveDataInJCR(EventRegistrationModel eventModel, SlingHttpServletRequest request) throws PathNotFoundException, ItemExistsException, VersionException, ConstraintViolationException, LockException, RepositoryException {
		ResourceResolver resourceResolver = request.getResourceResolver();
		Session session = resourceResolver.adaptTo(Session.class);
		
		Node jcrNode = session.getNode("/content/eventRegistration/au/en/home/event-registration/jcr:content");
		
		if (null != jcrNode) {
			Node formNode;
			if (jcrNode.hasNode("FormData")) {
				formNode = jcrNode.getNode("FormData");
			} else {
				formNode = jcrNode.addNode("FormData");
			}
			LocalDateTime now = LocalDateTime.now();
			Node dataNode = formNode.addNode(now.toString().replaceAll(":", "-").replaceAll("/.", "-"));
			dataNode.setProperty("firstName", eventModel.getFirstName());
			dataNode.setProperty("lastName", eventModel.getLastName());
			dataNode.setProperty("email", eventModel.getEmail());
			dataNode.setProperty("ID", eventModel.getId());
			log.info("Set the data in JCR Node : {}", dataNode.getPath());
			
			session.save();
		}
		
	}
}