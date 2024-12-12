package views.user;

import controller.ViewController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.User;

public class HomePage {
	ViewController viewController;
	User user;
	
	public HomePage(User loggedInUser) {
		this.viewController = ViewController.getInstance();
		this.user = loggedInUser;
	}
	
	public Scene getUI() {
		VBox homeContainer = new VBox();
		
		Label welcomeLabel = new Label("Welcome, " + user.getUser_name() + user.getUser_role());
		homeContainer.getChildren().addAll(welcomeLabel);
		
		return new Scene (homeContainer, 350, 200);
	}
}