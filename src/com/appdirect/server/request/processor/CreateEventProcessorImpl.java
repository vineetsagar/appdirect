package com.appdirect.server.request.processor;

import com.appdirect.http.client.EventDataReader;
import com.appdirect.server.EventData;
import com.appdirect.server.EventResult;
import com.appdirect.server.convertor.XmlToObject;
import com.appdirect.server.data.SubscriptionData;
import com.appdirect.server.store.DataStore;
import com.appdirect.server.store.InMemoryDataStore;
import com.appdirect.server.user.data.UserStatus;

public class CreateEventProcessorImpl implements EventsProcessor {

	@Override
	public EventResult process(EventData parser) {
		EventDataReader client = new EventDataReader(parser);
		try {
			
			XmlToObject xmlToObject = new XmlToObject(client.get());
			SubscriptionData subscriptionData = xmlToObject.get();	
			
			String openId = subscriptionData.getCreator().getUuid();
			DataStore store = InMemoryDataStore.getInstance();
			UserStatus status = new UserStatus();
			status.setActive(Boolean.TRUE);
			String uniqueIdentifier = store.addUser(openId,  status);
			return new EventResult(true, uniqueIdentifier);
		} catch (Exception e) {
			new EventResult(false, "", e.getMessage());
		}
		return  new EventResult(true, "");

	}
}
