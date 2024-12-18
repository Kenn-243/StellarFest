package views.eventOrganizer;

import java.util.ArrayList;
import controller.EventController;
import controller.EventOrganizerController;
import controller.ViewController;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
	EventOrganizerController eventOrganizerController;
	EventController eventController;
	User user;
	Event event;
	String addType;
	
	public AddVendorGuestPage(User loggedInUser, String eventId, String addType) {
		this.viewController = ViewController.getInstance();
		this.eventOrganizerController = new EventOrganizerController();
		this.eventController = new EventController();
		this.user = loggedInUser;
		this.event = eventController.getEventById(eventId);
		this.addType = addType;
	}
	
	public Scene getUI() {
		VBox addContainer = new VBox();
		addContainer.setPadding(new Insets(15));
		
		Label titleLabel = new Label();
		titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		
		HBox buttonContainer = new HBox();
		buttonContainer.setAlignment(Pos.CENTER);
		buttonContainer.setSpacing(10);
		Button inviteButton = new Button();
		
		HBox errorPane = new HBox();
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);
		
		errorPane.getChildren().addAll(errorLabel);
		
		if(addType.equals("Vendor")) {
			addVendor(addContainer, titleLabel, inviteButton, errorPane, errorLabel, addType);			
		}else if(addType.equals("Guest")) {
			addGuest(addContainer, titleLabel, inviteButton, errorPane, errorLabel, addType);
		}
		
		Button backButton = new Button("Back");
		backButton.setOnAction(e -> {
			viewController.back();
		});
		
		buttonContainer.getChildren().addAll(inviteButton, backButton);
		
		addContainer.getChildren().addAll(buttonContainer);
		addContainer.setSpacing(10);
		addContainer.setAlignment(Pos.CENTER);
		
		return new Scene(addContainer, 600, 500);
	}

	private void addVendor(VBox addVendorContainer, Label titleLabel, Button inviteVendorsButton, HBox errorPane, Label errorLabel, String addType) {
		titleLabel.setText("Add Vendor");
		
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
		vendorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		vendorTable.setItems(vendorList);
		
		addVendorContainer.getChildren().addAll(titleLabel, vendorTable, errorPane);
		
		inviteVendorsButton.setOnAction(e -> {
			ArrayList<String> emails = new ArrayList<String>();
			for (Vendor vendor : vendorList) {
				if(vendor.getSelect().isSelected()) {
					emails.add(vendor.getUser_email());
				}
			}
			
			sendInvitation(emails, errorLabel, addType);
		});
	}
	
	private void addGuest(VBox addGuestContainer, Label titleLabel, Button inviteGuestsButton, HBox errorPane, Label errorLabel, String addType) {
		titleLabel.setText("Add Guest");
		
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
		guestTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		guestTable.setItems(guestList);
		
		addGuestContainer.getChildren().addAll(titleLabel, guestTable, errorPane);
		
		inviteGuestsButton.setOnAction(e -> {
			ArrayList<String> emails = new ArrayList<String>();
			for (Guest guest : guestList) {
				if(guest.getSelect().isSelected()) {
					emails.add(guest.getUser_email());
				}
			}
			
			sendInvitation(emails, errorLabel, addType);
		});
	}

	private void sendInvitation(ArrayList<String> emails, Label errorLabel, String target) {
		if(!emails.isEmpty()) {
			for (String email : emails) {
				String response = eventOrganizerController.sendInvitation(email, event, target);
				if(response.equals("Success")){
					viewController.showViewEventsPage(user);
				}
				else {
					errorLabel.setText(response);
				}
			}
		}
		else {
			errorLabel.setText("Please select a " + target.toLowerCase() + ".");
		}
	}
}
