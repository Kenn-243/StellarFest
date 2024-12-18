package controller;

import java.sql.Date;
import java.time.LocalDate;

import models.Event;

public class EventController {
	public String createEvent(String eventName, Date date, String location, String description, String organizerID) {
		// panggil method checkEventInput untuk validasi input dan tampung ke dalam variable String response
		String response = checkEventInput(eventName, date, location, description);
		// kalau response = success, maka panggil method createEvent dari Event class
		if(response.equals("Success")) {
			response = Event.createEvent(eventName, date, location, description, organizerID);
		}
		return response;
	}
	
	public String checkEventInput(String eventName, Date date, String location, String description) {
		// validasi eventName tidak boleh kosong
		if(eventName.isBlank()) {
			return "Name is required";
		}
		// validasi date tidak boleh kosong
		else if(date == null) {
			return "Date is required";
		}
		// validasi date tidak boleh dihari yang sama
		else if(!date.after(Date.valueOf(LocalDate.now()))) {
			return "Date must be in the future";
		}
		// validasi location tidak boleh kosong
		else if(location.isBlank()) {
			return "Location is required";
		}
		// validasi description tidak boleh kosong
		else if(description.isBlank()) {
			return "Description is required";
		}
		// validasi description tidak boleh > 200 huruf
		else if(description.length() > 200) {
			return "Description must be 200 characters or fewer";
		}
		// kalau semuanya berhasil dilewati, return success
		else {
			return "Success";
		}
	}
	
	public void viewEventDetails(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	// ditambahkan buat mendapatkan event berdasarkan ID
	public Event getEventById(String eventID) {
		// panggil method getEventById dari Event class
		return Event.getEventById(eventID);
	}
}
