package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

public class Guest extends User{
	private String accepted_invitations;
	
	// CheckBox ditambahkan supaya di TableView, setiap row memiliki kolom yang isinya checkbox
	private CheckBox select;
	
	public Guest(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
		this.select = new CheckBox();
	}
	
	public void acceptInvitation(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void viewAcceptedEvents(String email) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	// ASUMSI: parameter event ditambahkan supaya bisa dapatin event_id
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Guest
	public static ObservableList<Guest> getGuests(Event event) {
		// inisialisasi guestList yang akan menampung semua user dengan role guest
		ObservableList<Guest> guestList = FXCollections.observableArrayList();

		DatabaseConnection connection = DatabaseConnection.getInstance();
		/*
		 * Di Soal: Make sure if displayed Guest hasn’t already invited to events.
		 * ASUMSI:
		 * 1. guest sudah pernah menerima invitation untuk event lain, tapi belum pernah accept invitation akan ditampilkan
		 * 2. guest sudah pernah diundang ke event yang sama tidak akan ditampilkan
		 * 3. invitation_role dianggap sama dengan user_role
		 * 4. invitation status kalau sudah diaccept oleh Vendor adalah "Accepted" dan kalau belum statusnya "Pending"
		 */
		String query = "SELECT user_id, user_email, user_name, user_role FROM `user` "
				+ "WHERE user_role = ? AND user_id NOT IN ( "
				+ "SELECT user_id FROM `invitation` "
				+ "WHERE (event_id = ? AND invitation_role = ? AND (invitation_status = ? OR invitation_status = ?)))";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setString(1, "Guest");
			connection.getPreparedStatement().setInt(2, Integer.parseInt(event.getEvent_id()));
			connection.getPreparedStatement().setString(3, "Guest");
			connection.getPreparedStatement().setString(4, "Accepted");
			connection.getPreparedStatement().setString(5, "Pending");
			// gunakan executeQuery dan tampung ke dalam variable ResultSet result
			ResultSet result = connection.executeQuery();
			// selagi result masih ada isi, maka isinya akan ditambahkan ke dalam guestList
			while(result.next()) {
				String user_id = String.valueOf(result.getInt("user_id"));
				String user_email = result.getString("user_email");
				String user_name = result.getString("user_name");
				String user_role = result.getString("user_role");
				guestList.add(new Guest(user_id, user_email, user_name, "", user_role));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// return guestList yang sudah ada isi
		return guestList;
	}
	
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Guest
	public static ObservableList<User> getGuestsByTransactionID(String eventID) {
		// inisialisasi guestList yang akan menampung semua guest yang sudah menerima invitation untuk event berdasarkan event id
		ObservableList<User> guestList = FXCollections.observableArrayList();

		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk mengambil semua guest yang diundang ke event tersebut dan meng-accept invitation untuk event tersebut
		String query = "SELECT u.user_id, u.user_email, u.user_name, u.user_role FROM `user` AS u JOIN `invitation` AS i ON u.user_id = i.user_id WHERE i.event_id = ? AND i.invitation_status = ? AND i.invitation_role = ?";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(eventID));
			connection.getPreparedStatement().setString(2, "Accepted");
			connection.getPreparedStatement().setString(3, "Guest");
			// gunakan executeQuery dan tampung ke dalam variable 
			ResultSet result = connection.executeQuery();
			// selagi result masih ada isi, maka isinya akan ditambahkan ke dalam guestList
			while(result.next()) {
				String user_id = String.valueOf(result.getInt("user_id"));
				String user_email = result.getString("user_email");
				String user_name = result.getString("user_name");
				String user_role = result.getString("user_role");
				guestList.add(new Guest(user_id, user_email, user_name, "", user_role));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// return guestList yang sudah ada isi
		return guestList;
	}

	public String getAccepted_invitations() {
		return accepted_invitations;
	}

	public void setAccepted_invitations(String accepted_invitations) {
		this.accepted_invitations = accepted_invitations;
	}

	public CheckBox getSelect() {
		return select;
	}

	public void setSelect(CheckBox select) {
		this.select = select;
	}
}
