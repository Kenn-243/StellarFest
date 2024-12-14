package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
	private String product_id;
	private String vendor_id;
	private String product_name;
	private String product_description;
	
	public Product(String product_id, String vendor_id, String product_name, String product_description) {
		super();
		this.product_id = product_id;
		this.vendor_id = vendor_id;
		this.product_name = product_name;
		this.product_description = product_description;
	}
	
	// ditambahkan supaya bisa menampilkan list product milik vendor
	public static ObservableList<Product> getAllProducts(String userId){
		ObservableList<Product> productList = FXCollections.observableArrayList();
		
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "SELECT * FROM `product` WHERE vendor_id = ?";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setString(1, userId);
			ResultSet result = connection.executeQuery();
			while(result.next()) {
				String product_id = String.valueOf(result.getInt("product_id"));
				String vendor_id = String.valueOf(result.getInt("vendor_id"));
				String product_name = result.getString("product_name");
				String product_description = result.getString("product_description");
				productList.add(new Product(product_id, vendor_id, product_name, product_description));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return productList;
	}
	
	// ditambahkan supaya vendor bisa delete product, buat nambahin fungsionalitas ke manage vendor
	public static void deleteProduct(String productId) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		String query = "DELETE FROM `product` WHERE product_id = ?";
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(productId));
			connection.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getProduct_id() {
		return product_id;
	}
	
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	
	public String getVendor_id() {
		return vendor_id;
	}
	
	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}
	
	public String getProduct_name() {
		return product_name;
	}
	
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
	public String getProduct_description() {
		return product_description;
	}
	
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
}
