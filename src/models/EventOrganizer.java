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
		// dikosongin, karena ikutin sequence diagram
	}
	
	public void viewOrganizedEvents(String userID) {
		// dikosongin, karena ikutin sequence diagram
	}
	
	public void viewOrganizedEventDetails(String eventID) {
		// dikosongin, karena ikutin sequence diagram
	}
	
	public void getGuest() {
		// dikosongin, karena ikutin sequence diagram
	}
	
	public void getVendors() {
		// dikosongin, karena ikutin sequence diagram
	}
	
	public void getGuestsByTransactionID(String eventID) {
		// dikosongin, karena ikutin sequence diagram
	}
	
	public void getVendorsByTransactionID(String eventID) {
		// dikosongin, karena ikutin sequence diagram
	}
	
	public void checkCreateEventInput(String eventName, Date date, String location, String description) {
		// dikosongin, karena ikutin sequence diagram
	}
	
	public void checkAddVendorInput(String vendorID) {
		// dikosongin, karena ikutin sequence diagram
	}
	
	public void checkAddGuestInput(String vendorID) {
		// dikosongin, karena ikutin sequence diagram
	}
	
	public void editEventName(String eventID, String eventName) {
		
	}
	
	// ditambahkan buat mendapatkan event organizer berdasarkan ID
	public static EventOrganizer getOrganizerById(String organizerId) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "SELECT user_id, user_name, user_email, user_role FROM `user` WHERE user_id = ?";
		EventOrganizer organizer = null;
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(organizerId));
			ResultSet result = connection.executeQuery();
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
		
		return organizer;
	}

	public String getEvents_created() {
		return events_created;
	}

	public void setEvents_created(String events_created) {
		this.events_created = events_created;
	}
}
