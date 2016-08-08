package com.appdirect.server.request.processor;

import com.appdirect.http.client.EventDataReader;
import com.appdirect.server.EventData;
import com.appdirect.server.EventResult;
import com.appdirect.server.convertor.XmlToObject;
import com.appdirect.server.data.SubscriptionData;
import com.appdirect.server.store.DataStore;
import com.appdirect.server.store.InMemoryDataStore;
import com.appdirect.server.user.data.UserData;

public class ChangeEventProcessorImpl implements EventsProcessor {

	@Override
	public EventResult process(EventData parser) {

		EventDataReader client = new EventDataReader(parser);
		try {
			XmlToObject xmlToObject = new XmlToObject(client.get());
			SubscriptionData subscriptionData = xmlToObject.get();
			String openId = subscriptionData.getCreator().getOpenId();
			System.out.println("openid " + openId);
			DataStore store = InMemoryDataStore.getInstance();
			UserData user = store.getUserByOpenId(openId);
			if(user == null){
				return new EventResult(false, openId , "Subscription does not exist.");
			}

			// get the edition code from the data model
			String editionCode = subscriptionData.getPayload().getOrder().getEditionCode();
			user.setEditionCode(editionCode);
			System.out.println("setting edition code" + editionCode + " for open id " + openId);
			boolean updateUser = store.updateUser(openId, user);
			if(updateUser){
				return new EventResult(true, openId , "Subscription updated successfully");	
			}else{
				return new EventResult(false, openId , "Failed to update subscription");
			}
		} catch (Exception e) {
			return new EventResult(false, "", e.getMessage());
		}
	}
}
