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

	public static ObservableList<Event> viewAllEvents() {
		ObservableList<Event> eventList = FXCollections.observableArrayList();

		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "SELECT * FROM `event`";
		
		connection.setPreparedStatement(query);
		try {
			ResultSet result = connection.executeQuery();
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
		return eventList;
	}
	
	public void viewEventDetails(String eventID) {
		
	}
	
	public void deleteEvent(String eventID) {
		
	}
	
	public void deleteUser(String userID) {
		
	}
	
	public void getAllUsers() {
		
	}
	
	public void getAllEvents() {
		
	}
	
	public void getGuestsByTransactionID(String eventID) {
		
	}
	
	public void getVendorsByTransactionID(String eventID) {
		
	}
}
