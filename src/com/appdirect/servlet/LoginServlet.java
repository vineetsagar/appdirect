package com.appdirect.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;

import com.appdirect.server.service.RegistrationService;

public class LoginServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(final HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	/*
    	 * 1. Fetch the open Id form the request
    	 * 2. If Id not present then redirect user to error page
    	 * 3. If Open Id present then verify it
    	 * 4. On Success redirect user to success page
    	 */
    	String userSuppliedIdentifier = request.getParameter("openid_url");
    	if(userSuppliedIdentifier!=null){
	        // Delegate to Open ID code
	        DiscoveryInformation discoveryInformation = RegistrationService.performDiscoveryOnUserSuppliedIdentifier(userSuppliedIdentifier);
	        // store the discovery information in session
	        HttpSession session = request.getSession();
	        session.setAttribute("discovered", discoveryInformation);
	        // Create the AuthRequest
	        AuthRequest authRequest = RegistrationService.createOpenIdAuthRequest(discoveryInformation, RegistrationService.getReturnToUrl());
	        // redirect the user to their provider for authentication
	        response.sendRedirect(authRequest.getDestinationUrl(true));
    	}else{
    		response.getWriter().write("error in obtaining openID");
    	}
    }
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
