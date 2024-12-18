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
	
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Event
	public static String createEvent(String eventName, Date date, String location, String description, String organizerID) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk menginsert event ke dalam database
		String query = "INSERT INTO `event` (event_name, event_date, event_location, event_description, organizer_id) VALUES (?, ?, ?, ?, ?);";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setString(1, eventName);
			connection.getPreparedStatement().setDate(2, date);
			connection.getPreparedStatement().setString(3, location);
			connection.getPreparedStatement().setString(4, description);
			connection.getPreparedStatement().setInt(5, Integer.parseInt(organizerID));
			// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
			connection.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		// kalau berhasil diinsert, maka return Success
		return "Success";
	}
	
	public void viewEventDetails(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Event
	public static ObservableList<Event> viewOrganizedEvents(String userID) {
		// inisialisasi eventList yang akan menampung semua event yang dibuat oleh organizer berdasarkan user id
        ObservableList<Event> eventList = FXCollections.observableArrayList();

		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk mengambil semua event berdasarkan organizer id
		String query = "SELECT * FROM `event` WHERE organizer_id = ?";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(userID));
			// gunakan executeQuery dan tampung ke dalam variable ResultSet result
			ResultSet result = connection.executeQuery();
			// selagi result masih ada isi, maka isinya dimasukkan ke eventList
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
		// return eventList yang sudah ada isi
		return eventList;
	}
	
	// ditambahkan buat mendapatkan event berdasarkan id;
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Event
	public static Event getEventById(String eventID) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk mengambil event berdasarkan event id
		String query = "SELECT * FROM `event` WHERE event_id = ?";
		Event event = null;
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(eventID));
			// gunakan executeQuery dan tampung ke dalam variable ResultSet result
			ResultSet result = connection.executeQuery();
			// result hanya akan memiliki 1 isi karena query berdasarkan id
			// hasilnya akan ditampung ke dalam object event
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
		// return event yang didapatkan dari database
		return event;
	}
	
	/*
	 * Di Class Diagram tidak ada, tapi di Sequence Diagram untuk viewAcceptedEvents ada
	 * ASUMSI:
	 * 1. return type-nya menggunakan ObservableList karena di class diagram tidak di-specify
	 * 2. diarahkan untuk mengikuti sequence diagram
	 */
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Event
	public static ObservableList<Event> viewAcceptedEvents(String email) {
		// inisialisasi eventList yang akan menampung semua event yang diaccept oleh user, baik guest atau vendor
		ObservableList<Event> eventList = FXCollections.observableArrayList();
		
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk mengambil event yang diaccept oleh user, baik guest atau vendor
		String query = "SELECT * FROM `event` AS e JOIN `invitation` AS i ON e.event_id = i.event_id WHERE i.user_id = ? AND i.invitation_status = ?";
		// mendapatkan object user berdasarkan email
		User user = User.getUserByEmail(email);
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(user.getUser_id()));
			connection.getPreparedStatement().setString(2, "Accepted");
			// gunakan executeQuery dan tampung ke dalam variable ResultSet result
			ResultSet result = connection.executeQuery();
			// selagi result masih ada isi, maka isinya akan ditambahkan ke dalam eventList
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
		// return eventList yang sudah ada isi
		return eventList;
	}
	
	/*
	 * Di Class Diagram tidak ada, tapi di Sequence Diagram untuk editEventName ada
	 * ASUMSI:
	 * 1. ditambahkan karena mengikuti sequence diagram
	 */
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Event
	public static String editEventName(String eventID, String eventName) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk update event_name di dalam table event di database
		String query = "UPDATE `event` SET event_name = ? WHERE event_id = ?";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setString(1, eventName);
			connection.getPreparedStatement().setInt(2, Integer.parseInt(eventID));
			// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
			connection.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// kalau berhasil diupdate, maka return Success
		return "Success";
	}
	
	/*
	 * Di Class Diagram tidak ada, tapi di Sequence Diagram untuk deleteEvent ada
	 * ASUMSI:
	 * 1. ditambahkan karena mengikuti sequence diagram
	 */
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Event
	public static void deleteEvent(String eventID) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk delete invitation berdasarkan event_id (supaya datanya tetap konsisten)
		// karena tidak ditambahkan ON DELETE Cascade
		String query = "DELETE FROM `invitation` WHERE event_id = ?";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(eventID));
			// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
			connection.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		// query untuk delete event berdasarkan event_id
		String query2 = "DELETE FROM `event` WHERE event_id = ?";
		connection.setPreparedStatement(query2);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(eventID));
			// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
			connection.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
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
