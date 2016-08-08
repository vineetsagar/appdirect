package com.appdirect.server.data;

public class Order {

	private String editionCode;
	private String pricingDuration;
	private Item item;
	public String getEditionCode() {
		return editionCode;
	}
	public void setEditionCode(String editionCode) {
		this.editionCode = editionCode;
	}
	public String getPricingDuration() {
		return pricingDuration;
	}
	public void setPricingDuration(String pricingDuration) {
		this.pricingDuration = pricingDuration;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((editionCode == null) ? 0 : editionCode.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((pricingDuration == null) ? 0 : pricingDuration.hashCode());
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
		Order other = (Order) obj;
		if (editionCode == null) {
			if (other.editionCode != null)
				return false;
		} else if (!editionCode.equals(other.editionCode))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (pricingDuration == null) {
			if (other.pricingDuration != null)
				return false;
		} else if (!pricingDuration.equals(other.pricingDuration))
			return false;
		return true;
	}
}
