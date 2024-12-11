package views.user;

import javafx.scene.layout.VBox;
import main.Main;

public class HomePage {
	VBox homeContainer;
	
	public HomePage(Main main) {
		homeContainer = new VBox();
	}
	
	public VBox getUI() {
		return homeContainer;
	}
}