package com.appdirect.server.request.processor;

import com.appdirect.http.client.EventDataReader;
import com.appdirect.server.EventData;
import com.appdirect.server.EventResult;
import com.appdirect.server.convertor.XmlToObject;
import com.appdirect.server.data.SubscriptionData;
import com.appdirect.server.store.DataStore;
import com.appdirect.server.store.InMemoryDataStore;
import com.appdirect.server.user.data.UserData;

public class CreateEventProcessorImpl implements EventsProcessor {

	/**
	 * The main purpose of this method is to read the create event url and parse the event response from xml to pojo object. Once
	 * verifying all information pass the information to DataStore for creating a user
	 */
	@Override
	public EventResult process(EventData parser) {
		EventDataReader client = new EventDataReader(parser);
		try {
			XmlToObject xmlToObject = new XmlToObject(client.get());
			SubscriptionData subscriptionData = xmlToObject.get();	
			/**
			 * Based on subscritionData we can create a user model
			 * As of now if subscription data is not null i'm using this 
			 * account is active
			 */
			if(subscriptionData!=null){
				DataStore store = InMemoryDataStore.getInstance();
				String editionCode = subscriptionData.getPayload().getOrder().getEditionCode();
				String openId = subscriptionData.getCreator().getOpenId();
				UserData status = new UserData();
				status.setActive(Boolean.TRUE);
				status.setEditionCode(editionCode);
				String uniqueIdentifier = store.addUser(  status, openId);
				return new EventResult(true, uniqueIdentifier);
			}else{
				return new EventResult(false, "", "Error in assigning app");
			}
		} catch (Exception e) {
			return new EventResult(false, "", e.getMessage());
		}
	}
}
