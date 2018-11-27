package model;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VolumeInfo {
	class ImageLinks{
		@SerializedName("thumbnail")
		@Expose
		private String thumbnail;
		
		public String getThumbnail() {
			return thumbnail;
		}
	}
	
	@SerializedName("title")
	@Expose
	private String title;
	
	@SerializedName("authors")
	@Expose
	private String[] authors;
	
	@SerializedName("categories")
	@Expose
	private String[] categories;
	
	@SerializedName("averageRating")
	@Expose
	private float averageRating;
	
	@SerializedName("description")
	@Expose
	private String description;
	
	@SerializedName("imageLinks")
	@Expose
	private ImageLinks imageLinks;
	
	@SerializedName("price")
	@Expose
	private float price;
	
	@SerializedName("quantity")
	@Expose
	private int quantity;
	
	@SerializedName("reviews")
	@Expose
	private ArrayList<Review> reviews = new ArrayList<>();
	
	public Review getReviewAt(int pos) {
		return reviews.get(pos);
	}
	
	public void addReview(Review review) {
		reviews.add(review);
	}
	
	public void addAllReview(ArrayList<Review> r) {
		reviews.addAll(r);
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String[] getAuthors() {
		return authors;
	}
	
	public float getAverageRating() {
		return averageRating;
	}
	
	public void setAverageRating(float avgRating) {
		averageRating = avgRating;
	}
	
	public String[] getCategories() {
		return categories;
	}
}
