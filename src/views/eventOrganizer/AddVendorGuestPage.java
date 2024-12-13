package views.eventOrganizer;

import java.util.ArrayList;
import controller.EventOrganizerController;
import controller.ViewController;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Event;
import models.Guest;
import models.User;
import models.Vendor;

public class AddVendorGuestPage {
	ViewController viewController;
	EventOrganizerController eventOrganizerController = new EventOrganizerController();
	User user;
	Event event;
	String addType;
	
	public AddVendorGuestPage(User loggedInUser, Event invitedEvent, String addType) {
		this.viewController = ViewController.getInstance();
		this.user = loggedInUser;
		this.event = invitedEvent;
		this.addType = addType;
	}
	
	public Scene getUI() {
		VBox addContainer = new VBox();
		Button inviteButton = new Button();
		
		HBox errorPane = new HBox();
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);
		
		errorPane.getChildren().addAll(errorLabel);
		
		if(addType.equals("Vendor")) {
			addVendor(addContainer, inviteButton, errorPane, errorLabel);			
		}else if(addType.equals("Guest")) {
			addGuest(addContainer, inviteButton, errorPane, errorLabel);
		}
		
		return new Scene(addContainer, 350, 250);
	}

	private void addVendor(VBox addVendorContainer, Button inviteVendorsButton, HBox errorPane, Label errorLabel) {
		inviteVendorsButton.setText("Invite Vendor(s)");

		ObservableList<Vendor> vendorList = eventOrganizerController.getVendors(event);
		
		TableView<Vendor> vendorTable = new TableView<>();
		
		TableColumn<Vendor, String> vendorId = new TableColumn<>("Id");
		vendorId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
		
		TableColumn<Vendor, String> vendorEmail = new TableColumn<>("Email");
		vendorEmail.setCellValueFactory(new PropertyValueFactory<>("user_email"));
		
		TableColumn<Vendor, String> vendorName = new TableColumn<>("Name");
		vendorName.setCellValueFactory(new PropertyValueFactory<>("user_name"));
		
		TableColumn<Vendor, Boolean> select = new TableColumn<>("Select");
		select.setCellValueFactory(new PropertyValueFactory<>("select"));
		
		vendorTable.getColumns().addAll(vendorId, vendorEmail, vendorName, select);
		vendorTable.setItems(vendorList);
		
		addVendorContainer.getChildren().addAll(inviteVendorsButton, errorPane, vendorTable);
		
		inviteVendorsButton.setOnAction(e -> {
			ArrayList<String> emails = new ArrayList<String>();
			for (Vendor vendor : vendorList) {
				if(vendor.getSelect().isSelected()) {
					emails.add(vendor.getUser_email());
				}
			}
			
			sendInvitation(emails, errorLabel);
		});
	}
	
	private void addGuest(VBox addGuestContainer, Button inviteGuestsButton, HBox errorPane, Label errorLabel) {
		inviteGuestsButton.setText("Invite Guest(s)");

		ObservableList<Guest> guestList = eventOrganizerController.getGuests(event);
		
		TableView<Guest> guestTable = new TableView<>();
		
		TableColumn<Guest, String> guestId = new TableColumn<>("Id");
		guestId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
		
		TableColumn<Guest, String> guestEmail = new TableColumn<>("Email");
		guestEmail.setCellValueFactory(new PropertyValueFactory<>("user_email"));
		
		TableColumn<Guest, String> guestName = new TableColumn<>("Name");
		guestName.setCellValueFactory(new PropertyValueFactory<>("user_name"));
		
		TableColumn<Guest, Boolean> select = new TableColumn<>("Select");
		select.setCellValueFactory(new PropertyValueFactory<>("select"));
		
		guestTable.getColumns().addAll(guestId, guestEmail, guestName, select);
		guestTable.setItems(guestList);
		
		addGuestContainer.getChildren().addAll(inviteGuestsButton, errorPane, guestTable);
		
		inviteGuestsButton.setOnAction(e -> {
			ArrayList<String> emails = new ArrayList<String>();
			for (Guest guest : guestList) {
				if(guest.getSelect().isSelected()) {
					emails.add(guest.getUser_email());
				}
			}
			
			sendInvitation(emails, errorLabel);
		});
	}

	private void sendInvitation(ArrayList<String> emails, Label errorLabel) {
		if(!emails.isEmpty()) {
			for (String email : emails) {
				String response = eventOrganizerController.sendInvitation(email, event);
				if(response.equals("Success")){
					viewController.showViewEventsPage(user);
				}
				else {
					errorLabel.setText(response);
				}
			}
		}
		else {
			errorLabel.setText("Please select a guest.");
		}
	}
}
