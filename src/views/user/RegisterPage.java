package views.user;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Main;

public class RegisterPage {

	VBox registerContainer;
	
	HBox emailPane;
	Label emailTitle;
	TextField emailField;
	
	HBox usernamePane;
	Label usernameTitle;
	TextField usernameField;
	
	HBox passwordPane;
	Label passwordTitle;
	PasswordField passwordField;
	
	HBox rolePane;
	Label roleTitle;
	ComboBox<String> roleBox;
	
	HBox errorPane;
	Label errorLabel;
	
	HBox toLoginPane;
	Label toLoginTitle;
	Hyperlink toLoginLink;
	
	Button registerButton;
	
	public RegisterPage(Main main) {
		registerContainer = new VBox();
		
		emailPane = new HBox();
		emailTitle = new Label("Email");
		emailTitle.setPrefWidth(100);
		emailField = new TextField();
		
		usernamePane = new HBox();
		usernameTitle = new Label("Username");
		usernameTitle.setPrefWidth(100);
		usernameField = new TextField();
		
		passwordPane = new HBox();
		passwordTitle = new Label("Password");
		passwordTitle.setPrefWidth(100);
		passwordField = new PasswordField();
		
		rolePane = new HBox();
		roleTitle = new Label("Nationality");
		roleTitle.setPrefWidth(100);
		roleBox = new ComboBox<String>();
		roleBox.getItems().addAll("Guest", "Event Organizer", "Vendor", "Admin");
		
		errorPane = new HBox();
		errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);
		
		toLoginPane = new HBox();
		toLoginTitle = new Label("Already have an account? ");
		toLoginLink = new Hyperlink("Login");
		toLoginLink.setOnAction(e -> main.showLoginPage());
		
		registerButton = new Button("Register");
		
		emailPane.getChildren().addAll(emailTitle, emailField);
		usernamePane.getChildren().addAll(usernameTitle, usernameField);
		passwordPane.getChildren().addAll(passwordTitle, passwordField);
		rolePane.getChildren().addAll(roleTitle, roleBox);
		errorPane.getChildren().addAll(errorLabel);
		toLoginPane.getChildren().addAll(toLoginTitle, toLoginLink);
		
		registerContainer.getChildren().addAll(emailPane, usernamePane, passwordPane, rolePane, errorPane, toLoginPane,registerButton);
		registerContainer.setSpacing(10);
		
		registerButton.setOnAction(e -> {
			String response = controller.UserController.register(emailField.getText(), usernameField.getText(), passwordField.getText(), roleBox.getValue());
			if(response.equals("Success")) {
				main.showLoginPage();
			}else {
				errorLabel.setText(response);
			}
		});
	}
	
	public VBox getUI() {
		return registerContainer;
	}
}
