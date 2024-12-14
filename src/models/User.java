package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
		String query = "INSERT INTO `user` (user_email, user_name, user_password, user_role) VALUES (?, ?, ?, ?)";
		
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
		String query = "SELECT user_id, user_email, user_name, user_role FROM `user` WHERE user_email = ? AND user_password = ?";
		
		connection.setPreparedStatement(query);
		User user = null;
		try {
			connection.getPreparedStatement().setString(1, email);
			connection.getPreparedStatement().setString(2, password);
			ResultSet result = connection.executeQuery();
			while(result.next()) {
				String user_id = String.valueOf(result.getInt("user_id"));
				String user_email = result.getString("user_email");
				String user_name = result.getString("user_name");
				String user_role = result.getString("user_role");
				user = new User(user_id, user_email, user_name, "", user_role);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/*
	 * ASUMSI:
	 * 1. passing userId supaya bisa ganti profile user berdasarkan Id
	 * 2. oldPassword tidak digunakan karena tidak diperlukan untuk proses update profile, hanya diperlukan buat validasi newPassword di controller
	 */
	public static String changeProfile(String userId, String email, String name, String oldPassword, String newPassword) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		
		if(!newPassword.isBlank()) {
			String query = "UPDATE `user` SET user_email = ?, user_name = ?, user_password = ? WHERE user_id = ?";
			connection.setPreparedStatement(query);
			try {
				connection.getPreparedStatement().setString(1, email);
				connection.getPreparedStatement().setString(2, name);
				connection.getPreparedStatement().setString(3, newPassword);
				connection.getPreparedStatement().setInt(4, Integer.parseInt(userId));
				connection.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else {
			String query = "UPDATE `user` SET user_email = ?, user_name = ? WHERE user_id = ?";
			connection.setPreparedStatement(query);
			try {
				connection.getPreparedStatement().setString(1, email);
				connection.getPreparedStatement().setString(2, name);
				connection.getPreparedStatement().setInt(3, Integer.parseInt(userId));
				connection.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return "Success";
	}
	
	public static User getUserByEmail(String email) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "SELECT user_id, user_email, user_name, user_role FROM `user` WHERE user_email = ?";
		
		connection.setPreparedStatement(query);
		User user = null;
		try {
			connection.getPreparedStatement().setString(1, email);
			ResultSet result = connection.executeQuery();
			while(result.next()) {
				String user_id = String.valueOf(result.getInt("user_id"));
				String user_email = result.getString("user_email");
				String user_name = result.getString("user_name");
				String user_role = result.getString("user_role");
				user = new User(user_id, user_email, user_name, "", user_role);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public static User getUserByUsername(String name) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "SELECT user_id, user_email, user_name, user_role FROM `user` WHERE user_name = ?";
		
		connection.setPreparedStatement(query);
		User user = null;
		try {
			connection.getPreparedStatement().setString(1, name);
			ResultSet result = connection.executeQuery();
			while(result.next()) {
				String user_id = String.valueOf(result.getInt("user_id"));
				String user_email = result.getString("user_email");
				String user_name = result.getString("user_name");
				String user_role = result.getString("user_role");
				user = new User(user_id, user_email, user_name, "", user_role);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public void checkRegisterInput(String email, String name, String password) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan
	}
	
	public void checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan
	}
	
	public static ObservableList<User> getAllUsers(){
		ObservableList<User> userList = FXCollections.observableArrayList();
		
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "SELECT user_id, user_email, user_name, user_role FROM `user`";
		
		connection.setPreparedStatement(query);
		try {
			ResultSet result = connection.executeQuery();
			while(result.next()) {
				String user_id = String.valueOf(result.getInt("user_id"));
				String user_email = result.getString("user_email");
				String user_name = result.getString("user_name");
				String user_role = result.getString("user_role");
				userList.add(new User(user_id, user_email, user_name, "", user_role));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return userList;
	}
	
	/*
	 * Di Class Diagram tidak ada, tapi di Sequence Diagram untuk deleteUser ada
	 * ASUMSI:
	 * 1. ditambahkan karena mengikuti sequence diagram
	 */
	public static void deleteUser(String userID) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		User user = User.getUserById(userID);
		
		if(user != null && user.getUser_role().equals("Event Organizer")) {
			String query = "DELETE FROM `invitation` WHERE event_id IN (SELECT event_id FROM `event` WHERE organizer_id = ?)";
			connection.setPreparedStatement(query);
			try {
				connection.getPreparedStatement().setInt(1, Integer.parseInt(userID));
				connection.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}
			
			String query2 = "DELETE FROM `event` WHERE organizer_id = ?";
			connection.setPreparedStatement(query2);
			try {
				connection.getPreparedStatement().setInt(1, Integer.parseInt(userID));
				connection.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(user != null && user.getUser_role().equals("Vendor")) {
			String query = "DELETE FROM `invitation` WHERE user_id = ?";
			connection.setPreparedStatement(query);
			try {
				connection.getPreparedStatement().setInt(1, Integer.parseInt(userID));
				connection.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}
			
			String query2 = "DELETE FROM `product` WHERE vendor_id = ?";
			connection.setPreparedStatement(query2);
			try {
				connection.getPreparedStatement().setInt(1, Integer.parseInt(userID));
				connection.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(user != null && user.getUser_role().equals("Guest")) {
			String query = "DELETE FROM `invitation` WHERE user_id = ?";
			connection.setPreparedStatement(query);
			try {
				connection.getPreparedStatement().setInt(1, Integer.parseInt(userID));
				connection.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		String query3 = "DELETE FROM `user` WHERE user_id = ?";
		connection.setPreparedStatement(query3);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(userID));
			connection.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ditambahkan supaya bisa get userById
	public static User getUserById(String userId) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "SELECT user_id, user_email, user_name, user_role FROM `user` WHERE user_id = ?";
		User user = null;
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(userId));
			ResultSet result = connection.executeQuery();
			while(result.next()) {
				String user_id = String.valueOf(result.getInt("user_id"));
				String user_email = result.getString("user_email");
				String user_name = result.getString("user_name");
				String user_role = result.getString("user_role");
				user = new User(user_id, user_email, user_name, "", user_role);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public static boolean matchOldPassword(String userId, String oldPassword) {
	    DatabaseConnection connection = DatabaseConnection.getInstance();
	    String query = "SELECT user_id, user_email, user_name, user_role FROM user WHERE user_id = ? AND user_password = ?";
	    
	    connection.setPreparedStatement(query);
	    try {
	        connection.getPreparedStatement().setString(1, userId);
	        connection.getPreparedStatement().setString(2, oldPassword);
	        ResultSet result = connection.executeQuery();
	        return result.next();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
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
