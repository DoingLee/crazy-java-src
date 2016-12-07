package org.crazyit.book.vo;

public class User extends ValueObject {

	private String USER_NAME;
	
	private String USER_PASSWORD;

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String user_name) {
		USER_NAME = user_name;
	}

	public String getUSER_PASSWORD() {
		return USER_PASSWORD;
	}

	public void setUSER_PASSWORD(String user_password) {
		USER_PASSWORD = user_password;
	}
	
	
}
