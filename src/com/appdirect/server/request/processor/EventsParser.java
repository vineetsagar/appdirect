package com.appdirect.server.request.processor;

import java.util.Map;

import com.appdirect.server.event.data.EventData;

public interface EventsParser {

	public EventData parser(Map<String,String[]> parameters);
}
