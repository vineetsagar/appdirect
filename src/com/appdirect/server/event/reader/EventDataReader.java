package com.appdirect.server.event.reader;

import com.appdirect.server.event.data.EventData;

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
