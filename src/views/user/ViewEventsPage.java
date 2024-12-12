package views.user;

import controller.AdminController;
import controller.EventOrganizerController;
import controller.ViewController;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Event;
import models.User;

public class ViewEventsPage {
	ViewController viewController;
	EventOrganizerController eventOrganizerController = new EventOrganizerController();
	AdminController adminController = new AdminController(); 
	User user;
	
	public ViewEventsPage(User loggedInUser) {
		this.viewController = ViewController.getInstance();
		this.user = loggedInUser;
	}
	
	public Scene getUI() {
		VBox viewEventsContainer = new VBox();
		
		if(user.getUser_role().equals("Event Organizer")) {
			viewOrganizedEvents(viewEventsContainer);
		}else if(user.getUser_role().equals("Admin")) {
			viewAllEvents(viewEventsContainer);
		}
		
		return new Scene (viewEventsContainer, 350, 250);
	}

	private void viewOrganizedEvents(VBox viewEventsContainer) {
		HBox buttonContainer = new HBox();
		
		Button createEventButton = new Button("Create Event");
		createEventButton.setOnAction(e -> {
			viewController.showCreateEventPage(user);
		});
		
		Button addVendorButton = new Button("Add Vendor");
		addVendorButton.setDisable(true);
		
		buttonContainer.getChildren().addAll(createEventButton, addVendorButton);
		
		ObservableList<Event> eventList =  eventOrganizerController.viewOrganizedEvents(user.getUser_id());

		TableView<Event> eventTable = new TableView<Event>();
		
		TableColumn<Event, String> eventId = new TableColumn<Event, String>("Id");
		eventId.setCellValueFactory(new PropertyValueFactory<Event, String>("event_id"));
		
		TableColumn<Event, String> eventName = new TableColumn<Event, String>("Name");
		eventName.setCellValueFactory(new PropertyValueFactory<Event, String>("event_name"));
		
		TableColumn<Event, String> eventDate = new TableColumn<Event, String>("Date");
		eventDate.setCellValueFactory(new PropertyValueFactory<Event, String>("event_date"));
		
		TableColumn<Event, String> eventLocation = new TableColumn<Event, String>("Location");
		eventLocation.setCellValueFactory(new PropertyValueFactory<Event, String>("event_location"));
		
		eventTable.getColumns().addAll(eventId, eventName, eventDate, eventLocation);
		eventTable.setItems(eventList);
		
		eventTable.setOnMouseClicked(e->{
			Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
			if(selectedEvent != null) {
				addVendorButton.setDisable(false);
				addVendorButton.setOnAction(f -> {
					viewController.showAddVendorPage(user, selectedEvent);
				});				
			}
		});
		
		viewEventsContainer.getChildren().addAll(buttonContainer, eventTable);
	}
	
	private void viewAllEvents(VBox viewEventsContainer) {
		ObservableList<Event> eventList =  adminController.viewAllEvents();

		TableView<Event> eventTable = new TableView<Event>();
		
		TableColumn<Event, String> eventId = new TableColumn<Event, String>("Id");
		eventId.setCellValueFactory(new PropertyValueFactory<Event, String>("event_id"));
		
		TableColumn<Event, String> eventName = new TableColumn<Event, String>("Name");
		eventName.setCellValueFactory(new PropertyValueFactory<Event, String>("event_name"));
		
		TableColumn<Event, String> eventDate = new TableColumn<Event, String>("Date");
		eventDate.setCellValueFactory(new PropertyValueFactory<Event, String>("event_date"));
		
		TableColumn<Event, String> eventLocation = new TableColumn<Event, String>("Location");
		eventLocation.setCellValueFactory(new PropertyValueFactory<Event, String>("event_location"));
		
		TableColumn<Event, String> organizerId = new TableColumn<Event, String>("Organizer");
		organizerId.setCellValueFactory(new PropertyValueFactory<Event, String>("organizer_id"));
		
		eventTable.getColumns().addAll(eventId, eventName, eventDate, eventLocation, organizerId);
		eventTable.setItems(eventList);
		
		viewEventsContainer.getChildren().addAll(eventTable);
	}
}
