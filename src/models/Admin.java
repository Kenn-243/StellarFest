package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Admin extends User{
	public Admin(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
	}
	
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Admin
	public static ObservableList<Event> viewAllEvents() {
		// inisialisasi eventList yang akan menampung semua event
		ObservableList<Event> eventList = FXCollections.observableArrayList();

		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk mengambil semua event dari dalam database
		String query = "SELECT * FROM `event`";
		
		connection.setPreparedStatement(query);
		try {
			// gunakan executeQuery dan tampung ke dalam variable ResultSet result
			ResultSet result = connection.executeQuery();
			// selagi result masih ada isi, maka isinya akan dimasukkan ke dalam eventList
			while(result.next()) {
				String event_id = String.valueOf(result.getInt("event_id"));
				String event_name = result.getString("event_name");
				String event_date = result.getDate("event_date").toString();
				String event_location = result.getString("event_location");
				String event_description = result.getString("event_description");
				String organizer_id = String.valueOf(result.getInt("organizer_id"));
				eventList.add(new Event(event_id, event_name, event_date, event_location, event_description, organizer_id));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// return eventList yang sudah memiliki isi
		return eventList;
	}
	
	public void viewEventDetails(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void deleteEvent(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public static void deleteUser(String userID) {
		/*
		 * ASUMSI:
		 * 1. dijadikan static karena nama methodnya sama dengan yang ada di parent class (User) sehingga mau tidak mau harus sesuai dengan yang ada di User class
		 * 2. dikosongkan karena berdasarkan sequence diagram tidak digunakan di Admin class, tapi di User class
		 */
	}
	
	public static ObservableList<User> getAllUsers() {
		/*
		 * harusnya dikosongkan karena berdasarkan sequence diagram tidak digunakan
		 * tapi, nama methodnya sama dengan yang ada di parent class (User) sehingga
		 * mau tidak mau harus sesuai dengan yang ada di User class
		 * 
		 * method ini tidak digunakan di class atau package manapun
		 */
		ObservableList<User> userList = null;
		return userList;
	}
	
	public void getAllEvents() {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void getGuestsByTransactionID(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void getVendorsByTransactionID(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
}
