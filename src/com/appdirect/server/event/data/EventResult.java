package com.appdirect.server.event.data;

public class EventResult {

	private boolean success;
	
	private String identifier;
	private String message;
	
	public EventResult(boolean success, String identifier) {
		this.success = success;
		this.identifier = identifier;
	}
	public EventResult(boolean success, String identifier, String message) {
		this.success = success;
		this.identifier = identifier;
		this.message = message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
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
		EventResult other = (EventResult) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (success != other.success)
			return false;
		return true;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	
	
}
