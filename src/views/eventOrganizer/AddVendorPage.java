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
import models.User;
import models.Vendor;

public class AddVendorPage {
	ViewController viewController;
	EventOrganizerController eventOrganizerController = new EventOrganizerController();
	User user;
	Event event;
	
	public AddVendorPage(User loggedInUser, Event invitedEvent) {
		this.viewController = ViewController.getInstance();
		this.user = loggedInUser;
		this.event = invitedEvent;
	}
	
	public Scene getUI() {
		VBox addVendorContainer = new VBox();
		
		Button inviteVendorsButton = new Button("Invite Vendor(s)");
		
		HBox errorPane = new HBox();
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);

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
		
		errorPane.getChildren().addAll(errorLabel);
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
				errorLabel.setText("Please select a vendor.");
			}
		});
		
		return new Scene(addVendorContainer, 350, 250);
	}
}
