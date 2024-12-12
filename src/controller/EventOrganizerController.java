package controller;

import java.util.Date;

import javafx.collections.ObservableList;
import models.Event;
import models.Vendor;

public class EventOrganizerController {
	public void createEvent(String eventName, Date date, String location, String description, String organizerID) {
		
	}
	
	public ObservableList<Event> viewOrganizedEvents(String userID) {
		return Event.viewOrganizedEvents(userID);
	}
	
	public void viewOrganizedEventDetails(String eventID) {
		
	}
	
	public void getGuest() {
		
	}
	
	public ObservableList<Vendor> getVendors() {
		return Vendor.getVendors();
	}
	
	public void getGuestsByTransactionID(String eventID) {
		
	}
	
	public void getVendorsByTransactionID(String eventID) {
		
	}
	
	public void checkCreateEventInput(String eventName, Date date, String location, String description) {
		
	}
	
	public void checkAddVendorInput(String vendorID) {
		
	}
	
	public void checkAddGuestInput(String vendorID) {
		
	}
	
	public void editEventName(String eventID, String eventName) {
		
	}
}
