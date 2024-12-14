package views.user;

import controller.UserController;
import controller.ViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
	UserController userController; 
	
	public RegisterPage() {
		this.viewController = ViewController.getInstance();
		this.userController = new UserController();
	}

	public Scene getUI() {
		VBox registerContainer = new VBox();
		registerContainer.setPadding(new Insets(0, 15, 15, 15));
		
		Label titleLabel = new Label("Register");
		titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		
		HBox emailPane = new HBox();
		emailPane.setAlignment(Pos.CENTER);
		Label emailTitle = new Label("Email");
		emailTitle.setPrefWidth(100);
		TextField emailField = new TextField();
		emailField.setPrefWidth(200);
		
		HBox usernamePane = new HBox();
		usernamePane.setAlignment(Pos.CENTER);
		Label usernameTitle = new Label("Username");
		usernameTitle.setPrefWidth(100);
		TextField usernameField = new TextField();
		usernameField.setPrefWidth(200);
		
		HBox passwordPane = new HBox();
		passwordPane.setAlignment(Pos.CENTER);
		Label passwordTitle = new Label("Password");
		passwordTitle.setPrefWidth(100);;
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefWidth(200);
		
		HBox rolePane = new HBox();
		rolePane.setAlignment(Pos.CENTER);
		Label roleTitle = new Label("Role");
		roleTitle.setPrefWidth(100);
		ComboBox<String> roleBox = new ComboBox<String>();
		roleBox.getItems().addAll("Guest", "Event Organizer", "Vendor", "Admin");
		roleBox.setPrefWidth(200);
		
		HBox errorPane = new HBox();
		errorPane.setAlignment(Pos.CENTER);
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);
		
		HBox toLoginPane = new HBox();
		toLoginPane.setAlignment(Pos.CENTER);
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
		
		registerContainer.getChildren().addAll(titleLabel, emailPane, usernamePane, passwordPane, rolePane, errorPane, toLoginPane,registerButton);
		registerContainer.setSpacing(10);
		registerContainer.setAlignment(Pos.CENTER);
		
		registerButton.setOnAction(e -> {
			String response = userController.register(emailField.getText(), usernameField.getText(), passwordField.getText(), roleBox.getValue());
			if(response.equals("Success")) {
				viewController.showLoginPage();
			}else {
				errorLabel.setText(response);
			}
		});
		return new Scene (registerContainer, 600, 300);
	}
}
