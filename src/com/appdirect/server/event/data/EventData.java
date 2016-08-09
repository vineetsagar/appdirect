package com.appdirect.server;

import com.appdirect.server.data.SubscriptionData;

/** 
 * Contains all data parsed from the request from AppDirect
 * @author vineetsagar
 *
 */
public class EventData {
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventUrl == null) ? 0 : eventUrl.hashCode());
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
		if (eventUrl == null) {
			if (other.eventUrl != null)
				return false;
		} else if (!eventUrl.equals(other.eventUrl))
			return false;
		return true;
	}
}
