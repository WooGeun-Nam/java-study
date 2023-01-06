package chat;

import java.io.Writer;

public class User {
	private Writer writer;
	private String name;
	private boolean admin;
	
	public User(Writer writer, String name) {
		this.writer = writer;
		this.name = name;
		if (name.equals("admin")) {
			admin = true;
		} else {
			admin = false;
		}
	}
	
	public Writer getWriter() {
		return this.writer;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isAdmin() {
		return this.admin;
	}
}