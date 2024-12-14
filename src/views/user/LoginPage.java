package views.user;

import controller.UserController;
import controller.ViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.User;

public class LoginPage {
	ViewController viewController;
	UserController userController;
	
	public LoginPage() {
		this.viewController = ViewController.getInstance();
		this.userController = new UserController();
	}
	
	public Scene getUI() {
		VBox loginContainer = new VBox();
		loginContainer.setPadding(new Insets(0, 15, 15, 15));
		
		Label titleLabel = new Label("Login");
		titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		
		HBox emailPane = new HBox();
		emailPane.setAlignment(Pos.CENTER);
		Label emailTitle = new Label("Email");
		emailTitle.setPrefWidth(100);
		TextField emailField = new TextField();
		emailField.setPrefWidth(200);
		
		HBox passwordPane = new HBox();
		passwordPane.setAlignment(Pos.CENTER);
		Label passwordTitle = new Label("Password");
		passwordTitle.setPrefWidth(100);
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefWidth(200);
		
		HBox errorPane = new HBox();
		errorPane.setAlignment(Pos.CENTER);
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);
		
		HBox toRegisterPane = new HBox();
		toRegisterPane.setAlignment(Pos.CENTER);
		Label toRegisterTitle = new Label("Don't have an account? ");
		Hyperlink toRegisterLink = new Hyperlink("Register");
		toRegisterLink.setOnAction(e -> {
			viewController.showRegisterPage();
		});
		
		Button loginButton = new Button("Login");
		
		emailPane.getChildren().addAll(emailTitle, emailField);
		passwordPane.getChildren().addAll(passwordTitle, passwordField);
		errorPane.getChildren().addAll(errorLabel);
		toRegisterPane.getChildren().addAll(toRegisterTitle, toRegisterLink);
		
		loginContainer.getChildren().addAll(titleLabel, emailPane, passwordPane, errorPane, toRegisterPane, loginButton);
		loginContainer.setSpacing(10);
	    loginContainer.setAlignment(Pos.CENTER);
		
		loginButton.setOnAction(e -> {
			String response = userController.checkLoginInput(emailField.getText(), passwordField.getText()); 
			if(response.equals("Success")) {
				User user = userController.login(emailField.getText(), passwordField.getText());
				if(user != null) {
					if(user.getUser_role().equals("Event Organizer")) {
						viewController.showViewEventsPage(user);						
					}else if(user.getUser_role().equals("Admin")) {
						viewController.showViewUsersPage(user);
					}else if(user.getUser_role().equals("Guest") || user.getUser_role().equals("Vendor")) {
						viewController.showViewInvitationPage(user);
					}
				}else {
					errorLabel.setText("Wrong email or password");
				}
			}else {
				errorLabel.setText(response);
			}
		});
		
		return new Scene(loginContainer, 600, 250);
	}
}
