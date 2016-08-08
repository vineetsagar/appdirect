package com.appdirect.server.store;

import java.util.HashMap;
import java.util.Map;

import com.appdirect.server.user.data.UserStatus;

/**
 * This class will store all data of a user
 * @author vineetsagar
 *
 */
public abstract class DataStore {

	/**
	 * map will store user unique identifier vs userstatusin the system
	 */
	protected static Map<String,UserStatus> map = new HashMap<String,UserStatus>();
	/**
	 * openIdMap will store unique openid vs user unique identifier
	 */
	protected static Map<String,String> openIdMap = new HashMap<String,String>();
	/**
	 * Return the system generated unique identifier
	 * @param appDirectOpenId
	 * @param creator
	 * @return
	 */
	public abstract String addUser( String appDirectOpenId, UserStatus status);
	
	public abstract void deleteUser(String uniqueIdentifier, String appDirectOpenId);
	
	public abstract void updateUser(String uniqueIdentifier, String appDirectOpenId, UserStatus status);
	
	public abstract boolean isUserExist(String uniqueIdentifier, String appDirectOpenId);
	
	public abstract boolean isUserActive(String uniqueIdentifier, String appDirectOpenId);
	
	
}
