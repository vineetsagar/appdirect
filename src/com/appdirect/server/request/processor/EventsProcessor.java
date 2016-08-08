package com.appdirect.server.request.processor;

import com.appdirect.server.EventData;
import com.appdirect.server.EventResult;

public interface EventsProcessor {

	EventResult process(EventData parser);
}
