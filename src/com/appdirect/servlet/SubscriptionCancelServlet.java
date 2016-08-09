package com.appdirect.servlet;

import java.io.IOException;

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
public class SubscriptionCancelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubscriptionCancelServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestHandler impl = new RequestHandlerImpl();
    	JSONResponse jsonResponse = impl.handle(RequestType.CANCEL, request.getParameterMap());
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	ObjectMapper mapper = new ObjectMapper();
    	response.getWriter().write(mapper.writeValueAsString(jsonResponse));
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
