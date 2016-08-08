package com.appdirect.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.ParameterList;

import com.appdirect.server.service.RegistrationModel;
import com.appdirect.server.service.RegistrationService;

public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(final HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// extract the parameters from the authentication response
    	// (which comes in as a HTTP request from the OpenID provider)
    	String userAccountId = request.getParameter("accountId");
    	
    	System.out.println("account id " + userAccountId);
    	
    	ParameterList openidResp = new ParameterList(request.getParameterMap());
    	
    	System.out.println("all open id resp "+ openidResp);

    	// retrieve the previously stored discovery information
    	DiscoveryInformation discovered = (DiscoveryInformation) request.getAttribute("discovered");

    	// extract the receiving URL from the HTTP request
    	StringBuffer receivingURL = request.getRequestURL();
    	String queryString = request.getQueryString();

    	if (queryString != null && queryString.length() > 0)
    		receivingURL.append("?").append(request.getQueryString());

    	RegistrationModel processReturn = RegistrationService.processReturn(discovered, openidResp, receivingURL.toString());
    	response.setCharacterEncoding("UTF-8");
    	if (processReturn != null){
    		// success, use the verified identifier to identify the user
    		// check if user has access to the app or not 
    		response.getWriter().write("Welcome " + processReturn.getFullName() +"to app dashboard ");
    	}
    	else{
    		// OpenID authentication failed    		
    		response.getWriter().write("Ah! Unable to access your request.");
    	}
    }
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}



}
