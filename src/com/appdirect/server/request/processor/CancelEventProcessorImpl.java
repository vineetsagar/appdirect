package com.appdirect.server.request.processor;

import com.appdirect.server.EventData;
import com.appdirect.server.EventResult;
import com.appdirect.server.data.SubscriptionData;
import com.appdirect.server.store.DataStore;
import com.appdirect.server.store.InMemoryDataStore;

public class CancelEventProcessorImpl implements EventsProcessor {

	@Override
	public EventResult process(EventData parser) {
		DataStore store = InMemoryDataStore.getInstance();
		SubscriptionData data = parser.getData();
		String uuid = data.getCreator().getUuid();
		String accountIdentifier = data.getPayload().getAccount().getAccountIdentifier();
		store.deleteUser(accountIdentifier, uuid);
		
		return  new EventResult(true, accountIdentifier);
	}
}
