package views.user;

import controller.AdminController;
import controller.EventController;
import controller.EventOrganizerController;
import controller.GuestController;
import controller.VendorController;
import controller.ViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Event;
import models.EventOrganizer;
import models.User;
import views.component.Navbar;

public class ViewEventsPage {
	ViewController viewController;
	EventOrganizerController eventOrganizerController;
	AdminController adminController;
	VendorController vendorController;
	GuestController guestController;
	EventController eventController;
	User user;
	
	public ViewEventsPage(User loggedInUser) {
		this.viewController = ViewController.getInstance();
		this.eventOrganizerController = new EventOrganizerController();
		this.adminController = new AdminController(); 
		this.vendorController = new VendorController();
		this.guestController = new GuestController();
		this.eventController = new EventController();
		this.user = loggedInUser;
	}
	
	public Scene getUI() {
		VBox viewEventsContainer = new VBox();
		viewEventsContainer.setPadding(new Insets(15));
		
		BorderPane navbar = new Navbar(user).getUI();
		
		Label titleLabel = new Label();
		titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		
		HBox buttonContainer = new HBox();
		buttonContainer.setSpacing(10);
		Button viewEventDetailsButton = new Button("View Event Details");
		viewEventDetailsButton.setDisable(true);
		
		ObservableList<Event> eventList = null;
		
		TableView<Event> eventTable = new TableView<Event>();
		
		viewEventsContainer.getChildren().addAll(navbar, titleLabel);
		
		if(user.getUser_role().equals("Event Organizer")) {
			titleLabel.setText("Organized Events");
			viewOrganizedEvents(viewEventsContainer, eventList, eventTable, buttonContainer, viewEventDetailsButton);
		}else if(user.getUser_role().equals("Admin")) {
			titleLabel.setText("Events");
			viewAllEvents(viewEventsContainer, eventList, eventTable, buttonContainer, viewEventDetailsButton);
		}else if(user.getUser_role().equals("Vendor") || user.getUser_role().equals("Guest")) {
			titleLabel.setText("Accepted Events");
			viewAcceptedEvents(viewEventsContainer, eventList, eventTable, buttonContainer, viewEventDetailsButton);
		}
		
		viewEventsContainer.setSpacing(10);
		viewEventsContainer.setAlignment(Pos.CENTER);
		
		return new Scene (viewEventsContainer, 600, 300);
	}

	private void viewOrganizedEvents(VBox viewEventsContainer, ObservableList<Event> eventList, TableView<Event> eventTable, HBox buttonContainer, Button viewEventDetailsButton) {
		Button createEventButton = new Button("Create Event");
		createEventButton.setOnAction(e -> {
			viewController.showCreateEventPage(user);
		});
		
		Button addVendorButton = new Button("Add Vendor");
		addVendorButton.setDisable(true);
		
		Button addGuestButton = new Button("Add Guest");
		addGuestButton.setDisable(true);
		
		buttonContainer.getChildren().addAll(createEventButton, viewEventDetailsButton, addVendorButton, addGuestButton);
		buttonContainer.setSpacing(10);
		
		eventList =  eventOrganizerController.viewOrganizedEvents(user.getUser_id());
				
		setupEventTable(eventTable, eventList, false);
		
		viewEventsContainer.getChildren().addAll(buttonContainer, eventTable);
		
		eventTable.setOnMouseClicked(e->{
			Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
			if(selectedEvent != null) {
				viewEventDetailsButton.setDisable(false);
				viewEventDetailsButton.setOnAction(f -> {
					viewController.showViewEventDetailsPage(user, selectedEvent.getEvent_id());
				});
				addVendorButton.setDisable(false);
				addVendorButton.setOnAction(f -> {
					viewController.showAddVendorGuestPage(user, selectedEvent.getEvent_id(), "Vendor");
				});
				addGuestButton.setDisable(false);
				addGuestButton.setOnAction(f -> {
					viewController.showAddVendorGuestPage(user, selectedEvent.getEvent_id(), "Guest");
				});
			}
		});
	}
	
	private void viewAllEvents(VBox viewEventsContainer, ObservableList<Event> eventList, TableView<Event> eventTable, HBox buttonContainer, Button viewEventDetailsButton) {
		Button deleteEventButton = new Button("Delete Event");
		deleteEventButton.setDisable(true);
		
		buttonContainer.getChildren().addAll(viewEventDetailsButton, deleteEventButton);
		
		eventList = adminController.viewAllEvents();
		
		setupEventTable(eventTable, eventList, true);
		
		viewEventsContainer.getChildren().addAll(buttonContainer, eventTable);
		
		eventTable.setOnMouseClicked(e->{
			Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
			if(selectedEvent != null) {
				viewEventDetailsButton.setDisable(false);
				viewEventDetailsButton.setOnAction(f -> {
					viewController.showViewEventDetailsPage(user, selectedEvent.getEvent_id());
				});
				deleteEventButton.setDisable(false);
				deleteEventButton.setOnAction(f -> {
					adminController.deleteEvent(selectedEvent.getEvent_id());
					eventTable.getColumns().clear();
					setupEventTable(eventTable, adminController.viewAllEvents(), true);
				});
			}
		});
	}
	
	private void viewAcceptedEvents(VBox viewEventsContainer, ObservableList<Event> acceptedEventList, TableView<Event> eventTable, HBox buttonContainer, Button viewEventDetailsButton) {
		buttonContainer.getChildren().addAll(viewEventDetailsButton);

		if(user.getUser_role().equals("Vendor")) {
			acceptedEventList = vendorController.viewAcceptedEvents(user.getUser_email());
		}else if(user.getUser_role().equals("Guest")) {
			acceptedEventList = guestController.viewAcceptedEvents(user.getUser_email());
		}
		
		setupEventTable(eventTable, acceptedEventList, false);
		
		viewEventsContainer.getChildren().addAll(buttonContainer, eventTable);
		
		eventTable.setOnMouseClicked(e->{
			Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
			if(selectedEvent != null) {
				viewEventDetailsButton.setDisable(false);
				viewEventDetailsButton.setOnAction(f -> {
					viewController.showViewEventDetailsPage(user, selectedEvent.getEvent_id());
				});
			}
		});
	}
	
	private void setupEventTable(TableView<Event> eventTable, ObservableList<Event> eventList, boolean includeOrganizer) {
		TableColumn<Event, String> eventId = new TableColumn<>("Id");
	    eventId.setCellValueFactory(new PropertyValueFactory<>("event_id"));
		
		TableColumn<Event, String> eventName = new TableColumn<>("Name");
	    eventName.setCellValueFactory(new PropertyValueFactory<>("event_name"));

	    TableColumn<Event, String> eventDate = new TableColumn<>("Date");
	    eventDate.setCellValueFactory(new PropertyValueFactory<>("event_date"));

	    TableColumn<Event, String> eventLocation = new TableColumn<>("Location");
	    eventLocation.setCellValueFactory(new PropertyValueFactory<>("event_location"));

	    eventTable.getColumns().addAll(eventId, eventName, eventDate, eventLocation);
	    eventTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    

	    if (includeOrganizer) {
	        TableColumn<Event, String> eventOrganizer = new TableColumn<>("Organizer");
	        eventOrganizer.setCellValueFactory(e -> {
	        	String organizer_id = e.getValue().getOrganizer_id();
	        	EventOrganizer organizer = eventOrganizerController.getOrganizerById(organizer_id);
	        	return new SimpleStringProperty(organizer.getUser_name());
	        });
	        eventTable.getColumns().add(eventOrganizer);
	    }

	    eventTable.setItems(eventList);
	}
}
