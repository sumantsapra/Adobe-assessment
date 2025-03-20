package com.adobe.assessment.core.servlets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
@MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventRegistrationTest {
	
	private MockSlingHttpServletRequest request;
    private MockSlingHttpServletResponse response;
    
    EventRegistration eventRegistration;
    private AemContext ctx;
    
    @Mock
    Session session;
    
    @Mock
    ResourceResolver resourceResolver;
    
    @Mock
    Node jcrNode;
    
    @Mock
    Node formNode;
    
    @BeforeEach
    public void setupData() {
    	eventRegistration = new EventRegistration();
    	ctx = new AemContext(ResourceResolverType.JCR_MOCK);
    	
    	request = ctx.request();
        response = ctx.response();
        resourceResolver = request.getResourceResolver();
        
        ctx.registerAdapter(ResourceResolver.class, Session.class,session);
       
    }
    
    
    @Test
    @Order(1)
    void doPostBadRequest() {
    	eventRegistration.doPost(request, response);
    	assertEquals(HttpServletResponse.SC_BAD_REQUEST, ctx.response().getStatus());
    }
    
    @Test
    @Order(2)
    void doPostInternalServerError() {
    	final Map<String, Object> parameterMap = new HashMap<String, Object>();
    	parameterMap.put("firstName", "Maria");
    	parameterMap.put("LastName", "Kassu");
    	parameterMap.put("email", "Jerry80@hotmail.com");
    	parameterMap.put("id", "1");
    	parameterMap.put(":redirect", "/content/eventRegistration/au/en/home/thank-you-for-registerting.html");
    	parameterMap.put(":formstart", "/content/eventRegistration/au/en/home/event-registration/jcr:content/root/container/container/customcontainer");
    	
    	request.setParameterMap(parameterMap);
   
    	
    	eventRegistration.doPost(request, response);
    	
    	assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ctx.response().getStatus());
    }
    
    @Test
    @Order(3)
    void doPostTest() throws PathNotFoundException, RepositoryException {
    	final Map<String, Object> parameterMap = new HashMap<String, Object>();
    	parameterMap.put("firstName", "Maria");
    	parameterMap.put("LastName", "Kassu");
    	parameterMap.put("email", "Jerry80@hotmail.com");
    	parameterMap.put("id", "1");
    	parameterMap.put(":redirect", "/content/eventRegistration/au/en/home/thank-you-for-registerting.html");
    	parameterMap.put(":formstart", "/content/eventRegistration/au/en/home/event-registration/jcr:content/root/container/container/customcontainer");
    	
    	request.setParameterMap(parameterMap);
    	
    	eventRegistration.doPost(request, response);
    
    	when(session.getNode(any())).thenReturn(jcrNode);
    	when(jcrNode.addNode("FormData")).thenReturn(formNode);
    	assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ctx.response().getStatus());
    	
    }
    

}
