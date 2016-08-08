package com.appdirect.server.request.processor;

import java.util.Map;

import com.appdirect.server.EventData;
import com.appdirect.server.request.RequestType;

public class RequestFactoryImpl extends EventFactory {

	private static final String EVENT_URL_PARAMETER = "eventUrl";

	@Override
	public EventsParser getEventURL(RequestType type){

		switch (type) {
		case CREATE:
		case CANCEL:

			break;

		default:
			break;
		}
		return new EventsParser() {
			
			@Override
			public EventData parser(Map<String,String[]> parameters) {
				
				if(parameters!=null){
					if(parameters.containsKey(EVENT_URL_PARAMETER)){
						
						String[] strings = parameters.get(EVENT_URL_PARAMETER);
						if(strings!=null && strings.length > 0){
							String eventUrl = strings[0];
							return new EventData(eventUrl);
						}
					}
				}
				return null;
			}
		};
	
	}
	
	@Override
	public EventsProcessor getEventsProcessor(RequestType type) {
		switch (type) {
		case CREATE:
			return new CreateEventProcessorImpl();
		case CANCEL:;
			return new CancelEventProcessorImpl();
		default:
			break;
		}
		return null;
	}

}
