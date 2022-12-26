package prob05;

@SuppressWarnings("serial")
public class PasswordDismatchException extends RuntimeException{
    public PasswordDismatchException(String msg) {
    	super(msg);
    }
}
