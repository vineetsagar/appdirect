package com.appdirect.server.service;

import java.io.Serializable;

/**
 * This class serves as the model for populating information from oAuth request
 * 
 */
public class RegistrationModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2834908356868001273L;

	private String openId;
	private String fullname;
	private String email;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getFullName() {
		return fullname;
	}

	public void setFullName(String fullName) {
		this.fullname = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String emailAddress) {
		this.email = emailAddress;
	}
}
