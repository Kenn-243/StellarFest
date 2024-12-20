package views.eventOrganizer;

import java.sql.Date;
import java.time.LocalDate;

import controller.EventController;
import controller.ViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
		createEventContainer.setPadding(new Insets(15));
		
		Label titleLabel = new Label("Create Event");
		titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		
		HBox namePane = new HBox();
		namePane.setAlignment(Pos.CENTER);
		Label nameLabel = new Label("Name");
		nameLabel.setPrefWidth(100);
		TextField nameField = new TextField();
		nameField.setPrefWidth(300);
		
		HBox datePane = new HBox();
		datePane.setAlignment(Pos.CENTER);
		Label dateLabel = new Label("Date");
		dateLabel.setPrefWidth(100);
		DatePicker datePicker = new DatePicker();
		datePicker.setPrefWidth(300);
		
		HBox locationPane = new HBox();
		locationPane.setAlignment(Pos.CENTER);
		Label locationLabel = new Label("Location");
		locationLabel.setPrefWidth(100);
		TextField locationField = new TextField();
		locationField.setPrefWidth(300);
		
		HBox descriptionPane = new HBox();
		descriptionPane.setAlignment(Pos.CENTER);
		Label descriptionLabel = new Label("Description");
		descriptionLabel.setPrefWidth(100);
		TextArea descriptionField = new TextArea();
		descriptionField.setWrapText(false);
		descriptionField.setPrefWidth(300);
		descriptionField.setPrefRowCount(1);
		
		HBox errorPane = new HBox();
		errorPane.setAlignment(Pos.CENTER);
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);
		
		HBox buttonContainer = new HBox();
		buttonContainer.setSpacing(10);
		buttonContainer.setAlignment(Pos.CENTER);
		Button createEventButton = new Button("Create Event");
		Button backButton = new Button("Back");
		
		namePane.getChildren().addAll(nameLabel, nameField);
		datePane.getChildren().addAll(dateLabel, datePicker);
		locationPane.getChildren().addAll(locationLabel, locationField);
		descriptionPane.getChildren().addAll(descriptionLabel, descriptionField);
		errorPane.getChildren().addAll(errorLabel);
		buttonContainer.getChildren().addAll(createEventButton, backButton);
		
		createEventContainer.getChildren().addAll(titleLabel, namePane, datePane, locationPane, descriptionPane, errorPane, buttonContainer);
		createEventContainer.setSpacing(10);
		createEventContainer.setAlignment(Pos.CENTER);
		
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
		
		backButton.setOnAction(e -> {
			viewController.back();
		});
		
		return new Scene(createEventContainer, 600, 300);
	}
}
