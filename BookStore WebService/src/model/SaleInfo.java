package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleInfo {
	class RetailPrice{
		@SerializedName("amount")
		@Expose
		private int amount;
		
		public int getAmount() {
			return amount;
		}
	}
	
	@SerializedName("retailPrice")
	@Expose
	private RetailPrice retailPrice;
	
	public RetailPrice getRetailPrice() {
		return retailPrice;
	}
}
