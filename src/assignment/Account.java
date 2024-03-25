package assignment;

public class Account {
	private int userID;
	private String username;
	private String password;
	private int age;
	private int phoneNum;
	private String gender;
	private String email;
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Account(int userID, int age, int phoneNum, String username, String password, String gender, String email) {
		 	this.userID = userID;
	        this.username = username;
	        this.password = password;
	        this.age = age;
	        this.phoneNum = phoneNum;
	        this.gender = gender;
	        this.email = email;
	}
}

