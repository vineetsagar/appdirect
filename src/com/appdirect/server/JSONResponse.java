package com.appdirect.server;

import java.io.Serializable;

/**
 * @author vineetsagar
 *
 */
public class JSONResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean success;
	private int errorCode;
	private String message;
	private String accountIdentifier;
	
	public JSONResponse() {
	}
	
	public JSONResponse(int errorCode, boolean success, String message) {
		this.errorCode = errorCode;
		this.success = success;
		this.message = message;
	}
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountIdentifier == null) ? 0 : accountIdentifier.hashCode());
		result = prime * result + errorCode;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + (success ? 1231 : 1237);
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
		JSONResponse other = (JSONResponse) obj;
		if (accountIdentifier == null) {
			if (other.accountIdentifier != null)
				return false;
		} else if (!accountIdentifier.equals(other.accountIdentifier))
			return false;
		if (errorCode != other.errorCode)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (success != other.success)
			return false;
		return true;
	}
	
}
