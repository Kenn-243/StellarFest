package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

public class Vendor extends User{
	private String accepted_invitation;
	
	// CheckBox ditambahkan supaya di TableView, setiap row memiliki kolom yang isinya checkbox
	private CheckBox select;
	
	public Vendor(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
		this.select = new CheckBox();
	}
	
	public void acceptInvitation(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void viewAcceptedEvents(String email) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	/*
	 * ASUMSI:
	 * 1. manageVendor = add product
	 * 2. tambah parameter vendorId supaya bisa punay relation dengan vendor di dalam database
	 */
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Vendor
	public static String manageVendor(String description, String  product, String vendorId) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk menginsert product ke dalam database
		String query = "INSERT INTO `product` (vendor_id, product_name, product_description) VALUES (?, ?, ?)";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(vendorId));
			connection.getPreparedStatement().setString(2, product);
			connection.getPreparedStatement().setString(3, description);
			// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
			connection.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		// kalau berhasil diinput, maka return Success
		return "Success";
	}
	
	public void checkManageVendorInput(String description, String product) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Vendor
	public static ObservableList<Vendor> getVendors(Event event) {
		// inisialisasi vendorList yang akan menampung semua user dengan role vendor
		ObservableList<Vendor> vendorList = FXCollections.observableArrayList();

		DatabaseConnection connection = DatabaseConnection.getInstance();
		/*
		 * Di Soal: Make sure if displayed Vendor hasn’t already invited to events.
		 * ASUMSI:
		 * 1. vendor sudah pernah menerima invitation untuk event lain, tapi belum pernah accept invitation akan ditampilkan
		 * 2. vendor sudah pernah diundang ke event yang sama tidak akan ditampilkan
		 * 2. invitation_role dianggap sama dengan user_role
		 * 3. invitation status kalau sudah diaccept oleh Vendor adalah "Accepted" dan kalau belum statusnya "Pending"
		 */
		String query = "SELECT user_id, user_email, user_name, user_role FROM `user` "
				+ "WHERE user_role = ? AND user_id NOT IN ( "
				+ "SELECT user_id FROM `invitation` "
				+ "WHERE (event_id = ? AND invitation_role = ? AND (invitation_status = ? OR invitation_status = ?)))";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setString(1, "Vendor");
			connection.getPreparedStatement().setInt(2, Integer.parseInt(event.getEvent_id()));
			connection.getPreparedStatement().setString(3, "Vendor");
			connection.getPreparedStatement().setString(4, "Accepted");
			connection.getPreparedStatement().setString(5, "Pending");
			// gunakan executeQuery dan tampung ke dalam variable ResultSet result
			ResultSet result = connection.executeQuery();
			// selagi result masih ada isi, maka isinya akan ditampung ke dalam vendorList
			while(result.next()) {
				String user_id = String.valueOf(result.getInt("user_id"));
				String user_email = result.getString("user_email");
				String user_name = result.getString("user_name");
				String user_role = result.getString("user_role");
				vendorList.add(new Vendor(user_id, user_email, user_name, "", user_role));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// return vendorList yang sudah ada isi
		return vendorList;
	}
	
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Vendor
	public static ObservableList<User> getVendorsByTransactionID(String eventID) {
		// inisialisasi vendorList yang akan menampung semua vendor yang sudah menerima invitation untuk event berdasarkan event id
		ObservableList<User> vendorList = FXCollections.observableArrayList();

		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk mengambil vendor yang diundang ke event tersebut dan meng-accept invitation untuk event tersebut 
		String query = "SELECT u.user_id, u.user_email, u.user_name, u.user_role FROM `user` AS u JOIN `invitation` AS i ON u.user_id = i.user_id WHERE i.event_id = ? AND i.invitation_status = ? AND i.invitation_role = ?";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(eventID));
			connection.getPreparedStatement().setString(2, "Accepted");
			connection.getPreparedStatement().setString(3, "Vendor");
			// gunakan executeQuery dan tampung ke dalam variable
			ResultSet result = connection.executeQuery();
			// selagi result masih ada isi, maka isinya akan ditampung ke dalam vendorList
			while(result.next()) {
				String user_id = String.valueOf(result.getInt("user_id"));
				String user_email = result.getString("user_email");
				String user_name = result.getString("user_name");
				String user_role = result.getString("user_role");
				vendorList.add(new Vendor(user_id, user_email, user_name, "", user_role));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// return vendorList yang sudah ada isi
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
