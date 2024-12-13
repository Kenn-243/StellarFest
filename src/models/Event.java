package models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Event {
	private String event_id;
	private String event_name;
	private String event_date;
	private String event_location;
	private String event_description;
	private String organizer_id;
	
	public Event(String event_id, String event_name, String event_date, String event_location, String event_description, String organizer_id) {
		super();
		this.event_id = event_id;
		this.event_name = event_name;
		this.event_date = event_date;
		this.event_location = event_location;
		this.event_description = event_description;
		this.organizer_id = organizer_id;
	}

	public static String createEvent(String eventName, Date date, String location, String description, String organizerID) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "INSERT INTO `event` (event_name, event_date, event_location, event_description, organizer_id) VALUES (?, ?, ?, ?, ?);";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setString(1, eventName);
			connection.getPreparedStatement().setDate(2, date);
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
	
	public static ObservableList<Event> viewOrganizedEvents(String userID) {
        ObservableList<Event> eventList = FXCollections.observableArrayList();

		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "SELECT * FROM `event` WHERE organizer_id = ?";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(userID));
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
	
	// ditambahkan buat mendapatkan event berdasarkan id;
	public static Event getEventById(String eventID) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "SELECT * FROM `event` WHERE event_id = ?";
		Event event = null;
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(eventID));
			ResultSet result = connection.executeQuery();
			while(result.next()) {
				String event_id = String.valueOf(result.getInt("event_id"));
				String event_name = result.getString("event_name");
				String event_date = result.getDate("event_date").toString();
				String event_location = result.getString("event_location");
				String event_description = result.getString("event_description");
				String organizer_id = String.valueOf(result.getInt("organizer_id"));
				event = new Event(event_id, event_name, event_date, event_location, event_description, organizer_id);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return event;
	}
	
	/*
	 * Di Class Diagram tidak ada, tapi di Sequence Diagram untuk viewAcceptedEvents ada
	 * ASUMSI:
	 * 1. return type-nya menggunakan ObservableList karena di class diagram tidak di-specify
	 * 2. diarahkan untuk mengikuti sequence diagram
	 */
	public static ObservableList<Event> viewAcceptedEvents(String email) {
		ObservableList<Event> eventList = FXCollections.observableArrayList();
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "SELECT * FROM `event` AS e JOIN `invitation` AS i ON e.event_id = i.event_id WHERE i.user_id = ? AND i.invitation_status = ?";
		User user = User.getUserByEmail(email);
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(user.getUser_id()));
			connection.getPreparedStatement().setString(2, "Accepted");
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

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public String getEvent_date() {
		return event_date;
	}

	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}

	public String getEvent_location() {
		return event_location;
	}

	public void setEvent_location(String event_location) {
		this.event_location = event_location;
	}

	public String getEvent_description() {
		return event_description;
	}

	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}

	public String getOrganizer_id() {
		return organizer_id;
	}

	public void setOrganizer_id(String organizer_id) {
		this.organizer_id = organizer_id;
	}
}
