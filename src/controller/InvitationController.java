package controller;

import javafx.collections.ObservableList;
import models.Invitation;

public class InvitationController {
	public void sendInvitation(String email) {
		/*
		 * Di Class Diagram: tidak ada sendInvitation(email) di class ini, tapi di Sequence Diagram ada
		 * Di Class Diargam, sendInvitation(email) adanya di InvitationController, tapi diarahkan untuk mengikuti sequence diagram
		 * ASUMSI:
		 * 1. ikuti sequence diagram, tambahkan sendInvitation(String email) (diarahkan untuk mengikuti sequence diagram)
		 * 2. method sendInvitation(email) di InvitationController class akan dikosongkan.
		 */
		
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public void acceptInvitation(String eventID) {
		// dikosongkan karena berdasarkan sequence diagram tidak digunakan atau dipanggil sama sekali
	}
	
	public ObservableList<Invitation> getInvitations(String email) {
		// panggil method getInvitations dari Invitation class
		return Invitation.getInvitations(email);
	}
}
