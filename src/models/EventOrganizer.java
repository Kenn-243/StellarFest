package models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.DatabaseConnection;

public class EventOrganizer extends User{
	private String events_created;
	
	public EventOrganizer(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
	}
	
	public void createEvent(String eventName, Date date, String location, String description, String organizerID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void viewOrganizedEvents(String userID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void viewOrganizedEventDetails(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void getGuest() {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void getVendors() {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void getGuestsByTransactionID(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void getVendorsByTransactionID(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void checkCreateEventInput(String eventName, Date date, String location, String description) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void checkAddVendorInput(String vendorID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void checkAddGuestInput(String vendorID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void editEventName(String eventID, String eventName) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	// ditambahkan buat mendapatkan event organizer berdasarkan ID
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object EventOrganzer
	public static EventOrganizer getOrganizerById(String organizerId) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk mengambil Event Organizer berdasarkan d
		String query = "SELECT user_id, user_name, user_email, user_role FROM `user` WHERE user_id = ?";
		EventOrganizer organizer = null;
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(organizerId));
			// gunakan executeQuery dan tampung ke dalam variable ResultSet result
			ResultSet result = connection.executeQuery();
			// result hanya akan memiliki 1 isi karena query berdasarkan id
			// hasilnya akan ditampung ke dalam object event organizer
			while(result.next()) {
				String user_id = String.valueOf(result.getInt("user_id"));
				String user_email = result.getString("user_email");
				String user_name = result.getString("user_name");
				String user_role = result.getString("user_role");
				organizer = new EventOrganizer(user_id, user_email, user_name, "", user_role);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// return organizer yang diambil dari database
		return organizer;
	}

	public String getEvents_created() {
		return events_created;
	}

	public void setEvents_created(String events_created) {
		this.events_created = events_created;
	}
}
