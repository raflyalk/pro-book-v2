package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {
	@SerializedName("userId")
	@Expose
	private int userId;
	
	@SerializedName("rating")
	@Expose
	private int rating;
	
	@SerializedName("comment")
	@Expose
	private String comment;
	
	public int getUserId() {
		return userId;
	}
	
	public int getRating() {
		return rating;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setUserId(int id) {
		userId = id;
	}
	
	public void setRating(int rat) {
		rating = rat;
	}
	
	public void setComment(String c) {
		comment = c;
	}
}
