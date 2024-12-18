package views.eventOrganizer;

import controller.EventController;
import controller.EventOrganizerController;
import controller.ViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Event;
import models.User;

public class EditEventPage {
	ViewController viewController;
	EventOrganizerController eventOrganizerController;
	EventController eventController;
	User user;
	Event event;
	
	public EditEventPage(User user, String eventId) {
		this.viewController = ViewController.getInstance();
		this.eventOrganizerController = new EventOrganizerController();
		this.eventController =  new EventController();
		this.user = user;
		this.event = eventController.getEventById(eventId);
	}
	
	public Scene getUI() {
		VBox editEventContainer = new VBox();
		editEventContainer.setPadding(new Insets(15));
		
		Label titleLabel = new Label("Edit Event");
		titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		
		HBox namePane = new HBox();
		namePane.setAlignment(Pos.CENTER);
		Label nameLabel = new Label("Name");
		nameLabel.setPrefWidth(100);
		TextField nameField = new TextField();
		nameField.setText(event.getEvent_name());
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
		
		HBox errorPane = new HBox();
		errorPane.setAlignment(Pos.CENTER);
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);
		
		HBox buttonContainer = new HBox();
		buttonContainer.setAlignment(Pos.CENTER);
		buttonContainer.setSpacing(10);
		Button updateButton = new Button("Save Changes");
		Button backButton = new Button("Back");

		namePane.getChildren().addAll(nameLabel, nameField);
		datePane.getChildren().addAll(dateLabel, dateField);
		locationPane.getChildren().addAll(locationLabel, locationField);
		descriptionPane.getChildren().addAll(descriptionLabel, descriptionField);
		errorPane.getChildren().addAll(errorLabel);
		buttonContainer.getChildren().addAll(updateButton, backButton);
		
		editEventContainer.getChildren().addAll(titleLabel, namePane, datePane, locationPane, descriptionPane, errorPane, buttonContainer);
		editEventContainer.setSpacing(10);
		editEventContainer.setAlignment(Pos.CENTER);
		
		updateButton.setOnAction(e -> {
			String response = eventOrganizerController.editEventName(event.getEvent_id(), nameField.getText());
			if(response.equals("Success")) {
				viewController.pop(2);
				viewController.showViewEventDetailsPage(user, event.getEvent_id());
			}else {
				errorLabel.setText(response);
			}
		});
		
		backButton.setOnAction(e -> {
			viewController.back();
		});
		
		return new Scene(editEventContainer, 600, 500);
	}
}
