package com.appdirect.server.store;

import java.util.UUID;

import com.appdirect.server.user.data.UserStatus;

public class InMemoryDataStore extends DataStore {
	
	private static DataStore store;

	private InMemoryDataStore(){

	}

	public static synchronized DataStore getInstance(){
		if (store == null){
			store  = new InMemoryDataStore();
		}
		return store;
	}
	
	@Override
	public String addUser( String appDirectOpenId, UserStatus status) {
		String uniqueIdentifier = UUID.randomUUID().toString();
		
		/**
		 *  
		 */
		map.put(uniqueIdentifier, status);
		openIdMap.put(uniqueIdentifier, appDirectOpenId);
		return uniqueIdentifier;
	}

	@Override
	public void deleteUser(String uniqueIdentifier, String appDirectOpenId) {
		if(openIdMap.get(appDirectOpenId)!=null){
			if(map.containsKey(uniqueIdentifier)){
				map.remove(uniqueIdentifier);
				openIdMap.remove(appDirectOpenId);
			}
		}
	}

	@Override
	public void updateUser( String uniqueIdentifier, String appDirectOpenId, UserStatus status) {
		if(openIdMap.get(appDirectOpenId)!=null){
			if(map.containsKey(uniqueIdentifier)){
				map.put(uniqueIdentifier, status);
			}
		}
	}

	@Override
	public boolean isUserExist(String uniqueIdentifier, String appDirectOpenId) {
		if(openIdMap.get(appDirectOpenId)!=null){
			if(map.containsKey(uniqueIdentifier)){
				return true;	
			}
		}
		return false;
	}
	
	@Override
	public boolean isUserActive(String uniqueIdentifier, String appDirectOpenId) {
		if(openIdMap.get(appDirectOpenId)!=null){
			if(map.containsKey(uniqueIdentifier)){
				UserStatus userStatus = map.get(uniqueIdentifier);
				return userStatus.isActive();	
			}
		}
		return false;
	}
	
}
