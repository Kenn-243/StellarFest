package models;

public class User {
	private String user_id;
	private String user_email;
	private String user_name;
	private String user_password;
	private String user_role;
	
	public static void register(String email, String name, String password, String role) {
		
	}
	
	public static void login(String email, String password) {
		
	}
	
	public static void changeProfile(String email, String name, String oldPassword, String newPassword) {
		
	}
	
	public static void getUserByEmail(String email) {
		
	}
	
	public static void getUserByUsername(String name) {
		
	}
	
	public static void checkRegisterInput(String email, String name, String password) {
		
	}
	
	public static void checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
		
	}
}
