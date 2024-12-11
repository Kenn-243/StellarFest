package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.user.HomePage;
import views.user.LoginPage;
import views.user.RegisterPage;

public class Main extends Application{
	
	private Stage primaryStage;
	
	public static void main(String[] args) {
		launch(args);
		//testttt
	}
	
	public void showLoginPage() {
		LoginPage loginPage = new LoginPage(this);
	    primaryStage.setScene(new Scene(loginPage.getUI(), 350, 250));
    }
	
	public void showRegisterPage() {
        RegisterPage registerPage = new RegisterPage(this);
        primaryStage.setScene(new Scene(registerPage.getUI(), 350, 250));
    }
	
	public void showHomePage() {
		HomePage homePage = new HomePage(this);
		primaryStage.setScene(new Scene(homePage.getUI(), 350, 250));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
        showLoginPage();
        primaryStage.setTitle("StellarFest");
        primaryStage.show();
	}

}
