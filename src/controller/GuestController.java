package controller;

import javafx.collections.ObservableList;
import models.Event;
import models.Invitation;
import models.User;

public class GuestController {
	// ASUMSI: parameter object user ditambahkan supaya bisa update invitation untuk event yang user pilih.
	public String acceptInvitation(String eventID, User user) {
		// panggil method acceptInvitation dari Invitation class
		return Invitation.acceptInvitation(eventID, user);
	}
	// ASUMSI: return type-nya menggunakan ObservableList karena di class diagram tidak di-specify
	public ObservableList<Event> viewAcceptedEvents(String email) {
		// panggil method viewAcceptedEvents dari Event class
		return Event.viewAcceptedEvents(email);
	}
}
