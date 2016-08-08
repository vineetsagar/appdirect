package com.appdirect.server.filter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;

import com.appdirect.http.client.HTTPClient;
import com.appdirect.server.JSONResponse;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthRequest;
import com.sun.jersey.oauth.signature.OAuthSecrets;
import com.sun.jersey.oauth.signature.OAuthSignature;
import com.sun.jersey.oauth.signature.OAuthSignatureException;

/**
 * This call will verify OAuth signature in all the request coming from AppDirect server 
 * @author vineetsagar
 *
 */
public class OAuthSignatureVerfier implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
	      boolean verifyRequestJersey = verifyRequestJersey((HttpServletRequest)request);
	      
	      if(!verifyRequestJersey){
	    	  // throw exception with error code
	    	 	JSONResponse jsonResponse = new JSONResponse();
	    	 	jsonResponse.setSuccess(verifyRequestJersey);
	    	 	jsonResponse.setErrorCode(1);
	    	 	jsonResponse.setMessage("Error in verifying the OAuth Signature in the request.");
	        	response.setContentType("application/json");
	        	response.setCharacterEncoding("UTF-8");
	        	ObjectMapper mapper = new ObjectMapper();
	        	response.getWriter().write(mapper.writeValueAsString(jsonResponse));
	        	return;
	      }
	      
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	
	private boolean verifyRequestJersey(final HttpServletRequest sRequest){
		OAuthSecrets secrets = new OAuthSecrets();
		
		secrets.setConsumerSecret(HTTPClient.CONSUMER_SECRET);


		OAuthRequest request = new OAuthRequest(){

			@Override
			public void addHeaderValue(String arg0, String arg1) throws IllegalStateException {
				System.out.println("add header value get called with " +  arg0 + " <>" + arg1);
			}

			@Override
			public List<String> getHeaderValues(String arg0) {
				Enumeration<String> headers = sRequest.getHeaders(arg0);
				List<String> list = new ArrayList<String>();
				while(headers.hasMoreElements()){
					list.add(headers.nextElement());
				}
				return list;
			}

			@Override
			public Set<String> getParameterNames() {
				Set<String> set = new HashSet<String>();
				Enumeration<String> parameterNames = sRequest.getParameterNames();
				while(parameterNames.hasMoreElements()){
					set.add(parameterNames.nextElement());
				}
				return set;
			}

			@Override
			public List<String> getParameterValues(String arg0) {
				String[] parameterValues = sRequest.getParameterValues(arg0);
				return Arrays.asList(parameterValues);
			}

			@Override
			public String getRequestMethod() {
				return sRequest.getMethod();
			}

			@Override
			public URL getRequestURL() {
				try {
					return new URL(sRequest.getRequestURL().toString());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		
		OAuthParameters parameters = new OAuthParameters();
		OAuthParameters requestParameters = parameters.readRequest(request);

		if (requestParameters.size() == 0) {
	        System.out.println(" no paramets found");
	        /**
	         * this implies that oAuth Authroization header is missing in a request
	         * therefore we should throw an exception
	         */
	    }
		
		try {
			
			return OAuthSignature.verify(request, requestParameters, secrets);

		} catch (OAuthSignatureException e) {
			e.printStackTrace();
			/**
			 * log the exception
			 */
		}
		
		return false;
	}

}
