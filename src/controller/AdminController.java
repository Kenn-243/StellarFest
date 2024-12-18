package controller;

import javafx.collections.ObservableList;
import models.Admin;
import models.Event;
import models.Guest;
import models.User;
import models.Vendor;

public class AdminController {
	public ObservableList<Event> viewAllEvents() {
		// panggil method viewAllEvents dari Admin class
		return Admin.viewAllEvents();
	}
	
	public ObservableList<User> viewEventDetails(String eventID) {
		// panggil method getGuestsByTransactionID dari Guest class dan simpan di dalam attendeeList
		ObservableList<User> attendeeList = Guest.getGuestsByTransactionID(eventID);
		// tambahin vendor ke dalam attendeeList dengan memanggil method getVendorsByTransactionId dari Vendor class
		attendeeList.addAll(Vendor.getVendorsByTransactionID(eventID));
		// return attendeeList yang isinya semua guest dan vendor berdasarkan event id
		return attendeeList;
	}
	
	public void deleteEvent(String eventID) {
		// panggil method deleteEvent dari Event class
		Event.deleteEvent(eventID);
	}
	
	public void deleteUser(String userID) {
		// panggil method deleteUser dari User class
		User.deleteUser(userID);
	}
	
	public ObservableList<User> getAllUsers() {
		// panggil method getAllUsers dari User class
		return User.getAllUsers();
	}
	
	public void getAllEvents() {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void getGuestsByTransactionID(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void getVendorsByTransactionID(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
}
