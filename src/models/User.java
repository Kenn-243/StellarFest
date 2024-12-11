package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.DatabaseConnection;

public class User {
	private String user_id;
	private String user_email;
	private String user_name;
	private String user_password;
	private String user_role;
	
	public User(String user_id, String user_email, String user_name, String user_password, String user_role) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_role = user_role;
    }

	public static String register(String email, String name, String password, String role) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "INSERT INTO `User` (user_email, user_name, user_password, user_role) VALUES (?, ?, ?, ?);";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setString(1, email);
			connection.getPreparedStatement().setString(2, name);
			connection.getPreparedStatement().setString(3, password);
			connection.getPreparedStatement().setString(4, role);
			connection.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return "Success";
	}
	
	// kayaknya ini lebih cocok return object User
	public static String login(String email, String password) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "SELECT * FROM `User` WHERE user_email = ? AND user_password = ?;";
		
		connection.setPreparedStatement(query);
		User user = null;
		try {
			connection.getPreparedStatement().setString(1, email);
			connection.getPreparedStatement().setString(2, password);
			connection.executeQuery();
			ResultSet result = connection.executeQuery();
			while(result.next()) {
				user = new User(String.valueOf(result.getString("user_id")), result.getString("user_email"), result.getString("user_name"), result.getString("user_password"), result.getString("user_role"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user != null ? "Success" : "Wrong email or password";
	}
	
	public static void changeProfile(String email, String name, String oldPassword, String newPassword) {
		
	}
	
	public static User getUserByEmail(String email) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "SELECT * FROM `User` WHERE user_email = ?";
		
		connection.setPreparedStatement(query);
		User user = null;
		try {
			connection.getPreparedStatement().setString(1, email);
			connection.executeQuery();
			ResultSet result = connection.executeQuery();
			while(result.next()) {
				user = new User(String.valueOf(result.getString("user_id")), result.getString("user_email"), result.getString("user_name"), result.getString("user_password"), result.getString("user_role"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public static User getUserByUsername(String name) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "SELECT * FROM `User` WHERE user_name = ?";
		
		connection.setPreparedStatement(query);
		User user = null;
		try {
			connection.getPreparedStatement().setString(1, name);
			connection.executeQuery();
			ResultSet result = connection.executeQuery();
			while(result.next()) {
				user = new User(String.valueOf(result.getString("user_id")), result.getString("user_email"), result.getString("user_name"), result.getString("user_password"), result.getString("user_role"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public static void checkRegisterInput(String email, String name, String password) {
		
	}
	
	public static void checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
		
	}
}
