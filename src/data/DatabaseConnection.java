package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
	private final String DB_NAME = "stellarfest";
	private final String DB_USERNAME = "root";
	private final String DB_PASSWORD = "";
	private final String DB_HOST = "localhost:3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", DB_HOST, DB_NAME);
	
	private static Connection connection;
	private static DatabaseConnection instance;
	private PreparedStatement st;
	
	public static DatabaseConnection getInstance() {
		if(instance == null) instance = new DatabaseConnection();
		return instance;
	}
	
	private DatabaseConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(CONNECTION, DB_USERNAME, DB_PASSWORD);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet executeQuery() {
		try {
			return st.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void setPreparedStatement(String query) {
		try {
			st = connection.prepareStatement(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void executeUpdate() {
		try {
			st.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public PreparedStatement getPreparedStatement() {
		return st;
	}
}
