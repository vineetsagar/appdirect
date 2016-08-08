package com.appdirect.http.client;

import com.appdirect.server.EventData;

public class EventDataReader extends EventReader {
	
	private EventData data;
	
	public EventDataReader(EventData data) {
		this.data = data;
	}

	@Override
	EventData getEventData() throws Exception {
		return data;
	}
}
