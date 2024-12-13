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
			response = User.register(email, name, password, role);
		}
		
		return response;
	}
	
	public User login(String email, String password) {
		return User.login(email, password);
	}
	
	// ASUMSI: perlu passing object user supaya bisa mendapatkan user_id
	public String changeProfile(User user, String email, String name, String oldPassword, String newPassword) {
		String response = checkChangeProfileInput(user, email, name, oldPassword, newPassword);
		if(response.equals("Success")) {
			response = User.changeProfile(user, email, name, oldPassword, newPassword);
		}
		
		return response;
	}
	
	public User getUserByEmail(String email) {
		User user = User.getUserByEmail(email);
		return user;
	}
	
	public User getUserByUsername(String name) {
		User user = User.getUserByUsername(name);
		return user;
	}
	
	public String checkRegisterInput(String email, String name, String password, String role) {
		boolean hasLetter = false, hasNum =false;
		
		for (char c : password.toCharArray()) {
			if(Character.isLetter(c)) {
				hasLetter = true;
			}else if(Character.isDigit(c)) {
				hasNum = true;
			}
		}
		
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
		}else if(!hasLetter || !hasNum) {
			return "Password must be alphanumeric";
		}else if(role == null){
			return "Role must be picked";
		}else {
			return "Success";
		}
	}
	
	public String checkLoginInput(String email, String password) {
		if(email.isBlank()) {
			return "Email cannot be empty";
		}else if(password.isBlank()) {
			return "Password cannot be empty";
		}else {
			return "Success";
		}
	}
	
	public String checkChangeProfileInput(User user, String email, String name, String oldPassword, String newPassword) {
		if(getUserByEmail(email) != null) {
			return "Email must be unique";
		}else if(getUserByUsername(name) != null) {
			return "Username must be unique";
		}else if(!oldPassword.equals(user.getUser_password())) {
			return "Old password must be the same";
		}else if(newPassword.length() < 5) {
			return "New password must be at least 5 characters long";
		}else {
			return "Success";
		}
	}
}
