package views.user;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;

public class RegisterPage {

	VBox registerContainer;
	
	HBox emailPane;
	Label emailTitle;
	TextField emailField;
	
	HBox usernamePane;
	Label usernameTitle;
	TextField usernameField;
	
	HBox passPane;
	Label passTitle;
	PasswordField passField;
	
	HBox rolePane;
	Label roleTitle;
	ComboBox<String> roleBox;
	
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
		
		passPane = new HBox();
		passTitle = new Label("Password");
		passTitle.setPrefWidth(100);
		passField = new PasswordField();
		
		rolePane = new HBox();
		roleTitle = new Label("Nationality");
		roleTitle.setPrefWidth(100);
		roleBox = new ComboBox<String>();
		roleBox.getItems().addAll("Guest", "Event Organizer", "Vendor", "Admin");
		
		toLoginPane = new HBox();
		toLoginTitle = new Label("Already have an account? ");
		toLoginLink = new Hyperlink("Login");
		toLoginLink.setOnAction(e -> main.showLoginPage());
		
		registerButton = new Button("Register");
		
		emailPane.getChildren().addAll(emailTitle, emailField);
		usernamePane.getChildren().addAll(usernameTitle, usernameField);
		passPane.getChildren().addAll(passTitle, passField);
		rolePane.getChildren().addAll(roleTitle, roleBox);
		toLoginPane.getChildren().addAll(toLoginTitle, toLoginLink);
		
		registerContainer.getChildren().addAll(emailPane, usernamePane, passPane, rolePane, toLoginPane,registerButton);
		registerContainer.setSpacing(10);
	}
	
	public VBox getUI() {
		return registerContainer;
	}
}
