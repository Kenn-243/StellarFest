package controller;

import java.util.Stack;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Event;
import models.User;
import views.eventOrganizer.AddGuestPage;
import views.eventOrganizer.AddVendorPage;
import views.eventOrganizer.CreateEventPage;
import views.user.HomePage;
import views.user.LoginPage;
import views.user.RegisterPage;
import views.user.ViewEventsPage;

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
	
	public void showAddVendorPage(User user, Event event) {
		Scene addVendorPage = new AddVendorPage(user, event).getUI();
		sceneStack.push(addVendorPage);
		primaryStage.setScene(addVendorPage);
		primaryStage.show();
	}
	
	public void showAddGuestPage(User user, Event event) {
		Scene addVendorPage = new AddGuestPage(user, event).getUI();
		sceneStack.push(addVendorPage);
		primaryStage.setScene(addVendorPage);
		primaryStage.show();
	}
}
