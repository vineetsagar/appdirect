package com.appdirect.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.appdirect.server.JSONResponse;
import com.appdirect.server.request.RequestHandler;
import com.appdirect.server.request.RequestHandlerImpl;
import com.appdirect.server.request.RequestType;

/**
 * Servlet implementation class SubscriptionServlet
 */
public class SubcriptionCancelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubcriptionCancelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestHandler impl = new RequestHandlerImpl();
    	JSONResponse jsonResponse = impl.handle(RequestType.CANCEL, getRequestHeaderMap(request) , request.getParameterMap());
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	ObjectMapper mapper = new ObjectMapper();
    	response.getWriter().write(mapper.writeValueAsString(jsonResponse));
    }

	private Map<String, String> getRequestHeaderMap(HttpServletRequest request) {
		Map<String,String> headerMaps = new HashMap<String,String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()){
			String nextElement = headerNames.nextElement();
			String header = request.getHeader(nextElement);
			headerMaps.put(nextElement, header);
		}
		return headerMaps;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
