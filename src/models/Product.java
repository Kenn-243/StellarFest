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
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Product
	public static ObservableList<Product> getAllProducts(String userId){
		// inisialisasi productList untuk menampung product milik vendor
		ObservableList<Product> productList = FXCollections.observableArrayList();
		
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query untuk mengambil product milik vendor berdasarkan vendor_id
		String query = "SELECT * FROM `product` WHERE vendor_id = ?";
		
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setString(1, userId);
			// gunakan executeQuery dan tampung ke dalam variable ResultSet result
			ResultSet result = connection.executeQuery();
			// selagi result masih ada isi, maka isinya akan ditambahkan ke dalam productList
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
	// dijadikan static supaya bisa dipanggil tanpa perlu bikin object Product
	public static void deleteProduct(String productId) {
		DatabaseConnection connection = DatabaseConnection.getInstance();
		// query buat delete product berdasarkan product id
		String query = "DELETE FROM `product` WHERE product_id = ?";
		connection.setPreparedStatement(query);
		try {
			connection.getPreparedStatement().setInt(1, Integer.parseInt(productId));
			// gunakan executeUpdate supaya query tersebut diexecute tanpa return apapun, hanya update isi database aja
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
