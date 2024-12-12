package views.user;

import controller.UserController;
import controller.ViewController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class RegisterPage {
	ViewController viewController;
	UserController userController = new UserController(); 
	
	public RegisterPage() {
		this.viewController = ViewController.getInstance();
	}

	public Scene getUI() {
		VBox registerContainer = new VBox();
		
		HBox emailPane = new HBox();
		Label emailTitle = new Label("Email");
		emailTitle.setPrefWidth(100);
		TextField emailField = new TextField();
		
		HBox usernamePane = new HBox();
		Label usernameTitle = new Label("Username");
		usernameTitle.setPrefWidth(100);
		TextField usernameField = new TextField();
		
		HBox passwordPane = new HBox();
		Label passwordTitle = new Label("Password");
		passwordTitle.setPrefWidth(100);;
		PasswordField passwordField = new PasswordField();
		
		HBox rolePane = new HBox();
		Label roleTitle = new Label("Role");
		roleTitle.setPrefWidth(100);
		ComboBox<String> roleBox = new ComboBox<String>();
		roleBox.getItems().addAll("Guest", "Event Organizer", "Vendor", "Admin");
		
		HBox errorPane = new HBox();
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);
		
		HBox toLoginPane = new HBox();
		Label toLoginTitle = new Label("Already have an account? ");
		Hyperlink toLoginLink = new Hyperlink("Login");
		toLoginLink.setOnAction(e -> {
			viewController.showLoginPage();
		});
		
		Button registerButton = new Button("Register");
		
		emailPane.getChildren().addAll(emailTitle, emailField);
		usernamePane.getChildren().addAll(usernameTitle, usernameField);
		passwordPane.getChildren().addAll(passwordTitle, passwordField);
		rolePane.getChildren().addAll(roleTitle, roleBox);
		errorPane.getChildren().addAll(errorLabel);
		toLoginPane.getChildren().addAll(toLoginTitle, toLoginLink);
		
		registerContainer.getChildren().addAll(emailPane, usernamePane, passwordPane, rolePane, errorPane, toLoginPane,registerButton);
		registerContainer.setSpacing(10);
		
		registerButton.setOnAction(e -> {
			String response = userController.register(emailField.getText(), usernameField.getText(), passwordField.getText(), roleBox.getValue());
			if(response.equals("Success")) {
				viewController.showLoginPage();
			}else {
				errorLabel.setText(response);
			}
		});
		return new Scene (registerContainer, 350, 250);
	}
}
