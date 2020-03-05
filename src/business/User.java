package business;

import java.io.Serializable;

final public class User implements Serializable {
	
	private static final long serialVersionUID = 5147265048973262104L;

	private String username;
	
	private String password;
	private Auth[] authorization;

	private static User loggedInUser;

	public User(String username, String pass, Auth[]  auths) {
		this.username = username;
		this.password = pass;
		this.authorization = auths;
	}
	public static User getLoggedInUser(){
		return loggedInUser;
	}
	public static void setLoggedInUser(User user){
		loggedInUser = user;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public Auth[] getAuthorization() {
		return authorization;
	}
	@Override
	public String toString() {
		return "[" + username + ":" + password + ", " + authorization.toString() + "]";
	}
	
}
