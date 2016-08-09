package com.appdirect.server.store;

import java.util.UUID;

import com.appdirect.server.user.data.UserData;

/**
 * Store user subscription data. 
 * Please not this is inmemory store therefore if tomcat server get rebooted then whole subscription data will
 * get lost.
 * @author vineetsagar
 *
 */
public class InMemoryDataStore extends DataStore {
	
	private static DataStore store;
	
	// object for synchronized access to shared resource
	private static Object syncObj = new Object();

	private InMemoryDataStore(){

	}

	public static synchronized DataStore getInstance(){
		if (store == null){
			store  = new InMemoryDataStore();
		}
		return store;
	}
	
	@Override
	public String addUser( UserData status , String userOpenId) {
		String uniqueIdentifier = UUID.randomUUID().toString();
			synchronized (syncObj) {
				map.put(uniqueIdentifier, status);
				openIdMap.put(userOpenId, uniqueIdentifier);
			}
		return uniqueIdentifier;
	}

	@Override
	public void deleteUser(String uniqueIdentifier) {
		synchronized (syncObj) {
					map.remove(uniqueIdentifier);
		}
	}

	@Override
	public boolean updateUser( String userOpenId, UserData data) {
		synchronized (syncObj) {
			String identifier = openIdMap.get(userOpenId);
				if(identifier!=null){
					map.put(identifier, data);
					return true;
				}
		}
		return false;
	}

	@Override
	public boolean isUserExistbyOpenId(String userOpenId) {
		synchronized (syncObj) {
			if(openIdMap.containsKey(userOpenId)){
				String identifier = openIdMap.get(userOpenId);
				if(identifier!=null){
					return map.containsKey(identifier);
				}
			}
		}
		return false;
	}
	
	@Override
	public UserData getUserByOpenId(String userOpenId) {
		synchronized (syncObj) {
			if(openIdMap.containsKey(userOpenId)){
				String identifier = openIdMap.get(userOpenId);
				if(identifier!=null){
					return map.get(identifier);
				}
			}
		}
		return null;
	}
}
