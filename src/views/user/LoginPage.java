package views.user;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Main;

public class LoginPage {
	
	VBox loginContainer;
	
	HBox emailPane;
	Label emailTitle;
	TextField emailField;
	
	HBox passwordPane;
	Label passwordTitle;
	PasswordField passwordField;
	
	HBox errorPane;
	Label errorLabel;
	
	HBox toRegisterPane;
	Label toRegisterTitle;
	Hyperlink toRegisterLink;
	
	Button loginButton;
	
	public LoginPage(Main main) {
		loginContainer = new VBox();
		
		emailPane = new HBox();
		emailTitle = new Label("Email");
		emailTitle.setPrefWidth(100);
		emailField = new TextField();
		
		passwordPane = new HBox();
		passwordTitle = new Label("Password");
		passwordTitle.setPrefWidth(100);
		passwordField = new PasswordField();
		
		errorPane = new HBox();
		errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);
		
		toRegisterPane = new HBox();
		toRegisterTitle = new Label("Don't have an account? ");
		toRegisterLink = new Hyperlink("Register");
		toRegisterLink.setOnAction(e -> main.showRegisterPage());
		
		loginButton = new Button("Login");
		
		emailPane.getChildren().addAll(emailTitle, emailField);
		passwordPane.getChildren().addAll(passwordTitle, passwordField);
		errorPane.getChildren().addAll(errorLabel);
		toRegisterPane.getChildren().addAll(toRegisterTitle, toRegisterLink);
		
		loginContainer.getChildren().addAll(emailPane, passwordPane, errorPane, toRegisterPane, loginButton);
		loginContainer.setSpacing(10);
		
		loginButton.setOnAction(e -> {
			String response = controller.UserController.login(emailField.getText(), passwordField.getText());
			if(response.equals("Success")) {
				main.showHomePage();
			}else {
				errorLabel.setText(response);
			}
		});
	}
	
	public VBox getUI() {
		return loginContainer;
	}
}
