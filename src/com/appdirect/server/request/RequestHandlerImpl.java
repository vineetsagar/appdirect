package com.appdirect.server.request;

import java.util.Map;

import com.appdirect.server.JSONResponse;
import com.appdirect.server.event.data.EventData;
import com.appdirect.server.event.data.EventResult;
import com.appdirect.server.request.processor.EventFactory;
import com.appdirect.server.request.processor.EventsParser;
import com.appdirect.server.request.processor.EventsProcessor;
import com.appdirect.server.request.processor.RequestFactoryImpl;

public class RequestHandlerImpl implements RequestHandler {

	
	
	private EventFactory requestFactory = new RequestFactoryImpl();
	
	
	@Override
	public JSONResponse handle(RequestType type, Map<String,String> headerArguments,  Map<String,String[]> queryParameters) {
		EventsParser parser = requestFactory.getEventURL(type);
		EventData eventData = parser.parser(queryParameters);
		EventsProcessor processor = requestFactory.getEventsProcessor(type);
		if(processor!=null){
			EventResult process = processor.process(eventData);
			if(process!=null && process.isSuccess()){
				/**
				 * all check are done, return success message
				 */
				return new JSONResponse(0, true, "success!!");
			}
		}
		// if all the process is done then return json response
		// if any failure then return failure response
		return new JSONResponse(0, false, "Failed to server the request");
	}

}
