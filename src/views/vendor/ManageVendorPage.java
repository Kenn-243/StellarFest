package views.vendor;

import controller.VendorController;
import controller.ViewController;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Product;
import models.User;
import views.component.Navbar;

public class ManageVendorPage {
	ViewController viewController;
	VendorController vendorController;
	User user;
	
	public ManageVendorPage(User user) {
		this.viewController = ViewController.getInstance();
		this.vendorController = new VendorController();
		this.user = user;
	}
	
	public Scene getUI() {
		VBox manageProductContainer = new VBox();
		manageProductContainer.setPadding(new Insets(0, 15, 15, 15));
		
		BorderPane navbar = new Navbar(user).getUI();
		
		Label titleLabel = new Label("Create Event");
		titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		
		HBox namePane = new HBox();
		namePane.setAlignment(Pos.CENTER);
		Label nameLabel = new Label("Name");
		nameLabel.setPrefWidth(100);
		TextField nameField = new TextField();
		nameField.setPrefWidth(300);
		
		HBox descriptionPane = new HBox();
		descriptionPane.setAlignment(Pos.CENTER);
		Label descriptionLabel = new Label("Description");
		descriptionLabel.setPrefWidth(100);
		TextArea descriptionField = new TextArea();
		descriptionField.setWrapText(false);
		descriptionField.setPrefWidth(300);
		descriptionField.setPrefRowCount(1);
		
		HBox errorPane = new HBox();
		errorPane.setAlignment(Pos.CENTER);
		Label errorLabel = new Label();
		errorLabel.setTextFill(Color.RED);
		
		HBox buttonContainer = new HBox();
		buttonContainer.setSpacing(10);
		buttonContainer.setAlignment(Pos.CENTER);
		Button addProductButton = new Button("Add");
		Button deleteProductButton = new Button("Delete");
		deleteProductButton.setDisable(true);
		
		ObservableList<Product> productList = vendorController.getAllProducts(user.getUser_id());
		
		TableView<Product> productTable = new TableView<Product>();
		
		setupProductTable(productList, productTable);
		
		namePane.getChildren().addAll(nameLabel, nameField);
		descriptionPane.getChildren().addAll(descriptionLabel, descriptionField);
		errorPane.getChildren().addAll(errorLabel);
		buttonContainer.getChildren().addAll(addProductButton, deleteProductButton);
		
		manageProductContainer.getChildren().addAll(navbar, titleLabel, namePane, descriptionPane, errorPane, buttonContainer, productTable);
		manageProductContainer.setSpacing(10);
		manageProductContainer.setAlignment(Pos.CENTER);
		
		addProductButton.setOnAction(e -> {
			errorLabel.setText("");
			String response = vendorController.manageVendor(descriptionField.getText(), nameField.getText(), user.getUser_id());
			if(response.equals("Success")) {
				productTable.getColumns().clear();
				setupProductTable(vendorController.getAllProducts(user.getUser_id()), productTable);
				nameField.clear();
				descriptionField.clear();
			}else {
				errorLabel.setText(response);
			}
		});
		
		productTable.setOnMouseClicked(e->{
			Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
			if(selectedProduct != null) {
				deleteProductButton.setDisable(false);
				deleteProductButton.setOnAction(f -> {
					vendorController.deleteProduct(selectedProduct.getProduct_id());
					productList.remove(selectedProduct);
				});
			}
		});
		
		return new Scene(manageProductContainer, 600, 500);
	}

	public void setupProductTable(ObservableList<Product> productList, TableView<Product> productTable) {
		TableColumn<Product, String> productName = new TableColumn<>("Name");
		productName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
		
		TableColumn<Product, String> productDescription = new TableColumn<>("Description");
		productDescription.setCellValueFactory(new PropertyValueFactory<>("product_description"));

		productTable.getColumns().addAll(productName, productDescription);
		productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		productTable.setItems(productList);
	}
}
