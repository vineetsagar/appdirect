package com.appdirect.server.event.reader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.appdirect.http.client.HTTPClient;
import com.appdirect.server.event.data.EventData;

public abstract class EventReader {
	
	private HTTPClient httpClient = new HTTPClient();
	
	abstract EventData getEventData()throws Exception;
	
	public String get() throws Exception {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<String,Object> params = new LinkedHashMap();
        params.put("method", "GET");
        HttpURLConnection conn = httpClient.get(getEventData().getEventUrl(), params);
        int responseCode = conn.getResponseCode();
		System.out.println("Response Code : " + responseCode);
		if (responseCode == 200){
			StringBuilder responseBuilder = new StringBuilder();
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String input;
			while ((input = in.readLine()) != null) {
				responseBuilder.append(input);
			}
			in.close();
			
			String response = responseBuilder.toString();
			return response;
		}
		throw new  Exception("Error in reading data from event " + getEventData().getEventUrl());
	}
}
