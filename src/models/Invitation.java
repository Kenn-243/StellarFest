package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

public class Invitation {
	private String invitation_id;
	private String event_id;
	private String user_id;
	private String invitation_status;
	private String invitation_role;
	
	private CheckBox select;
	
	public Invitation(String invitation_id, String event_id, String user_id, String invitation_status, String invitation_role) {
		super();
		this.invitation_id = invitation_id;
		this.event_id = event_id;
		this.user_id = user_id;
		this.invitation_status = invitation_status;
		this.invitation_role = invitation_role;
		this.select = new CheckBox();
	}

	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Invitation
	public static String sendInvitation(String email, Event event) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk insert invitation ke dalam database
		String query = "INSERT INTO `invitation` (event_id, user_id, invitation_status, invitation_role) VALUES (?, ?, ?, ?)";
		User user = User.getUserByEmail(email);
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(event.getEvent_id()));
			connection.getPreparedStatement().setInt(2, Integer.parseInt(user.getUser_id()));
			connection.getPreparedStatement().setString(3, "Pending");
			connection.getPreparedStatement().setString(4, user.getUser_role());
			// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
			connection.executeUpdate();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// kalau berhasil return success
		return "Success";
	}
	
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Invitation
	public static String acceptInvitation(String eventID, User user) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk update invitation_status di dalam database
		String query = "UPDATE `invitation` SET invitation_status = ? WHERE event_id = ? AND user_id = ?";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setString(1, "Accepted");
			connection.getPreparedStatement().setInt(2, Integer.parseInt(eventID));
			connection.getPreparedStatement().setInt(3, Integer.parseInt(user.getUser_id()));
			// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
			connection.executeUpdate();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		// return success kalau berhasil
		return "Success";
	}
	
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Invitation
	public static ObservableList<Invitation> getInvitations(String email) {
		// inisialisasi invitationList untuk menampung invitation milik user, baik guest atau vendor, yang masih pending
		ObservableList<Invitation> invitationList = FXCollections.observableArrayList();
		
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk mengambil invitation milik guest atau vendor yang masih pending
		String query = "SELECT * FROM `invitation` WHERE user_id = ? AND invitation_status = ?";
		User user = User.getUserByEmail(email);
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setString(1, user.getUser_id());
			connection.getPreparedStatement().setString(2, "Pending");
			// gunakan executeQuery dan tampung ke dalam variable ResultSet result
			ResultSet result = connection.executeQuery();
			// selagi result masih ada isi, maka isinya ditambahkan ke dalam invitationList 
			while(result.next()) {
				String invitation_id = String.valueOf(result.getInt("invitation_id"));
				String event_id = String.valueOf(result.getInt("event_id"));
				String user_id = String.valueOf(result.getInt("user_id"));
				String invitation_status = result.getString("invitation_status");
				String invitation_role = result.getString("invitation_role");
				invitationList.add(new Invitation(invitation_id, event_id, user_id, invitation_status, invitation_role));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// return invitationList yang sudah ada isi
		return invitationList;
	}

	public String getInvitation_id() {
		return invitation_id;
	}

	public void setInvitation_id(String invitation_id) {
		this.invitation_id = invitation_id;
	}

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getInvitation_status() {
		return invitation_status;
	}

	public void setInvitation_status(String invitation_status) {
		this.invitation_status = invitation_status;
	}

	public String getInvitation_role() {
		return invitation_role;
	}

	public void setInvitation_role(String invitation_role) {
		this.invitation_role = invitation_role;
	}
	
	public CheckBox getSelect() {
		return select;
	}

	public void setSelect(CheckBox select) {
		this.select = select;
	}
}
