package business;

import java.io.Serializable;

final public class Author extends Person implements Serializable {
	private String bio;
	private String credentials;

	private static final long serialVersionUID = 7508481940058530471L;

	public Author(String f, String l, String t, Address a, String bio) {
		super(f, l, t, a);
		this.bio = bio;
	}

	public String getCredentials() {
		return credentials;
	}

	public String getBio() {
		return bio;
	}
}
