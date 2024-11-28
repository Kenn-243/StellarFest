package controller;

import models.User;

public class UserController {
	public static String register(String email, String name, String password, String role) {
		User user;
		String response;
		
		user = getUserByEmail(email);
		if(user != null) {
			response = "Email already exist";
			return response;
		}
		
		user = getUserByUsername(name);
		if(user != null) {
			response = "Username already exist";
			return response;
		}
		
		response = checkRegisterInput(email, name, password);
		if(response.equals("Success")) {
			response = User.register(email, name, password, role);
		}
		
		return response;
	}
	
	public static String login(String email, String password) {
		String response;
		
		if(email.isBlank()) {
			response = "Email cannot be empty";
		}else if(password.isBlank()) {
			response = "Password cannot be empty";
		}else {
			response = models.User.login(email, password);
		}
		
		return response;
	}
	
	public static void changeProfile(String email, String name, String oldPassword, String newPassword) {
		
	}
	
	public static User getUserByEmail(String email) {
		User user = models.User.getUserByEmail(email);
		return user;
	}
	
	public static User getUserByUsername(String name) {
		User user = models.User.getUserByUsername(name);
		return user;
	}
	
	public static String checkRegisterInput(String email, String name, String password) {
		String response;
		boolean hasLetter = false, hasNum =false;
		
		for (char c : password.toCharArray()) {
			if(Character.isLetter(c)) {
				hasLetter = true;
			}else if(Character.isDigit(c)) {
				hasNum = true;
			}
		}
		
		if(email.length() < 5 || !email.endsWith("@gmail.com")) {
			response = "Email length must be more than 5 and ends with @gmail.com";
		}else if(name.isBlank()) {
			response = "Username cannot be empty";
		}else if(!hasLetter || !hasNum) {
			response = "Password must be alphanumeric";
		}else {
			response = "Success";
		}
		
		return response;
	}
	
	public static void checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
		
	}
}
