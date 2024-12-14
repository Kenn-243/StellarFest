package controller;

import java.util.Stack;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;
import views.admin.ViewUsersPage;
import views.eventOrganizer.AddVendorGuestPage;
import views.eventOrganizer.CreateEventPage;
import views.eventOrganizer.EditEventPage;
import views.guestVendor.ViewInvitationPage;
import views.user.ChangeProfilePage;
import views.user.HomePage;
import views.user.LoginPage;
import views.user.RegisterPage;
import views.user.ViewEventDetailsPage;
import views.user.ViewEventsPage;
import views.vendor.ManageVendorPage;

public class ViewController {
	private Stack<Scene> sceneStack;
	private Stage primaryStage;
	
	private static ViewController instance;
	
	public static ViewController getInstance() {
		if(instance == null) instance = new ViewController();
		return instance;
	}
	
	private ViewController() {
		sceneStack = new Stack<Scene>();
	}
	
	public void setStage(Stage stage) {
		this.primaryStage = stage;
	}
	
	public void back() {
		sceneStack.pop();
		Scene prevScene = sceneStack.peek();
		primaryStage.setScene(prevScene);
	}
	
	public void showLoginPage() {
		Scene loginPage = new LoginPage().getUI();
		sceneStack.push(loginPage);
		primaryStage.setScene(loginPage);
    }
	
	public void showRegisterPage() {
		Scene registerScene = new RegisterPage().getUI();
		sceneStack.push(registerScene);
        primaryStage.setScene(registerScene);
    }
	
	public void showHomePage(User user) {
		Scene homePage = new HomePage(user).getUI();
		sceneStack.push(homePage);
		primaryStage.setScene(homePage);
	}
	
	public void showCreateEventPage(User user) {
		Scene createEventPage = new CreateEventPage(user).getUI();
		sceneStack.push(createEventPage);
		primaryStage.setScene(createEventPage);
	}
	
	public void showViewEventsPage(User user) {
		Scene viewEventsPage = new ViewEventsPage(user).getUI();
		sceneStack.push(viewEventsPage);
		primaryStage.setScene(viewEventsPage);
	}
	
	public void showAddVendorGuestPage(User user, String eventId, String addType) {
		Scene addVendorGuestPage = new AddVendorGuestPage(user, eventId, addType).getUI();
		sceneStack.push(addVendorGuestPage);
		primaryStage.setScene(addVendorGuestPage);
	}
	
	public void showViewInvitationPage(User user) {
		Scene viewInvitationPage = new ViewInvitationPage(user).getUI();
		sceneStack.push(viewInvitationPage);
		primaryStage.setScene(viewInvitationPage);
	}
	
	public void showViewEventDetailsPage(User user, String eventId) {
		Scene viewEventDetailsPage = new ViewEventDetailsPage(user, eventId).getUI();
		sceneStack.push(viewEventDetailsPage);
		primaryStage.setScene(viewEventDetailsPage);
	}
	
	public void showEditEventPage(User user, String eventId) {
		Scene editEventPage = new EditEventPage(user, eventId).getUI();
		sceneStack.push(editEventPage);
		primaryStage.setScene(editEventPage);
	}
	
	public void showViewUsersPage(User user) {
		Scene viewUsersPage = new ViewUsersPage(user).getUI();
		sceneStack.push(viewUsersPage);
		primaryStage.setScene(viewUsersPage);
	}
	
	public void showManageVendorPage(User user) {
		Scene manageVendorPage = new ManageVendorPage(user).getUI();
		sceneStack.push(manageVendorPage);
		primaryStage.setScene(manageVendorPage);
	}
	
	public void showChangeProfilePage(User user) {
		Scene changeProfilePage = new ChangeProfilePage(user).getUI();
		sceneStack.push(changeProfilePage);
		primaryStage.setScene(changeProfilePage);
	}
	
	public void logout() {
		sceneStack.clear();
		showLoginPage();
	}
}
