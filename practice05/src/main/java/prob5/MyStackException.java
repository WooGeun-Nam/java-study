package prob5;

public class MyStackException extends RuntimeException {
	public MyStackException() {	
	}
    public MyStackException(String msg) {
    	super(msg);
    }
}