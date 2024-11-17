package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import views.user.LoginPage;
import views.user.RegisterPage;

public class Main extends Application{
	
	 private Stage primaryStage;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void showLoginPage() {
		LoginPage loginPage = new LoginPage(this);
	    primaryStage.setScene(new Scene(loginPage.getUI(), 350, 250));
    }
	
	public void showRegisterPage() {
        RegisterPage registerPage = new RegisterPage(this);
        primaryStage.setScene(new Scene(registerPage.getUI(), 350, 250));
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
        showLoginPage();
        primaryStage.setTitle("StellarFest");
        primaryStage.show();
	}

}
