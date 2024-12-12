package controller;

import javafx.collections.ObservableList;
import models.Admin;
import models.Event;

public class AdminController {
	public ObservableList<Event> viewAllEvents() {
		return Admin.viewAllEvents();
	}
	
	public void viewEventDetails(String eventID) {
		
	}
	
	public void deleteEvent(String eventID) {
		
	}
	
	public void deleteUser(String userID) {
		
	}
	
	public void getAllUsers() {
		
	}
	
	public void getAllEvents() {
		
	}
	
	public void getGuestsByTransactionID(String eventID) {
		
	}
	
	public void getVendorsByTransactionID(String eventID) {
		
	}
}
