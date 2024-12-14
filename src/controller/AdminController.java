package controller;

import javafx.collections.ObservableList;
import models.Admin;
import models.Event;
import models.Guest;
import models.User;
import models.Vendor;

public class AdminController {
	public ObservableList<Event> viewAllEvents() {
		return Admin.viewAllEvents();
	}
	
	public ObservableList<User> viewEventDetails(String eventID) {
		ObservableList<User> attendeeList = Guest.getGuestsByTransactionID(eventID);
		attendeeList.addAll(Vendor.getVendorsByTransactionID(eventID));
		return attendeeList;
	}
	
	public void deleteEvent(String eventID) {
		Event.deleteEvent(eventID);
	}
	
	public void deleteUser(String userID) {
		User.deleteUser(userID);
	}
	
	public ObservableList<User> getAllUsers() {
		return User.getAllUsers();
	}
	
	public void getAllEvents() {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan
	}
	
	public void getGuestsByTransactionID(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan
	}
	
	public void getVendorsByTransactionID(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan
	}
}
