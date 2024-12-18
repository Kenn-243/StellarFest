package controller;

import models.User;

public class UserController {
	public String register(String email, String name, String password, String role) {
		// validasi email sudah pernah dipakai
		if(getUserByEmail(email) != null) {
			return "Email already exist";
		}
		// validasi username sudah pernah dipakai
		if(getUserByUsername(name) != null) {
			return "Username already exist";
		}
		
		// panggil method checkRegisterInput untuk validasi input dan tampung di dalam variable string response
		String response = checkRegisterInput(email, name, password, role);
		// kalau response = success, maka panggil method register dari User class
		if(response.equals("Success")) {
			return User.register(email, name, password, role);
		}
		return response;			
	}
	
	public User login(String email, String password) {
		// panggil method login dari User class
		return User.login(email, password);
	}
	
	/*
	 * ASUMSI:
	 * 1. parameter userId ditambahkan supaya bisa dipassing ke db buat validasi password berdasarkan user dan update profile user berdasarkan id
	 */
	public String changeProfile(String userId, String email, String name, String oldPassword, String newPassword) {
		// panggil method checkChangeProfileInput untuk validasi input dan tampung di dalam variable string response
		String response = checkChangeProfileInput(userId, email, name, oldPassword, newPassword);
		// kalau response = success, maka panggil method changeProfile dari User class
		if(response.equals("Success")) {
			return User.changeProfile(userId, email, name, oldPassword, newPassword);
		}
		return response;			
	}
	
	public User getUserByEmail(String email) {
		// panggil method getUserByEmail dari User class
		return  User.getUserByEmail(email);
	}
	
	public User getUserByUsername(String name) {
		// panggil method getUserByUsername dari User class
		return User.getUserByUsername(name);
	}
	
	public String checkRegisterInput(String email, String name, String password, String role) {		
		// validasi email tidak boleh kosong
		if(email.isBlank()){
			return "Email cannot be empty";
		}
		// validasi email diakhiri @gmail.com
		else if(!email.endsWith("@gmail.com")) {
			return "Email must end with @gmail.com";
		}
		// validasi username tidak boleh kosong
		else if(name.isBlank()) {
			return "Username cannot be empty";
		}
		// validasi password tidak boleh kosong
		else if(password.isBlank()) {
			return "Password cannot be empty";
		}
		// validasi password setidaknya punya 5 karakter
		else if(password.length() < 5) {
			return "Password must be at least 5 characters long";
		}
		// validasi role harus dipilih
		else if(role == null){
			return "Role must be picked";
		}
		// kalau berhasil lewatin semuanya, return success
		return "Success";
	}
	
	public String checkLoginInput(String email, String password) {
		// validasi email tidak boleh kosong
		if(email.isBlank()) {
			return "Email cannot be empty";
		}
		// validasi password tidak boleh kosong
		else if(password.isBlank()) {
			return "Password cannot be empty";
		}
		// kalau berhasil lewatin semuanya, return success
		return "Success";
	}
	
	/*
	 * ASUMSI:
	 * 1. parameter userId ditambahkan supaya bisa dipassing ke db buat validasi password berdasarkan user
	 */
	public String checkChangeProfileInput(String userId, String email, String name, String oldPassword, String newPassword) {
		User user = User.getUserById(userId);
		// kalau email tidak kosong dan email tidak sama dengan email saat ini, artinya emailnya diubah
	    if (!email.isBlank() && !email.equals(user.getUser_email())) {
	    	// maka validasi email belum pernah dipakai
	        if (getUserByEmail(email) != null) {
	            return "Email must be unique";
	        }
	    }
	    // kalau username tidak kosong dan username tidak sama dengan username saat ini, artinya nama diubah  
	    if (!name.isBlank() && !name.equals(user.getUser_name())) {
	    	// maka validasi username belum pernah dipakai
	        if (getUserByUsername(name) != null) {
	            return "Username must be unique";
	        }
	    }
	    // kalau newPassword tidak kosong, artinya password mau diubah
	    if (!newPassword.isBlank()) {
	    	// validasi oldPassword tidak kosong
	        if (oldPassword.isBlank()) {
	            return "Current password is required to set a new password";
	        }
	        // validasi oldPassword sama dengan yang didatabase
	        if (!User.matchOldPassword(userId, oldPassword)) {
	            return "Current password didn't match";
	        }
	        // validasi newPassword harus punya setidaknya 5 karakter
	        if (newPassword.length() < 5) {
	            return "New password must be at least 5 characters long";
	        }
	    } 
	    // kalau newPassword kosong
	    else {
	    	// tapi oldPassword tidak kosong, artinya mau ubah password
	    	// validasi newPassword tidak boleh kosong
	        if (!oldPassword.isBlank()) {
	            return "New password must be filled";
	        }
	    }
	    // kalau berhasil lewatin semuanya, return success
	    return "Success";
	}
}
