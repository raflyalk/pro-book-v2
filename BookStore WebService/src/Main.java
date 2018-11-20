import ws.*;

import java.sql.Connection;

import javax.xml.ws.Endpoint;

import database.BookDbHelper;

public class Main {
	public static void main(String[] args) {
		Connection conn = BookDbHelper.getConnectionInstance();
		
		try {
			Endpoint.publish("http://localhost:4789/services/search", new SearchServiceImpl());	
			Endpoint.publish("http://localhost:4789/services/order", new OrderServiceImpl());		
			Endpoint.publish("http://localhost:4789/services/recommendation", new RecommendationServiceImpl());		
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
