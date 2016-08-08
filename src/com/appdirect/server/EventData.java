package com.appdirect.server;

import com.appdirect.server.data.SubscriptionData;

/** 
 * Contains all data parsed from the request from AppDirect
 * @author vineetsagar
 *
 */
public class EventData {
	
	private SubscriptionData data;
	
	private String eventUrl;
	
	
	

	public EventData(String eventUrl) {
		this.eventUrl = eventUrl;
	}
	
	public EventData() {
	}
	
	
	public String getEventUrl() {
		return eventUrl;
	}

	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}

	public SubscriptionData getData() {
		return data;
	}

	public void setData(SubscriptionData data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventData other = (EventData) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
}
