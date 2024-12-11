package views.user;

import controller.ViewController;
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
	
	public LoginPage() {
		this.viewController = ViewController.getInstance();
	}
	
	public Scene getUI() {
		VBox loginContainer = new VBox();
		
		HBox emailPane = new HBox();
		Label emailTitle = new Label("Email");
		emailTitle.setPrefWidth(100);
		TextField emailField = new TextField();
		
		HBox passwordPane = new HBox();
		Label passwordTitle = new Label("Password");
		passwordTitle.setPrefWidth(100);
		PasswordField passwordField = new PasswordField();
		
		HBox errorPane = new HBox();
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);
		
		HBox toRegisterPane = new HBox();
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
		
		loginContainer.getChildren().addAll(emailPane, passwordPane, errorPane, toRegisterPane, loginButton);
		loginContainer.setSpacing(10);
		
		loginButton.setOnAction(e -> {
			// kayaknya ga perlu static buat function loginnya
			String response = controller.UserController.login(emailField.getText(), passwordField.getText());
			if(response.equals("Success")) {
				viewController.showHomePage();
			}else {
				errorLabel.setText(response);
			}
		});
		
		return new Scene(loginContainer, 350, 250);
	}
}
