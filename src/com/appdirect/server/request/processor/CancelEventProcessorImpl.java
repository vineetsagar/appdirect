package com.appdirect.server.request.processor;

import org.apache.log4j.Logger;

import com.appdirect.server.convertor.XmlToObject;
import com.appdirect.server.data.SubscriptionData;
import com.appdirect.server.event.data.EventData;
import com.appdirect.server.event.data.EventResult;
import com.appdirect.server.event.reader.EventDataReader;
import com.appdirect.server.store.DataStore;
import com.appdirect.server.store.InMemoryDataStore;

public class CancelEventProcessorImpl implements EventsProcessor {
	private static final Logger log = Logger.getLogger(CancelEventProcessorImpl.class);
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
			log.error("error in cancelling the subscription " + e.getMessage());
			return new EventResult(false, "", e.getMessage());
		}
	}
}
