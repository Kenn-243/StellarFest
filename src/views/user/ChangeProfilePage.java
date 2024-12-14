package views.user;

import controller.UserController;
import controller.ViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.User;
import views.component.Navbar;

public class ChangeProfilePage {
	ViewController viewController;
	UserController userController;
	User user;
	
	public ChangeProfilePage(User loggedInUser) {
		this.viewController = ViewController.getInstance();
		this.userController = new UserController();
		this.user = loggedInUser;
	}
	
	public Scene getUI() {
		VBox changeProfileContainer = new VBox();
		changeProfileContainer.setPadding(new Insets(0, 15, 15, 15));
		
		BorderPane navbar = new Navbar(user).getUI();
		
		Label titleLabel = new Label("Change Profile");
		titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		
		HBox emailPane = new HBox();
		emailPane.setAlignment(Pos.CENTER);
		Label emailTitle = new Label("Email");
		emailTitle.setPrefWidth(100);
		TextField emailField = new TextField();
		emailField.setPrefWidth(200);
		emailField.setText(user.getUser_email());
		
		HBox usernamePane = new HBox();
		usernamePane.setAlignment(Pos.CENTER);
		Label usernameTitle = new Label("Username");
		usernameTitle.setPrefWidth(100);
		TextField usernameField = new TextField();
		usernameField.setPrefWidth(200);
		usernameField.setText(user.getUser_name());
		
		HBox currentPasswordPane = new HBox();
		currentPasswordPane.setAlignment(Pos.CENTER);
		Label currentPasswordTitle = new Label("Current Password");
		currentPasswordTitle.setPrefWidth(100);;
		PasswordField currentPasswordField = new PasswordField();
		currentPasswordField.setPrefWidth(200);
		
		HBox newPasswordPane = new HBox();
		newPasswordPane.setAlignment(Pos.CENTER);
		Label newPasswordTitle = new Label("New Password");
		newPasswordTitle.setPrefWidth(100);;
		PasswordField newPasswordField = new PasswordField();
		newPasswordField.setPrefWidth(200);
		
		HBox errorPane = new HBox();
		errorPane.setAlignment(Pos.CENTER);
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);
		
		Button saveChangesButton = new Button("Save Changes");
		
		emailPane.getChildren().addAll(emailTitle, emailField);
		usernamePane.getChildren().addAll(usernameTitle, usernameField);
		currentPasswordPane.getChildren().addAll(currentPasswordTitle, currentPasswordField);
		newPasswordPane.getChildren().addAll(newPasswordTitle, newPasswordField);
		errorPane.getChildren().addAll(errorLabel);
		
		changeProfileContainer.getChildren().addAll(navbar, titleLabel, emailPane, usernamePane, currentPasswordPane, newPasswordPane, errorPane, saveChangesButton);
		changeProfileContainer.setSpacing(10);
		changeProfileContainer.setAlignment(Pos.CENTER);
		
		saveChangesButton.setOnAction(e -> {
		    String response = userController.changeProfile(user.getUser_id(), emailField.getText(), usernameField.getText(), currentPasswordField.getText(), newPasswordField.getText());
		    if (response.equals("Success")) {
		        user = User.getUserById(user.getUser_id());
		        emailField.setText(user.getUser_email());
		        usernameField.setText(user.getUser_name());
		        currentPasswordField.setText("");
		        newPasswordField.setText("");
		        errorLabel.setText("Profile updated successfully!");
		        errorLabel.setTextFill(Color.GREEN);
		        BorderPane updatedNavbar = new Navbar(user).getUI();
		        changeProfileContainer.getChildren().set(0, updatedNavbar);
		    } else {
		        errorLabel.setText(response);
		        errorLabel.setTextFill(Color.RED);
		    }
		});
		
		return new Scene(changeProfileContainer, 600, 300);
	}
}
