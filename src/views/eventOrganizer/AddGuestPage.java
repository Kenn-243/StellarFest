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

public class AddGuestPage {
	ViewController viewController;
	EventOrganizerController eventOrganizerController = new EventOrganizerController();
	User user;
	Event event;
	
	public AddGuestPage(User loggedInUser, Event invitedEvent) {
		this.viewController = ViewController.getInstance();
		this.user = loggedInUser;
		this.event = invitedEvent;
	}
	
	public Scene getUI() {
		VBox addGuestContainer = new VBox();
		
		Button inviteGuestsButton = new Button("Invite Guest(s)");
		
		HBox errorPane = new HBox();
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);

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
		
		errorPane.getChildren().addAll(errorLabel);
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
		});
		
		return new Scene(addGuestContainer, 350, 250);
	}
}
