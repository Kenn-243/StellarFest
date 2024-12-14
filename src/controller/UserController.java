package controller;

import models.User;

public class UserController {
	public String register(String email, String name, String password, String role) {
		if(getUserByEmail(email) != null) {
			return "Email already exist";
		}
		
		if(getUserByUsername(name) != null) {
			return "Username already exist";
		}
		
		String response = checkRegisterInput(email, name, password, role);
		if(response.equals("Success")) {
			return User.register(email, name, password, role);
		}
		return response;			
	}
	
	public User login(String email, String password) {
		return User.login(email, password);
	}
	
	/*
	 * ASUMSI:
	 * 1. parameter userId ditambahkan supaya bisa dipassing ke db buat validasi password berdasarkan user dan update profile user berdasarkan id
	 */
	public String changeProfile(String userId, String email, String name, String oldPassword, String newPassword) {
		String response = checkChangeProfileInput(userId, email, name, oldPassword, newPassword);
		if(response.equals("Success")) {
			return User.changeProfile(userId, email, name, oldPassword, newPassword);
		}
		return response;			
	}
	
	public User getUserByEmail(String email) {
		return  User.getUserByEmail(email);
	}
	
	public User getUserByUsername(String name) {
		return User.getUserByUsername(name);
	}
	
	public String checkRegisterInput(String email, String name, String password, String role) {		
		if(email.isBlank()){
			return "Email cannot be empty";
		}else if(!email.endsWith("@gmail.com")) {
			return "Email must end with @gmail.com";
		}else if(name.isBlank()) {
			return "Username cannot be empty";
		}else if(password.isBlank()) {
			return "Password cannot be empty";
		}else if(password.length() < 5) {
			return "Password must be at least 5 characters long";
		}else if(role == null){
			return "Role must be picked";
		}
		return "Success";
	}
	
	public String checkLoginInput(String email, String password) {
		if(email.isBlank()) {
			return "Email cannot be empty";
		}else if(password.isBlank()) {
			return "Password cannot be empty";
		}
		return "Success";
	}
	
	/*
	 * ASUMSI:
	 * 1. parameter userId ditambahkan supaya bisa dipassing ke db buat validasi password berdasarkan user
	 */
	public String checkChangeProfileInput(String userId, String email, String name, String oldPassword, String newPassword) {
		User user = User.getUserById(userId);
	    if (!email.isBlank() && !email.equals(user.getUser_email())) {
	        if (getUserByEmail(email) != null) {
	            return "Email must be unique";
	        }
	    }
	    if (!name.isBlank() && !name.equals(user.getUser_name())) {
	        if (getUserByUsername(name) != null) {
	            return "Username must be unique";
	        }
	    }
	    if (!newPassword.isBlank()) {
	        if (oldPassword.isBlank()) {
	            return "Current password is required to set a new password";
	        }
	        if (!User.matchOldPassword(userId, oldPassword)) {
	            return "Current password didn't match";
	        }
	        if (newPassword.length() < 5) {
	            return "New password must be at least 5 characters long";
	        }
	    } else {
	        if (!oldPassword.isBlank()) {
	            return "New password must be filled";
	        }
	    }
	    return "Success";
	}
}
