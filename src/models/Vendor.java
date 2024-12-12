package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

public class Vendor extends User{
	private String accepted_invitation;
	
	private CheckBox select;
	
	public Vendor(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
		this.select = new CheckBox();
	}
	
	public void acceptInvitation(String eventID) {
		
	}
	
	public void viewAcceptedEvents(String email) {
		
	}
	
	public void manageVendor(String description, String  product) {
		
	}
	
	public void checkManageVendorInput(String description, String product) {
		
	}
	
	public static ObservableList<Vendor> getVendors() {
		ObservableList<Vendor> vendorList = FXCollections.observableArrayList();

		DatabaseConnection connection = DatabaseConnection.getInstance();
		/*
		 * Di Soal: Make sure if displayed Vendor hasn’t already invited to events.
		 * ASUMSI:
		 * 1. vendor sudah pernah menerima invitation, tapi belum pernah accept invitation!
		 * 2. invitation_role dianggap sama dengan user_role
		 * 3. invitation status kalau sudah diaccept oleh Vendor adalah "Accepted"
		 */
		String query = "SELECT user_id, user_email, user_name, user_role FROM `user` WHERE user_role = ? AND user_id NOT IN ( SELECT user_id FROM `invitation` WHERE invitation_role = ? AND invitation_status = ?)";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setString(1, "Vendor");
			connection.getPreparedStatement().setString(2, "Vendor");
			connection.getPreparedStatement().setString(3, "Accepted");
			ResultSet result = connection.executeQuery();
			while(result.next()) {
				vendorList.add(new Vendor(String.valueOf(result.getInt("user_id")), result.getString("user_email"), result.getString("user_name"), "", result.getString("user_role")));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return vendorList;
	}

	public String getAccepted_invitation() {
		return accepted_invitation;
	}

	public void setAccepted_invitation(String accepted_invitation) {
		this.accepted_invitation = accepted_invitation;
	}
	
	public CheckBox getSelect() {
		return select;
	}
	
	public void setSelect(CheckBox select) {
		this.select = select;
	}
}
