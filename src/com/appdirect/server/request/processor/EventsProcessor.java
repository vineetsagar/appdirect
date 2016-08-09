package com.appdirect.server.request.processor;

import com.appdirect.server.event.data.EventData;
import com.appdirect.server.event.data.EventResult;

public interface EventsProcessor {

	EventResult process(EventData parser);
}
