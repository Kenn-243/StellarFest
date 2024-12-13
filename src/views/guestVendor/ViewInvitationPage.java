package views.guestVendor;

import java.util.ArrayList;

import controller.EventController;
import controller.InvitationController;
import controller.VendorController;
import controller.ViewController;
import javafx.beans.property.SimpleStringProperty;
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
import models.Invitation;
import models.User;

public class ViewInvitationPage {
	ViewController viewController;
	InvitationController invitationController = new InvitationController();
	VendorController vendorController = new VendorController();
	EventController eventController = new EventController();
	User user;
	
	public ViewInvitationPage(User user) {
		this.viewController = ViewController.getInstance();
		this.user = user;
	}
	
	public Scene getUI() {
		VBox viewInvitationsContainer = new VBox();
		
		Button acceptInvitationsButton = new Button("Accept Invitation(s)");
		
		HBox errorPane = new HBox();
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);

		ObservableList<Invitation> invitationList = invitationController.getInvitations(user.getUser_email());
		
		TableView<Invitation> invitationTable = new TableView<>();
		
		TableColumn<Invitation, String> invitationId = new TableColumn<>("Id");
		invitationId.setCellValueFactory(new PropertyValueFactory<>("invitation_id"));
		
		TableColumn<Invitation, String> eventName = new TableColumn<>("Event");
		eventName.setCellValueFactory(e -> {
			String eventId = e.getValue().getEvent_id();
			Event event = eventController.getEventById(eventId);
			return new SimpleStringProperty(event.getEvent_name());
		});
		
		TableColumn<Invitation, String> invitationStatus = new TableColumn<>("Status");
		invitationStatus.setCellValueFactory(new PropertyValueFactory<>("invitation_status"));
		
		TableColumn<Invitation, String> invitationRole = new TableColumn<>("Role");
		invitationRole.setCellValueFactory(new PropertyValueFactory<>("invitation_role"));
		
		TableColumn<Invitation, Boolean> select = new TableColumn<>("Select");
		select.setCellValueFactory(new PropertyValueFactory<>("select"));
		
		errorPane.getChildren().addAll(errorLabel);
		invitationTable.getColumns().addAll(invitationId, eventName, invitationStatus, invitationRole, select);
		invitationTable.setItems(invitationList);
		
		viewInvitationsContainer.getChildren().addAll(acceptInvitationsButton, errorPane, invitationTable);
		
		acceptInvitationsButton.setOnAction(e -> {
			ArrayList<String> eventIds = new ArrayList<String>();
			for (Invitation invitation : invitationList) {
				if(invitation.getSelect().isSelected()) {
					eventIds.add(invitation.getEvent_id());
				}
			}
			
			if(!eventIds.isEmpty()) {
				for (String eventId : eventIds) {
					String response = vendorController.acceptInvitation(eventId, user);
					if(response.equals("Success")){
						// ke View Accepted Invitations
						viewController.showHomePage(user);
					}
					else {
						errorLabel.setText(response);
					}
				}
			}
			else {
				errorLabel.setText("Please select an invitation or more.");
			}
		});
		
		return new Scene(viewInvitationsContainer, 350, 250);
	}
}
