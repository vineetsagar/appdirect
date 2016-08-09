package com.appdirect.server.request.processor;

import java.util.Map;

import com.appdirect.server.event.data.EventData;

public interface EventsParser {

	/**
	 * Search for event url query parameter in the given map if found then it will wrap the event url in EventData object
	 * @param parameters
	 * @return
	 */
	public EventData parser(Map<String,String[]> parameters);
}
