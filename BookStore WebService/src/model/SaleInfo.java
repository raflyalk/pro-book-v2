package model;

import com.google.gson.annotations.SerializedName;

public class SaleInfo {
	public class RetailPrice{
		@SerializedName("amount")
		private float amount;
		
		public float getAmount() {
			return amount;
		}
	}
	
	@SerializedName("saleability")
	private String saleability;
	
	public String isSaleAbility() {
		return saleability;
	}
	
	@SerializedName("retailPrice")
	private RetailPrice retailPrice;
	
	public RetailPrice getRetailPrice() {
		return retailPrice;
	}
}