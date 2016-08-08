package com.appdirect.server.request.processor;

import com.appdirect.http.client.EventDataReader;
import com.appdirect.server.EventData;
import com.appdirect.server.EventResult;
import com.appdirect.server.convertor.XmlToObject;
import com.appdirect.server.data.SubscriptionData;
import com.appdirect.server.store.DataStore;
import com.appdirect.server.store.InMemoryDataStore;

public class CancelEventProcessorImpl implements EventsProcessor {

	@Override
	public EventResult process(EventData parser) {
		
		EventDataReader client = new EventDataReader(parser);
		try {
			XmlToObject xmlToObject = new XmlToObject(client.get());
			SubscriptionData subscriptionData = xmlToObject.get();
			String accountIdentifier = subscriptionData.getPayload().getAccount().getAccountIdentifier();
			DataStore store = InMemoryDataStore.getInstance();
			store.deleteUser(accountIdentifier);
			return new EventResult(true, accountIdentifier , "Cancel subscription successfully");
		} catch (Exception e) {
			return new EventResult(false, "", e.getMessage());
		}
	}
}
