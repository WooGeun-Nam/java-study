package exception;

import java.io.IOException;

public class MyClassTest {
	public static void main(String[] args) {
		try {
			MyClass myclass = new MyClass();
			myclass.danger();
		} catch (Exception e) {
			System.out.println("error: " + e);
		}
	}
}
