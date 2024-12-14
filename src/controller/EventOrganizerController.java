package controller;

import java.util.Date;

import javafx.collections.ObservableList;
import models.Event;
import models.EventOrganizer;
import models.Guest;
import models.Invitation;
import models.User;
import models.Vendor;

public class EventOrganizerController {
	public void createEvent(String eventName, Date date, String location, String description, String organizerID) {
		// dikosongkan karena berdasarkan sequence diagram dilempar ke EventController
	}
	
	/*
	 * ASUMSI:
	 * 1. return type-nya menggunakan ObservableList karena di class diagram tidak di-specify
	 * 2. Di Class Diagram, Event class tidak punya method viewOrganizedEvents(userID), tapi di sequence ada, jadi ikuti sequence
	 */
	public ObservableList<Event> viewOrganizedEvents(String userID) {
		return Event.viewOrganizedEvents(userID);
	}
	
	public ObservableList<User> viewOrganizedEventDetails(String eventID) {
		ObservableList<User> attendeeList = Guest.getGuestsByTransactionID(eventID);
		attendeeList.addAll(Vendor.getVendorsByTransactionID(eventID));
		return attendeeList;
	}
	
	/*
	 * ASUMSI:
	 * 1. return type-nya menggunakan ObservableList karena di class diagram tidak di-specify
	 * 2. Di Sequence Diagram, panahnya ke vendor, tapi jadinya aneh karena lagi mau getGuests()
	 * 3. method getGuests(event) diarahkan ke Guest class supaya lebih masuk akal
	 * 4. tambah parameter event supaya bisa check apakah vendor sudah pernah diundang ke event tersebut
	 */
	public ObservableList<Guest> getGuests(Event event) {
		return Guest.getGuests(event);
	}
	
	/*
	 * ASUMSI:
	 * 1. return type-nya menggunakan ObservableList karena di class diagram tidak di-specify
	 * 2. Di Class Diagram, Vendor class tidak punya method getVendors(), tapi di sequence ada, jadi ikuti sequence
	 * 3. tambah parameter event supaya bisa check apakah vendor sudah pernah diundang ke event tersebut
	 */
	public ObservableList<Vendor> getVendors(Event event) {
		return Vendor.getVendors(event);
	}
	
	public void getGuestsByTransactionID(String eventID) {
		
	}
	
	public void getVendorsByTransactionID(String eventID) {
		
	}
	
	public void checkCreateEventInput(String eventName, Date date, String location, String description) {
		
	}
	
	/*
	 * Di Class Diagram pakai method checkAddVendorInput(vendorID), tapi di Sequence Diagram pakai checkAddVendorInput(email)
	 * ASUMSI:
	 * 1. ikuti sequence diagram, gunakan checkAddVendorInput(email)
	 * 2. untuk validasi, asumsikan validasi email benar (diakhiri @gmail.com) + tidak kosong
	 * 3. return String karena mau return error message
	 */
	public String checkAddVendorInput(String email) {
		if(email.isBlank()) {
			return "Email is not found";
		}else if(!email.endsWith("@gmail.com")) {
			return "Email is invalid";
		}else {
			return "Success";
		}
	}
	
	/*
	 * Di Class Diagram pakai method checkAddGuestInput(vendorID), tapi di Sequence Diagram pakai checkAddVendorInput(email)
	 * ASUMSI:
	 * 1. ikuti sequence diagram, gunakan checkAddGuestInput(email)
	 * 2. untuk validasi, asumsikan validasi email benar (diakhiri @gmail.com) + tidak kosong
	 * 3. return String karena mau return error message
	 */
	public String checkAddGuestInput(String email) {
		if(email.isBlank()) {
			return "Email is not found";
		}else if(!email.endsWith("@gmail.com")) {
			return "Email is invalid";
		}else {
			return "Success";
		}
	}
	
	public String editEventName(String eventID, String eventName) {
		String response = checkEditEventInput(eventName);
		if(response.equals("Success")) {
			return Event.editEventName(eventID, eventName);
		}else {
			return response;
		}
	}
	
	/*
	 * Di Class Diargam, sendInvitation(email) adanya di InvitationController, tapi diarahkan untuk mengikuti sequence diagram
	 * ASUMSI:
	 * 1. ikuti sequence diagram, tambahkan sendInvitation(String email)
	 * 2. method sendInvitation(email) di InvitationController class akan dikosongkan.
	 * 3. return String karena mau return error message
	 * 4. parameter event ditambahkan agar bisa mendapat
	 * 5. parameter target ditambahkan agar function checkAddGuestInput(email) dan checkAddVendorInput(email) bisa terpakai :)
	 */
	public String sendInvitation(String email, Event event, String target) {
		String response;
		if(target.equals("Vendor")) {
			response = checkAddVendorInput(email);
		}else if(target.equals("Guest")) {
			response = checkAddGuestInput(email);
		}else {
			response = "Can't invite other than Vendor or Guest";
		}
		
		if(response.equals("Success")) {
			return Invitation.sendInvitation(email, event);
		}else {
			return response;
		}	
	}
	
	// ditambahkan buat mendapatkan event organizer berdasarkan ID
	public EventOrganizer getOrganizerById(String organizerId) {
		return EventOrganizer.getOrganizerById(organizerId);
	}
	
	/*
	 * Di Class Diargam tidak checkEditEventInput(eventName) di class manapun, tapi diarahkan untuk mengikuti sequence diagram
	 * ASUMSI:
	 * 1. ikuti sequence diagram, tambahkan checkEditEventInput(eventName)
	 */
	public String checkEditEventInput(String eventName) {
		if(eventName.isBlank()) {
			return "Event name is required";
		}else {
			return "Success";
		}
	}
}
