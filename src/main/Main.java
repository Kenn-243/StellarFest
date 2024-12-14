package main;

import controller.ViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ViewController viewController = ViewController.getInstance();
		viewController.setStage(primaryStage);
        viewController.showLoginPage();
        primaryStage.setTitle("StellarFest");
        primaryStage.show();
	}
}
