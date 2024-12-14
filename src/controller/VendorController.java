package controller;

import javafx.collections.ObservableList;
import models.Event;
import models.Invitation;
import models.Product;
import models.User;
import models.Vendor;

public class VendorController {
	// ASUMSI: parameter object user ditambahkan supaya bisa update invitation untuk event yang user pilih.
	public String acceptInvitation(String eventID, User user) {
		return Invitation.acceptInvitation(eventID, user);
	}
	
	// ASUMSI: return type-nya menggunakan ObservableList karena di class diagram tidak di-specify
	public ObservableList<Event> viewAcceptedEvents(String email) {
		return Event.viewAcceptedEvents(email);
	}
	
	/*
	 * ASUMSI:
	 * 1. manageVendor = add product
	 * 2. tambah parameter vendorId supaya bisa punay relation dengan vendor di dalam database
	 */
	public String manageVendor(String description, String  product, String vendorId) {
		String response = checkManageVendorInput(description, product);
		if(response.equals("Success")) {
			return Vendor.manageVendor(description, product, vendorId);
		}else {
			return response;
		}
	}
	
	// ASUMSI: checkManageVendorInput = check input ketika add product
	public String checkManageVendorInput(String description, String product) {
		if(product.isBlank()) {
			return "Name is required";
		}else if(description.isBlank()) {
			return "Description is required";
		}else if(description.length() > 200) {
			return "Description must be 200 characters or fewer";
		}else {
			return "Success";
		}
	}
	
	// ditambahkan supaya bisa menampilkan list product milik vendor
	public ObservableList<Product> getAllProducts(String userId){
		return Product.getAllProducts(userId);
	}
	
	public void deleteProduct(String productId) {
		Product.deleteProduct(productId);
	}
}
