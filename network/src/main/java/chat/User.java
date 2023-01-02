package chat;

import java.io.Writer;

public class User {
	private Writer writer;
	private String name;
	
	public User(Writer writer, String name) {
		this.writer = writer;
		this.name = name;
	}
	
	public Writer getWriter() {
		return this.writer;
	}
	
	public String getName() {
		return this.name;
	}
}