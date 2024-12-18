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
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	/*
	 * ASUMSI:
	 * 1. return type-nya menggunakan ObservableList karena di class diagram tidak di-specify
	 * 2. Di Class Diagram, Event class tidak punya method viewOrganizedEvents(userID), tapi di sequence ada, jadi ikuti sequence
	 */
	public ObservableList<Event> viewOrganizedEvents(String userID) {
		// panggil method viewOrganizedEvents dari Event class
		return Event.viewOrganizedEvents(userID);
	}
	
	public ObservableList<User> viewOrganizedEventDetails(String eventID) {
		// panggil method getGuestsByTransactionID dari Guest class dan simpan di dalam attendeeList
		ObservableList<User> attendeeList = Guest.getGuestsByTransactionID(eventID);
		// tambahin vendor ke dalam attendeeList dengan memanggil method getVendorsByTransactionId dari Vendor class
		attendeeList.addAll(Vendor.getVendorsByTransactionID(eventID));
		// return attendeeList yang isinya semua guest dan vendor berdasarkan event id
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
		// panggil method getGuests dari Guest class
		return Guest.getGuests(event);
	}
	
	/*
	 * ASUMSI:
	 * 1. return type-nya menggunakan ObservableList karena di class diagram tidak di-specify
	 * 2. Di Class Diagram, Vendor class tidak punya method getVendors(), tapi di sequence ada, jadi ikuti sequence
	 * 3. tambah parameter event supaya bisa check apakah vendor sudah pernah diundang ke event tersebut
	 */
	public ObservableList<Vendor> getVendors(Event event) {
		// panggil method getVendors dari Vendor class
		return Vendor.getVendors(event);
	}
	
	public void getGuestsByTransactionID(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void getVendorsByTransactionID(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void checkCreateEventInput(String eventName, Date date, String location, String description) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	/*
	 * Di Class Diagram pakai method checkAddVendorInput(vendorID), tapi di Sequence Diagram pakai checkAddVendorInput(email)
	 * ASUMSI:
	 * 1. ikuti sequence diagram, gunakan checkAddVendorInput(email)
	 * 2. untuk validasi, asumsikan validasi email benar (diakhiri @gmail.com) + tidak kosong
	 * 3. return String karena mau return error message
	 */
	public String checkAddVendorInput(String email) {
		// validasi email tidak boleh kosong
		if(email.isBlank()) {
			return "Email is not found";
		}
		// validasi email harus diakhiri dengan @gmail.com
		else if(!email.endsWith("@gmail.com")) {
			return "Email is invalid";
		}
		// kalau semuanya berhasil dilewati, return success
		else {
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
		// validasi email tidak boleh kosong
		if(email.isBlank()) {
			return "Email is not found";
		}
		// validasi email harus diakhiri dengan @gmail.com
		else if(!email.endsWith("@gmail.com")) {
			return "Email is invalid";
		}
		// kalau semuanya berhasil dilewati, return success
		else {
			return "Success";
		}
	}
	
	public String editEventName(String eventID, String eventName) {
		// panggil method checkEditEventInput untuk validasi input dan tampung ke dalam variable String response
		String response = checkEditEventInput(eventName);
		// kalau response = success, panggil editEventName dari Event class
		if(response.equals("Success")) {
			return Event.editEventName(eventID, eventName);
		}
		return response;
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
		String response = null;
		// kalau target = Vendor, maka panggil method checkAddVendorInput dan tampung ke dalam variable string response
		if(target.equals("Vendor")) {
			response = checkAddVendorInput(email);
		}
		// kalau target = Guest, maka panggil method checkAddGuestInput dan tampung ke dalam variable string response
		else if(target.equals("Guest")) {
			response = checkAddGuestInput(email);
		}
		
		// kalau response = success, maka panggil method sendInvitation dari Invitation class
		if(response.equals("Success")) {
			return Invitation.sendInvitation(email, event);
		}
		return response;
	}
	
	// ditambahkan buat mendapatkan event organizer berdasarkan ID
	public EventOrganizer getOrganizerById(String organizerId) {
		// panggil method getOrganizerById dari EventOrganizer class
		return EventOrganizer.getOrganizerById(organizerId);
	}
	
	/*
	 * Di Class Diargam tidak checkEditEventInput(eventName) di class manapun, tapi diarahkan untuk mengikuti sequence diagram
	 * ASUMSI:
	 * 1. ikuti sequence diagram, tambahkan checkEditEventInput(eventName)
	 */
	public String checkEditEventInput(String eventName) {
		// validasi name tidak boleh kosong
		if(eventName.isBlank()) {
			return "Event name is required";
		}
		// kalau semuanya berhasil dilewati, return success
		else {
			return "Success";
		}
	}
}
