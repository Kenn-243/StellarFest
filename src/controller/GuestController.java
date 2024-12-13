package controller;

import javafx.collections.ObservableList;
import models.Event;
import models.Invitation;
import models.User;

public class GuestController {
	// ASUMSI: parameter object user ditambahkan supaya bisa update invitation untuk event yang user pilih.
	public String acceptInvitation(String eventID, User user) {
		return Invitation.acceptInvitation(eventID, user);
	}
	// ASUMSI: return type-nya menggunakan ObservableList karena di class diagram tidak di-specify
	public ObservableList<Event> viewAcceptedEvents(String email) {
		return Event.viewAcceptedEvents(email);
	}
}
