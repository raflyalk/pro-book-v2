package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BookDbHelper {
	private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final static String DB_URL = "jdbc:mysql://localhost:3306/book_ws_db";
	private final static String USER = "nuance";
	private final static String PASS = "Allofthings1";
	private final static String ORDER_TABLE = "orders";
	private final static String BOOK_TABLE = "books";
	private static Connection conn;
	
	public static Connection getConnectionInstance() {
		if (conn == null) {
			try {
	        	//Try to connect to db
	        	Class.forName(JDBC_DRIVER);
	    		conn = DriverManager.getConnection(DB_URL, USER, PASS);
	    		
	    		//If success
	            System.out.println(String.format("Connected to database %s "
	                    + "successfully.", conn.getCatalog()));
	            
	            //Create table if not exist
	            createOrderTableIfNotExist();
	            createBookTableIfNotExist();
	            
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        } catch (ClassNotFoundException ex) {
	        	System.out.println("Class not found");
			}
		}
		return conn;
	}
	
	private static void createOrderTableIfNotExist() throws SQLException {
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS " + ORDER_TABLE
	            + "  (id				int(10) NOT NULL PRIMARY KEY,"
	            + "   user_id			int(10),"
	            + "   book_id			int(10),"
	            + "   category			varchar(255),"
	            + "   ordered_by		datetime)";

	    Statement stmt = conn.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	private static void createBookTableIfNotExist() throws SQLException {
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS " + BOOK_TABLE
	            + "  (id				int(10) NOT NULL PRIMARY KEY,"
	            + "   book_id			varchar(255) UNIQUE,"
	            + "   title				varchar(255),"
	            + "   author			varchar(255),"
	            + "   description		text,"
	            + "   avg_rating		float(1,1),"
	            + "   quantity			int(10),"
	            + "   price				float(11,1),"
	            + "   thumbnail			varchar(255))";

	    Statement stmt = conn.createStatement();
	    stmt.execute(sqlCreate);
	}
}
