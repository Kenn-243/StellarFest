package views.eventOrganizer;

import java.sql.Date;
import java.time.LocalDate;

import controller.EventController;
import controller.ViewController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.User;

public class CreateEventPage {
	ViewController viewController;
	EventController eventController = new EventController();
	User eventOrganizer;
	
	public CreateEventPage(User eventOrganizer) {
		this.viewController = ViewController.getInstance();
		this.eventOrganizer = eventOrganizer;
	}
	
	public Scene getUI() {
		VBox createEventContainer = new VBox();
		
		HBox namePane = new HBox();
		Label nameLabel = new Label("Name");
		nameLabel.setPrefWidth(100);
		TextField nameField = new TextField();
		
		HBox datePane = new HBox();
		Label dateLabel = new Label("Date");
		dateLabel.setPrefWidth(100);
		DatePicker datePicker = new DatePicker();
		
		HBox locationPane = new HBox();
		Label locationLabel = new Label("Location");
		locationLabel.setPrefWidth(100);
		TextField locationField = new TextField();
		
		HBox descriptionPane = new HBox();
		Label descriptionLabel = new Label("Description");
		descriptionLabel.setPrefWidth(100);
		TextField descriptionField = new TextField();
		
		HBox errorPane = new HBox();
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);
		
		Button createEventButton = new Button("Create Event");
		
		namePane.getChildren().addAll(nameLabel, nameField);
		datePane.getChildren().addAll(dateLabel, datePicker);
		locationPane.getChildren().addAll(locationLabel, locationField);
		descriptionPane.getChildren().addAll(descriptionLabel, descriptionField);
		errorPane.getChildren().addAll(errorLabel);
		
		createEventContainer.getChildren().addAll(namePane, datePane, locationPane, descriptionPane, errorPane, createEventButton);
		createEventContainer.setSpacing(10);
		
		createEventButton.setOnAction(e -> {
			LocalDate selectedDate = datePicker.getValue();
			Date date = (selectedDate != null) ? Date.valueOf(selectedDate) : null;
			String response = eventController.createEvent(nameField.getText(), date , locationField.getText(), descriptionField.getText(), eventOrganizer.getUser_id());
			if(response.equals("Success")) {
				viewController.showViewEventsPage(eventOrganizer);
			}else {
				errorLabel.setText(response);
			}
		});
		
		return new Scene(createEventContainer, 350, 250);
	}
}
