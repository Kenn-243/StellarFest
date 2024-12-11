package controller;

import java.util.Stack;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.user.HomePage;
import views.user.LoginPage;
import views.user.RegisterPage;

public class ViewController {
	private Stack<Scene> sceneStack;
	private Stage primaryStage;
	
	private static ViewController instance;
	
	public static ViewController getInstance() {
		if(instance == null) instance = new ViewController();
		return instance;
	}
	
	private ViewController() {
		sceneStack = new Stack<Scene>();
	}
	
	public void setStage(Stage stage) {
		this.primaryStage = stage;
	}
	
	public void showLoginPage() {
		Scene loginPage = new LoginPage().getUI();
		sceneStack.push(loginPage);
		primaryStage.setScene(loginPage);
    }
	
	public void showRegisterPage() {
		Scene registerScene = new RegisterPage().getUI();
		sceneStack.push(registerScene);
        primaryStage.setScene(registerScene);
    }
	
	public void showHomePage() {
		Scene homePage = new HomePage().getUI();
		sceneStack.push(homePage);
		primaryStage.setScene(homePage);
	}
}
