package com.appdirect.server.request.processor;

import com.appdirect.server.request.RequestType;

public  abstract class EventFactory {
	public abstract EventsProcessor getEventsProcessor(RequestType type);
	public abstract EventsParser getEventURL(RequestType type);
}
