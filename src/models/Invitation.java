package models;

import java.sql.SQLException;

import data.DatabaseConnection;

public class Invitation {
	private String invitation_id;
	private String event_id;
	private String user_id;
	private String invitation_status;
	private String invitation_role;
	
	public static String sendInvitation(String email, Event event) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "INSERT INTO `invitation` (event_id, user_id, invitation_status, invitation_role) VALUES (?, ?, ?, ?)";
		User user = User.getUserByEmail(email);
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(event.getEvent_id()));
			connection.getPreparedStatement().setInt(2, Integer.parseInt(user.getUser_id()));
			connection.getPreparedStatement().setString(3, "Invited");
			connection.getPreparedStatement().setString(4, user.getUser_role());
			connection.executeUpdate();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "Success";
	}
	
	public void acceptInvitation(String eventID) {
		
	}
	
	public void getInvitations(String email) {
		
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
}
