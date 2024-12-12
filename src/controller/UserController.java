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
	
	public void changeProfile(String email, String name, String oldPassword, String newPassword) {
		
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
	
	public void checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
		
	}
}
