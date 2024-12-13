package controller;

import java.util.Date;

import javafx.collections.ObservableList;
import models.Event;
import models.Guest;
import models.Invitation;
import models.Vendor;

public class EventOrganizerController {
	public void createEvent(String eventName, Date date, String location, String description, String organizerID) {
		
	}
	
	/*
	 * ASUMSI:
	 * 1. return type-nya menggunakan ObservableList karena di class diagram tidak di-specify
	 * 2. Di Class Diagram, Event class tidak punya method viewOrganizedEvents(userID), tapi di sequence ada, jadi ikuti sequence
	 */
	public ObservableList<Event> viewOrganizedEvents(String userID) {
		return Event.viewOrganizedEvents(userID);
	}
	
	public void viewOrganizedEventDetails(String eventID) {
		
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
	
	public void checkAddGuestInput(String vendorID) {
		
	}
	
	public void editEventName(String eventID, String eventName) {
		
	}
	
	/*
	 * Di Class Diagram: tidak ada sendInvitation(email) di class ini, tapi di Sequence Diagram ada
	 * Di Class Diargam, sendInvitation(email) adanya di InvitationController, tapi diarahkan untuk mengikuti sequence diagram
	 * ASUMSI:
	 * 1. ikuti sequence diagram, tambahkan sendInvitation(String email)
	 * 2. method sendInvitation(email) di InvitationController class akan dikosongkan.
	 * 3. return String karena mau return error message
	 * 4. parameter event ditambahkan agar bisa mendapat
	 */
	public String sendInvitation(String email, Event event) {
		String response = checkAddVendorInput(email);
		if(response.equals("Success")) {
			return Invitation.sendInvitation(email, event);
		}else {
			return response;
		}
	}
}
