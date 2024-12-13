package controller;

import java.sql.Date;
import java.time.LocalDate;

import models.Event;

public class EventController {
	public String createEvent(String eventName, Date date, String location, String description, String organizerID) {
		String response = checkEventInput(eventName, date, location, description);
		if(response.equals("Success")) {
			response = Event.createEvent(eventName, date, location, description, organizerID);
		}
		
		return response;
	}
	
	public String checkEventInput(String eventName, Date date, String location, String description) {
		if(eventName.isBlank()) {
			return "Name is required";
		}else if(date == null) {
			return "Date is required";
		}else if(!date.after(Date.valueOf(LocalDate.now()))) {
			return "Date must be in the future";
		}else if(location.isBlank()) {
			return "Location is required";
		}else if(description.isBlank()) {
			return "Description is required";
		}else if(description.length() > 200) {
			return "Description must be 200 characters or fewer";
		}else {
			return "Success";
		}
	}
	
	public void viewEventDetails(String eventID) {
		
	}
	
	// ditambahkan buat mendapatkan event berdasarkan ID
	public Event getEventById(String eventID) {
		return Event.getEventById(eventID);
	}
}
