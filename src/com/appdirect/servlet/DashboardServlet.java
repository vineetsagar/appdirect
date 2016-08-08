package com.appdirect.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.ParameterList;

import com.appdirect.server.service.RegistrationModel;
import com.appdirect.server.service.RegistrationService;
import com.appdirect.server.store.DataStore;
import com.appdirect.server.store.InMemoryDataStore;
import com.appdirect.server.user.data.UserData;

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
    	
    	
    	ParameterList openidResp = new ParameterList(request.getParameterMap());
    	
    	// retrieve the previously stored discovery information
    	DiscoveryInformation discovered = (DiscoveryInformation) request.getSession().getAttribute("discovered");
    	Identifier claimedIdentifier = discovered.getClaimedIdentifier();
    	String identifier = claimedIdentifier.getIdentifier();
    	
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
    		DataStore store = InMemoryDataStore.getInstance();
    		boolean userExist = store.isUserExistbyOpenId(identifier);
    		if(userExist){
    			UserData user = store.getUserByOpenId(identifier);
    			if(user.isActive()){
    				/**
    				 * Instead of redirecting user to success page i'm just writing success test on response object.
    				 */
    				response.getWriter().write("Welcome " + processReturn.getFullName() +" to app dashboard. Your are currently having " + user.getEditionCode() + " subscription");
    			}else{
    				/**
    	    		 * We should be redirecting user to error page with message "Please renew subscription to access this service"
    	    		 */
    				response.getWriter().write("Please renew your subscription to access this service");
    			}
    			return;
    		}
    		/**
    		 * We should be redirecting user to error page that you are not allowed to access this service.
    		 */
    		response.getWriter().write("You are not allowed to access this service");
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
