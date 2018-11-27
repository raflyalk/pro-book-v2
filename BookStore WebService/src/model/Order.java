package model;

import java.sql.Timestamp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
	@SerializedName("bookId")
	@Expose
	private String bookId;
	
	@SerializedName("id")
	@Expose
	private int id;
	
	@SerializedName("rating")
	@Expose
	private int rating;
	
	@SerializedName("quantity")
	@Expose
	private int quantity;
	
	@SerializedName("comment")
	@Expose
	private String comment;
	
	@SerializedName("orderedBy")
	@Expose
	private Timestamp orderedBy;
	
	public String getBookId() {
		return bookId;
	}
	
	public int getId() {
		return id;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public Timestamp getOrderedBy() {
		return orderedBy;
	}
	
	public int getRating() {
		return rating;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setId(int i) {
		id = i;
	}
	
	public void setBookId(String id) {
		bookId = id;
	}
	
	public void setRating(int rat) {
		rating = rat;
	}
	
	public void setComment(String c) {
		comment = c;
	}
	
	public void setQuantity(int q) {
		quantity = q;
	}
	
	public void setOrderedBy(Timestamp t) {
		orderedBy = t;
	}
}
