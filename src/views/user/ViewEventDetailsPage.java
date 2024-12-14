package views.user;

import controller.AdminController;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Event;
import models.User;

public class ViewEventDetailsPage {
	ViewController viewController;
	EventController eventController;
	EventOrganizerController eventOrganizerController;
	AdminController adminController;
	User user;
	Event event;
	
	public ViewEventDetailsPage(User loggedInUser, String eventId) {
		this.viewController = ViewController.getInstance();
		this.eventController = new EventController();
		this.eventOrganizerController = new EventOrganizerController();
		this.adminController = new AdminController();
		this.user = loggedInUser;
		this.event = eventController.getEventById(eventId);
	}
	
	public Scene getUI() {
		VBox viewEventDetailsContainer = new VBox();
		viewEventDetailsContainer.setPadding(new Insets(0, 15, 15, 15));
		
		Label titleLabel = new Label("Event Details");
		titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		
		HBox buttonContainer = new HBox();
		buttonContainer.setAlignment(Pos.CENTER);
		if(user.getUser_role().equals("Event Organizer")) {
			Button editEventButton = new Button("Edit Event");
			buttonContainer.getChildren().addAll(editEventButton);
			editEventButton.setOnAction(e -> {
				viewController.showEditEventPage(user, event.getEvent_id());
			});
		}
		
		HBox namePane = new HBox();
		namePane.setAlignment(Pos.CENTER);
		Label nameLabel = new Label("Name");
		nameLabel.setPrefWidth(100);
		TextField nameField = new TextField();
		nameField.setText(event.getEvent_name());
		nameField.setEditable(false);
		nameField.setPrefWidth(400);
		
		HBox datePane = new HBox();
		datePane.setAlignment(Pos.CENTER);
		Label dateLabel = new Label("Date");
		dateLabel.setPrefWidth(100);
		TextField dateField = new TextField();
		dateField.setText(event.getEvent_date());
		dateField.setEditable(false);
		dateField.setPrefWidth(400);
		
		HBox locationPane = new HBox();
		locationPane.setAlignment(Pos.CENTER);
		Label locationLabel = new Label("Location");
		locationLabel.setPrefWidth(100);
		TextField locationField = new TextField();
		locationField.setText(event.getEvent_location());
		locationField.setEditable(false);
		locationField.setPrefWidth(400);
		
		HBox descriptionPane = new HBox();
		descriptionPane.setAlignment(Pos.CENTER);
		Label descriptionLabel = new Label("Description");
		descriptionLabel.setPrefWidth(100);
		TextArea descriptionField = new TextArea();
		descriptionField.setText(event.getEvent_description());
		descriptionField.setEditable(false);
		descriptionField.setWrapText(false);
		descriptionField.setPrefWidth(400);
		descriptionField.setPrefRowCount(1);
		
		Button backButton = new Button("Back");
		backButton.setOnAction(e -> {
			viewController.back();
		});
		
		namePane.getChildren().addAll(nameLabel, nameField);
		datePane.getChildren().addAll(dateLabel, dateField);
		locationPane.getChildren().addAll(locationLabel, locationField);
		descriptionPane.getChildren().addAll(descriptionLabel, descriptionField);
		
		viewEventDetailsContainer.getChildren().addAll(titleLabel, buttonContainer, namePane, datePane, locationPane, descriptionPane);
		if(user.getUser_role().equals("Event Organizer") || user.getUser_role().equals("Admin")) {
			ObservableList<User> attendeeList = null;
			if(user.getUser_role().equals("Event Organizer")) {
				attendeeList = eventOrganizerController.viewOrganizedEventDetails(event.getEvent_id());				
			}else if(user.getUser_role().equals("Admin")) {
				attendeeList = adminController.viewEventDetails(event.getEvent_id());
			}
			
			TableView<User> attendeeTable = new TableView<User>();
			
			TableColumn<User, String> attendeeName = new TableColumn<>("Name");
			attendeeName.setCellValueFactory(new PropertyValueFactory<>("user_name"));
			
			TableColumn<User, String> attendeeEmail = new TableColumn<>("Email");
			attendeeEmail.setCellValueFactory(new PropertyValueFactory<>("user_email"));
			
			TableColumn<User, String> attendeeRole = new TableColumn<>("Role");
			attendeeRole.setCellValueFactory(new PropertyValueFactory<>("user_role"));
			
			attendeeTable.getColumns().addAll(attendeeName, attendeeEmail, attendeeRole);
			attendeeTable.setItems(attendeeList);
			attendeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
			viewEventDetailsContainer.getChildren().addAll(attendeeTable);
		}
		viewEventDetailsContainer.getChildren().addAll(backButton);
		viewEventDetailsContainer.setSpacing(10);
		viewEventDetailsContainer.setAlignment(Pos.CENTER);
		
		return new Scene(viewEventDetailsContainer, 600, 500);
	}
}
