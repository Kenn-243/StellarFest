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
		// panggil method acceptInvitation dari Invitation class
		return Invitation.acceptInvitation(eventID, user);
	}
	
	// ASUMSI: return type-nya menggunakan ObservableList karena di class diagram tidak di-specify
	public ObservableList<Event> viewAcceptedEvents(String email) {
		// panggil method viewAcceptedEvents dari Event class
		return Event.viewAcceptedEvents(email);
	}
	
	/*
	 * ASUMSI:
	 * 1. manageVendor = add product
	 * 2. tambah parameter vendorId supaya bisa punay relation dengan vendor di dalam database
	 */
	public String manageVendor(String description, String  product, String vendorId) {
		// panggil method checkManageVendorInput untuk validasi input dan tampung di dalam variable string response
		String response = checkManageVendorInput(description, product);
		// kalau response = success, panggil method manageVendor dari Vendor class
		if(response.equals("Success")) {
			return Vendor.manageVendor(description, product, vendorId);
		}
		return response;
	}
	
	// ASUMSI: checkManageVendorInput = check input ketika add product
	public String checkManageVendorInput(String description, String product) {
		// validasi nama product tidak kosong
		if(product.isBlank()) {
			return "Name is required";
		}
		// validasi description tidak kosong
		else if(description.isBlank()) {
			return "Description is required";
		}
		// validasi description tidak boleh > 200 huruf
		else if(description.length() > 200) {
			return "Description must be 200 characters or fewer";
		}
		// kalau berhasil lewatin semuanya, return success
		else {
			return "Success";
		}
	}
	
	// ditambahkan supaya bisa menampilkan list product milik vendor
	public ObservableList<Product> getAllProducts(String userId){
		// panggil method getAllProducts dari Product class
		return Product.getAllProducts(userId);
	}
	
	// ditambahkan supaya vendor bisa deleteProduct, tambahan fitur untuk manageVendor
	public void deleteProduct(String productId) {
		// panggil method deleteProduct dari Product class
		Product.deleteProduct(productId);
	}
}
