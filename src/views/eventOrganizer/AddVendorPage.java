package views.eventOrganizer;

import java.util.HashMap;
import java.util.Map;

import controller.EventOrganizerController;
import controller.ViewController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import models.User;
import models.Vendor;

public class AddVendorPage {
	ViewController viewController;
	EventOrganizerController eventOrganizerController = new EventOrganizerController();
	User user;
	
	public AddVendorPage(User loggedInUser) {
		this.viewController = ViewController.getInstance();
		this.user = loggedInUser;
	}
	
	public Scene getUI() {
		VBox addVendorContainer = new VBox();
		
		Button inviteVendors = new Button("Invite Vendor(s)");
		inviteVendors.setOnAction(e -> {
			// kirim email
		});

		ObservableList<Vendor> vendorList = eventOrganizerController.getVendors();
		
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
		vendorTable.setItems(vendorList);
		
		addVendorContainer.getChildren().addAll(vendorTable);
		
		return new Scene(addVendorContainer, 350, 250);
	}
}
