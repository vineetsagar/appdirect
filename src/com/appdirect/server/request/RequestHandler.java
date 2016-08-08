package com.appdirect.server.request;

import java.util.Map;

import com.appdirect.server.JSONResponse;

public interface RequestHandler {

	JSONResponse handle( RequestType type, Map<String,String> headerArguments ,  Map<String,String[]> queryParameters);

}
