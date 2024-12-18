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
	
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object User
	public static String register(String email, String name, String password, String role) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk insert user baru ke dalam database
		String query = "INSERT INTO `user` (user_email, user_name, user_password, user_role) VALUES (?, ?, ?, ?)";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setString(1, email);
			connection.getPreparedStatement().setString(2, name);
			connection.getPreparedStatement().setString(3, password);
			connection.getPreparedStatement().setString(4, role);
			// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
			connection.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		// kalau berhasil, return success
		return "Success";
	}
	
	public static User login(String email, String password) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk mengambil user berdasarkan email dan password
		String query = "SELECT user_id, user_email, user_name, user_role FROM `user` WHERE user_email = ? AND user_password = ?";
		
		connection.setPreparedStatement(query);
		User user = null;
		try {
			connection.getPreparedStatement().setString(1, email);
			connection.getPreparedStatement().setString(2, password);
			// gunakan executeQuery dan tampung ke dalam variable ResultSet result
			ResultSet result = connection.executeQuery();
			// result hanya akan memiliki 1 isi karena query berdasarkan email yang unique dan password yang match
			// hasilnya akan ditampung ke dalam object user
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
		// return user yang didapatkan dari database
		return user;
	}
	
	/*
	 * ASUMSI:
	 * 1. passing userId supaya bisa ganti profile user berdasarkan Id
	 * 2. oldPassword tidak digunakan karena tidak diperlukan untuk proses update profile, hanya diperlukan buat validasi newPassword di controller
	 */
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object User
	public static String changeProfile(String userId, String email, String name, String oldPassword, String newPassword) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		
		// kalau newPassword tidak kosong, maka update password user
		if(!newPassword.isBlank()) {
			// query untuk update email, username, dan password user
			String query = "UPDATE `user` SET user_email = ?, user_name = ?, user_password = ? WHERE user_id = ?";
			connection.setPreparedStatement(query);
			try {
				connection.getPreparedStatement().setString(1, email);
				connection.getPreparedStatement().setString(2, name);
				connection.getPreparedStatement().setString(3, newPassword);
				connection.getPreparedStatement().setInt(4, Integer.parseInt(userId));
				// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
				connection.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else {
			// query untuk update emal dan username user
			String query = "UPDATE `user` SET user_email = ?, user_name = ? WHERE user_id = ?";
			connection.setPreparedStatement(query);
			try {
				connection.getPreparedStatement().setString(1, email);
				connection.getPreparedStatement().setString(2, name);
				connection.getPreparedStatement().setInt(3, Integer.parseInt(userId));
				// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
				connection.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		// kalau berhasil update, return success
		return "Success";
	}
	
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object User
	public static User getUserByEmail(String email) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk ambil user berdasarkan email
		String query = "SELECT user_id, user_email, user_name, user_role FROM `user` WHERE user_email = ?";
		
		connection.setPreparedStatement(query);
		User user = null;
		try {
			connection.getPreparedStatement().setString(1, email);
			// gunakan executeQuery dan tampung ke dalam variable ResultSet result
			ResultSet result = connection.executeQuery();
			// result hanya akan memiliki 1 isi karena query berdasarkan email yang unique
			// hasilnya akan ditampung ke dalam object user
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
		// return user yang didapatkan dari database
		return user;
	}
	
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object User
	public static User getUserByUsername(String name) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk mengambil user berdasarkan username
		String query = "SELECT user_id, user_email, user_name, user_role FROM `user` WHERE user_name = ?";
		
		connection.setPreparedStatement(query);
		User user = null;
		try {
			connection.getPreparedStatement().setString(1, name);
			// gunakan executeQuery dan tampung ke dalam variable ResultSet result
			ResultSet result = connection.executeQuery();
			// result hanya akan memiliki 1 isi karena query berdasarkan username yang unique
			// hasilnya akan ditampung ke dalam object user
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
		// return user yang didapatkan dari database
		return user;
	}
	
	public void checkRegisterInput(String email, String name, String password) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object User
	public static ObservableList<User> getAllUsers(){
		// inisialisasi userList untuk menampung semua user
		ObservableList<User> userList = FXCollections.observableArrayList();
		
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk mengambil user dari database, ambil semua atribut kecuali password
		String query = "SELECT user_id, user_email, user_name, user_role FROM `user`";
		
		connection.setPreparedStatement(query);
		try {
			// gunakan executeQuery dan tampung ke dalam variable ResultSet result
			ResultSet result = connection.executeQuery();
			// selagi result masih ada isi, maka isinya akan ditambahkan ke dalam userList
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
		// return userList yang sudah ada isi
		return userList;
	}
	
	/*
	 * Di Class Diagram tidak ada, tapi di Sequence Diagram untuk deleteUser ada
	 * ASUMSI:
	 * 1. ditambahkan karena mengikuti sequence diagram
	 */
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object User
	public static void deleteUser(String userID) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		User user = User.getUserById(userID);
		
		// kalau rolenya Event Organizer, maka delete semua event yang dibuat dan semua invitation untuk event tersebut
		if(user != null && user.getUser_role().equals("Event Organizer")) {
			// query untuk hapus invitation untuk event yang dibuat oleh user berdasarkan organizer_id
			String query = "DELETE FROM `invitation` WHERE event_id IN (SELECT event_id FROM `event` WHERE organizer_id = ?)";
			connection.setPreparedStatement(query);
			try {
				connection.getPreparedStatement().setInt(1, Integer.parseInt(userID));
				// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
				connection.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}
			
			// query untuk delete event berdasarkan organizer_id
			String query2 = "DELETE FROM `event` WHERE organizer_id = ?";
			connection.setPreparedStatement(query2);
			try {
				connection.getPreparedStatement().setInt(1, Integer.parseInt(userID));
				// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
				connection.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// kalau rolenya vendor, maka delete invitation yang diterima oleh vendor tersebut
		else if(user != null && user.getUser_role().equals("Vendor")) {
			// query untuk delete invitation berdasarkan user_id milik vendor
			String query = "DELETE FROM `invitation` WHERE user_id = ?";
			connection.setPreparedStatement(query);
			try {
				connection.getPreparedStatement().setInt(1, Integer.parseInt(userID));
				// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
				connection.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}
			
			// query untuk delete product milik vendor
			String query2 = "DELETE FROM `product` WHERE vendor_id = ?";
			connection.setPreparedStatement(query2);
			try {
				connection.getPreparedStatement().setInt(1, Integer.parseInt(userID));
				// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
				connection.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// kalau rolenya guest, maka delete invitation yang diterima oleh guest tersebut
		else if(user != null && user.getUser_role().equals("Guest")) {
			// query untuk delete invitation berdasarkan user_id milik guest
			String query = "DELETE FROM `invitation` WHERE user_id = ?";
			connection.setPreparedStatement(query);
			try {
				connection.getPreparedStatement().setInt(1, Integer.parseInt(userID));
				// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
				connection.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// query untuk hapus user dari dalam database berdasarkan user_id
		String query3 = "DELETE FROM `user` WHERE user_id = ?";
		connection.setPreparedStatement(query3);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(userID));
			// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
			connection.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ditambahkan supaya bisa get userById
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object User
	public static User getUserById(String userId) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk mengambil user berdasarkan id
		String query = "SELECT user_id, user_email, user_name, user_role FROM `user` WHERE user_id = ?";
		User user = null;
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(userId));
			// gunakan executeQuery dan tampung ke dalam variable ResultSet result
			ResultSet result = connection.executeQuery();
			// result hanya akan memiliki 1 isi karena query berdasarkan id 
			// hasilnya akan ditampung ke dalam object user
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
		// return user yang sudah ada isi
		return user;
	}
	
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object User
	// ditambahkan untuk 
	public static boolean matchOldPassword(String userId, String oldPassword) {
	    DatabaseConnection connection = DatabaseConnection.getInstance();
	    // query untuk mengambil user berdasarkan id dan password
	    String query = "SELECT user_id, user_email, user_name, user_role FROM user WHERE user_id = ? AND user_password = ?";
	    
	    connection.setPreparedStatement(query);
	    try {
	        connection.getPreparedStatement().setString(1, userId);
	        connection.getPreparedStatement().setString(2, oldPassword);
	        // gunakan executeQuery dan tampung ke dalam variable ResultSet result
	        ResultSet result = connection.executeQuery();
	        // return result.next() (true kalau ada isi)
	        return result.next();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    // return false kalau kosong
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
