package views.user;

import controller.ViewController;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import main.Main;

public class HomePage {
	ViewController viewController;
	
	public HomePage() {
		this.viewController = ViewController.getInstance();
	}
	
	public Scene getUI() {
		VBox homeContainer = new VBox();
		return new Scene (homeContainer, 350, 200);
	}
}