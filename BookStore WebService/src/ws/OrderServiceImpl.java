package ws;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.jws.WebService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import api.GoogleBooksAPI;
import database.BookDbHelper;
import model.Book;
import model.Order;

@WebService(endpointInterface = "ws.OrderService")
public final class OrderServiceImpl implements OrderService {

	@Override
	public String order(int userId, String bookId, int quantity, int accountNumber) {
		boolean success;
		
		/** Check user's balance, if greater or equal than price of ordered books then buy it..
			Need to check Bank WebService from accountNumber
		**/
		
		//Assume that user's order always success (because Bank WebService is not ready yet)
		success = true;
		if (success) {
			Date date = new Date();
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
			
			Book book = GoogleBooksAPI.getBookDetail(bookId);
			
			//Connect to db
			BookDbHelper db = new BookDbHelper();
			
			//Insert new dummy data order
			String[] categories = book.getVolumeInfo().getCategories();
			for (String category: categories) {
				try {
					db.insertOrder(userId, bookId, category, quantity, sqlDate);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			return "{\"success\":1}";
		}
		
		return "{\"success\":0}";
	}

	@Override
	public String getOrderHistoryById(int userId) {
		BookDbHelper db = new BookDbHelper();
		ArrayList<Order> orders = new ArrayList<>();
		
		try {
			orders = db.getOrderHistoryById(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Remove unused data
		Gson gson = new Gson();
		Type listOrderType = new TypeToken<ArrayList<Order>>() {}.getType();
		
		return gson.toJson(orders, listOrderType);
	}

	@Override
	public String updateOrder(int id, int rating, String comment) {
		BookDbHelper db = new BookDbHelper();
		
		try {
			db.updateOrder(id, rating, comment);
			
			return "{\"success\":1}";
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return "{\"success\":0}";
	}

}
