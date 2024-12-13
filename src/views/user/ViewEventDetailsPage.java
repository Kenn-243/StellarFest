package views.user;

import controller.AdminController;
import controller.EventController;
import controller.EventOrganizerController;
import controller.GuestController;
import controller.VendorController;
import controller.ViewController;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Event;
import models.User;

public class ViewEventDetailsPage {
	ViewController viewController;
	EventOrganizerController eventOrganizerController;
	AdminController adminController;
	VendorController vendorController;
	GuestController guestController;
	EventController eventController;
	User user;
	Event event;
	
	public ViewEventDetailsPage(User loggedInUser, Event selectedEvent) {
		this.viewController = ViewController.getInstance();
		this.eventOrganizerController = new EventOrganizerController();
		this.adminController = new AdminController(); 
		this.vendorController = new VendorController();
		this.guestController = new GuestController();
		this.eventController = new EventController();
		this.user = loggedInUser;
		this.event = selectedEvent;
	}
	
	public Scene getUI() {
		VBox viewEventDetailsContainer = new VBox();
		
		HBox namePane = new HBox();
		Label nameLabel = new Label("Event Name");
		nameLabel.setPrefWidth(100);
		TextArea nameField = new TextArea();
		nameField.setText(event.getEvent_name());
		nameField.setEditable(false);
		nameField.setWrapText(false);
		nameField.setPrefRowCount(1);
		
		HBox datePane = new HBox();
		Label dateLabel = new Label("Event Date");
		dateLabel.setPrefWidth(100);
		TextArea dateField = new TextArea();
		dateField.setText(event.getEvent_date());
		dateField.setEditable(false);
		dateField.setWrapText(false);
		dateField.setPrefRowCount(1);
		
		HBox locationPane = new HBox();
		Label locationLabel = new Label("Location");
		locationLabel.setPrefWidth(100);
		TextArea locationField = new TextArea();
		locationField.setText(event.getEvent_location());
		locationField.setEditable(false);
		locationField.setWrapText(false);
		locationField.setPrefRowCount(1);
		
		HBox descriptionPane = new HBox();
		Label descriptionLabel = new Label("Description");
		descriptionLabel.setPrefWidth(100);
		TextArea descriptionField = new TextArea();
		descriptionField.setText(event.getEvent_description());
		descriptionField.setEditable(false);
		descriptionField.setWrapText(false);
		descriptionField.setPrefRowCount(1);
		
		Button backButton = new Button("Back");
		backButton.setOnAction(e -> {
			viewController.back(user);
		});
		
		namePane.getChildren().addAll(nameLabel, nameField);
		datePane.getChildren().addAll(dateLabel, dateField);
		locationPane.getChildren().addAll(locationLabel, locationField);
		descriptionPane.getChildren().addAll(descriptionLabel, descriptionField);
		
		viewEventDetailsContainer.getChildren().addAll(namePane, datePane, locationPane, descriptionPane);
		if(user.getUser_role().equals("Event Organizer")) {
			ObservableList<User> attendeeList = eventOrganizerController.viewOrganizedEventDetails(event.getEvent_id());
			
			TableView<User> attendeeTable = new TableView<User>();
			
			TableColumn<User, String> attendeeName = new TableColumn<>("Name");
			attendeeName.setCellValueFactory(new PropertyValueFactory<>("user_name"));
			
			TableColumn<User, String> attendeeEmail = new TableColumn<>("Email");
			attendeeEmail.setCellValueFactory(new PropertyValueFactory<>("user_email"));
			
			TableColumn<User, String> attendeeRole = new TableColumn<>("Role");
			attendeeRole.setCellValueFactory(new PropertyValueFactory<>("user_role"));
			
			attendeeTable.getColumns().addAll(attendeeName, attendeeEmail, attendeeRole);
			attendeeTable.setItems(attendeeList);
			
			viewEventDetailsContainer.getChildren().addAll(attendeeTable);
		}
		viewEventDetailsContainer.getChildren().addAll(backButton);
		viewEventDetailsContainer.setSpacing(10);
		
		return new Scene(viewEventDetailsContainer, 600, 500);
	}
}
