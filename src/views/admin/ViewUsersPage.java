package views.admin;

import controller.AdminController;
import controller.ViewController;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.User;
import views.component.Navbar;

public class ViewUsersPage {
	ViewController viewController;
	AdminController adminController;
	User user;
	
	public ViewUsersPage(User loggedInUser) {
		this.viewController = ViewController.getInstance();
		this.adminController = new AdminController();
		this.user = loggedInUser;
	}
	
	public Scene getUI() {
		VBox viewUsersContainer = new VBox();
		viewUsersContainer.setPadding(new Insets(0, 15, 15, 15));
		
		BorderPane navbar = new Navbar(user).getUI();
		
		Label titleLabel = new Label("Users");
		titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		
		HBox buttonContainer = new HBox();
		Button deleteUserButton = new Button("Delete User");
		deleteUserButton.setDisable(true);
		
		ObservableList<User> userList = adminController.getAllUsers();
		
		TableView<User> userTable = new TableView<User>();
		
		setupUserTable(userList, userTable);
		
		buttonContainer.getChildren().add(deleteUserButton);
		
		viewUsersContainer.getChildren().addAll(navbar, titleLabel, buttonContainer, userTable);
		viewUsersContainer.setSpacing(10);
		viewUsersContainer.setAlignment(Pos.CENTER);
		
		userTable.setOnMouseClicked(e->{
			User selectedUser = userTable.getSelectionModel().getSelectedItem();
			if(selectedUser != null) {
				deleteUserButton.setDisable(false);
				deleteUserButton.setOnAction(f -> {
					adminController.deleteUser(selectedUser.getUser_id());
					userList.remove(selectedUser);
				});
			}
		});
		
		return new Scene(viewUsersContainer, 600, 500);
	}

	public void setupUserTable(ObservableList<User> userList, TableView<User> userTable) {
		TableColumn<User, String> userEmail = new TableColumn<>("Email");
		userEmail.setCellValueFactory(new PropertyValueFactory<>("user_email"));
		
		TableColumn<User, String> userName = new TableColumn<>("Userame");
		userName.setCellValueFactory(new PropertyValueFactory<>("user_name"));
		
		TableColumn<User, String> userRole = new TableColumn<>("Role");
		userRole.setCellValueFactory(new PropertyValueFactory<>("user_role"));
		
		userTable.getColumns().addAll(userEmail, userName, userRole);
		userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		userTable.setItems(userList);
	}
}
