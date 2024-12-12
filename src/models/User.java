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
		String query = "INSERT INTO `user` (user_email, user_name, user_password, user_role) VALUES (?, ?, ?, ?);";
		
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
	
	public static User login(String email, String password) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "SELECT user_id, user_email, user_name, user_role FROM `user` WHERE user_email = ? AND user_password = ?;";
		
		connection.setPreparedStatement(query);
		User user = null;
		try {
			connection.getPreparedStatement().setString(1, email);
			connection.getPreparedStatement().setString(2, password);
			ResultSet result = connection.executeQuery();
			while(result.next()) {
				user = new User(String.valueOf(result.getString("user_id")), result.getString("user_email"), result.getString("user_name"), "", result.getString("user_role"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public static String changeProfile(User user, String email, String name, String oldPassword, String newPassword) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "UPDATE `User` SET user_email = ?, user_name = ?, user_password = ? WHERE user_id = ?";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setString(1, email);
			connection.getPreparedStatement().setString(2, name);
			connection.getPreparedStatement().setString(3, newPassword);
			connection.executeQuery();
			connection.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return "Success";
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

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
}
