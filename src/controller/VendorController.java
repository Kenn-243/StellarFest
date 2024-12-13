package controller;

import models.Invitation;
import models.User;

public class VendorController {
	// ASUMSI: parameter object user ditambahkan supaya bisa update invitation untuk event yang user pilih.
	public String acceptInvitation(String eventID, User user) {
		return Invitation.acceptInvitation(eventID, user);
	}
	
	public void viewAcceptedEvents(String email) {
		
	}
	
	public void manageVendor(String description, String  product) {
		
	}
	
	public void checkManageVendorInput(String description, String product) {
		
	}
}
