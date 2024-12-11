package models;

import java.sql.SQLException;
import java.util.Date;

import data.DatabaseConnection;

public class Event {
	private String event_id;
	private String event_name;
	private String event_date;
	private String event_location;
	private String event_description;
	private String organization_id;

	public String createEvent(String eventName, Date date, String location, String description, String organizerID) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "INSERT INTO `Event` (event_name, event_date, event_location, event_description, organizer_id) VALUES (?, ?, ?, ?, ?);";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setString(1, eventName);
			connection.getPreparedStatement().setDate(2, (java.sql.Date) date);
			connection.getPreparedStatement().setString(3, location);
			connection.getPreparedStatement().setString(4, description);
			connection.getPreparedStatement().setInt(5, Integer.parseInt(organizerID));
			connection.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return "Success";
	}
	
	public void viewEventDetails(String eventID) {
		
	}
}
