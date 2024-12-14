package views.component;

import controller.ViewController;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import models.User;

public class Navbar extends BorderPane {
	ViewController viewController;
	User user;
	
	public Navbar(User loggedInUser) {
		this.viewController = ViewController.getInstance();
		this.user = loggedInUser;
	}
	
	public BorderPane getUI() {
		BorderPane menuPane = new BorderPane();
		
		MenuBar menuBar = new MenuBar();
		
		Menu menu = new Menu("Menu");
		
		MenuItem viewUsers = new MenuItem("View Users");
		viewUsers.setOnAction(e -> {
			viewController.showViewUsersPage(user);
		});
		
		MenuItem viewEvents = new MenuItem("View Events");
		viewEvents.setOnAction(e -> {
			viewController.showViewEventsPage(user);
		});
		
		MenuItem viewOrganizedEvents = new MenuItem("View Organized Events");
		viewOrganizedEvents.setOnAction(e -> {
			viewController.showViewEventsPage(user);
		});
		
		MenuItem viewInvitations = new MenuItem("View Invitations");
		viewInvitations.setOnAction(e -> {
			viewController.showViewInvitationPage(user);
		});
		
		MenuItem viewAcceptedEvents = new MenuItem("View Accepted Events");
		viewAcceptedEvents.setOnAction(e -> {
			viewController.showViewEventsPage(user);
		});
		
		MenuItem manageVendor = new MenuItem("Manage Vendor");
		manageVendor.setOnAction(e -> {
			viewController.showManageVendorPage(user);
		});
		
		MenuItem changeProfile = new MenuItem("Change Profile");
		changeProfile.setOnAction(e -> {
			viewController.showChangeProfilePage(user);
		});
		
		MenuItem logout = new MenuItem("Logout");
		logout.setOnAction(e -> {
			viewController.logout();
		});
		
		if(user.getUser_role().equals("Admin")) {
			menu.getItems().addAll(viewUsers, viewEvents);
		}else if(user.getUser_role().equals("Event Organizer")) {
			menu.getItems().addAll(viewOrganizedEvents);
		}else if(user.getUser_role().equals("Vendor")) {
			menu.getItems().addAll(viewInvitations, viewAcceptedEvents, manageVendor);
		}else if(user.getUser_role().equals("Guest")) {
			menu.getItems().addAll(viewInvitations, viewAcceptedEvents);
		}
		
		menu.getItems().addAll(changeProfile, logout);
		
		menuBar.getMenus().add(menu);
		
		menuPane.setTop(menuBar);
		
		return menuPane;
	}
}
