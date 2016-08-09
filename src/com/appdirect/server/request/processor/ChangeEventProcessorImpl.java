package com.appdirect.server.request.processor;

import org.apache.log4j.Logger;

import com.appdirect.server.convertor.XmlToObject;
import com.appdirect.server.data.SubscriptionData;
import com.appdirect.server.event.data.EventData;
import com.appdirect.server.event.data.EventResult;
import com.appdirect.server.event.reader.EventDataReader;
import com.appdirect.server.store.DataStore;
import com.appdirect.server.store.InMemoryDataStore;
import com.appdirect.server.user.data.UserData;

public class ChangeEventProcessorImpl implements EventsProcessor {
	
	private static final Logger log = Logger.getLogger(ChangeEventProcessorImpl.class);
	
	@Override
	public EventResult process(EventData parser) {

		EventDataReader client = new EventDataReader(parser);
		try {
			XmlToObject xmlToObject = new XmlToObject(client.get());
			SubscriptionData subscriptionData = xmlToObject.get();
			String openId = subscriptionData.getCreator().getOpenId();
			DataStore store = InMemoryDataStore.getInstance();
			UserData user = store.getUserByOpenId(openId);
			if(user == null){
				log.error("Error in changing subscription. User doesn't exist for given open Id ");
				return new EventResult(false, openId , "Subscription does not exist.");
			}

			// get the edition code from the data model
			String editionCode = subscriptionData.getPayload().getOrder().getEditionCode();
			user.setEditionCode(editionCode);
			boolean updateUser = store.updateUser(openId, user);
			if(updateUser){
				return new EventResult(true, openId , "Subscription updated successfully");	
			}else{
				return new EventResult(false, openId , "Failed to update subscription");
			}
		} catch (Exception e) {
			log.error("Error in changing subscription "+ e.getMessage());
			return new EventResult(false, "", e.getMessage());
		}
	}
}
