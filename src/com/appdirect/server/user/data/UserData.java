package com.appdirect.server.user.data;

public class UserData {

	
	private boolean active;
	
	/**
	 * This is the only information we are saving from app direct. 
	 * Simillary we can store other use full information as required
	 */
	private String editionCode;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEditionCode() {
		return editionCode;
	}

	public void setEditionCode(String editionCode) {
		this.editionCode = editionCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((editionCode == null) ? 0 : editionCode.hashCode());
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
		UserData other = (UserData) obj;
		if (active != other.active)
			return false;
		if (editionCode == null) {
			if (other.editionCode != null)
				return false;
		} else if (!editionCode.equals(other.editionCode))
			return false;
		return true;
	}
}
