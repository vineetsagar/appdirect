package com.appdirect.server.store;

import java.util.HashMap;
import java.util.Map;

import com.appdirect.server.user.data.UserData;

/**
 * This class will store all data of a user
 * @author vineetsagar
 *
 */
public abstract class DataStore {
	
	public DataStore(){
	}
	
	
	/**
	 * map will store user unique identifier vs userdata the system
	 */
	protected static Map<String,UserData> map = new HashMap<String,UserData>();
	
	protected static Map<String,String> openIdMap = new HashMap<String,String>();

	/**
	 * Return the system generated unique identifier
	 * @param appDirectOpenId
	 * @param creator
	 * @return
	 */
	public abstract String addUser( UserData status, String userOpenId);
	
	public abstract void deleteUser(String uniqueIdentifier);
	
	public abstract boolean updateUser(String userOpenId, UserData status);
	
	public abstract UserData getUserByOpenId(String userOpenId);
	
	public abstract boolean isUserExistbyOpenId(String userOpenId);
	
	
}
