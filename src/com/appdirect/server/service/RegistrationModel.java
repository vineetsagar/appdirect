package com.appdirect.server.service;

import java.io.Serializable;
import java.util.Date;

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
	private String zipCode;
	private Date dateOfBirth;
	private String favoriteColor;

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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFavoriteColor() {
		return favoriteColor;
	}

	public void setFavoriteColor(String favoriteColor) {
		this.favoriteColor = favoriteColor;
	}

}
