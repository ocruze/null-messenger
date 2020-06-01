package exceptions;

public class RegisterWithoutPasswordException extends Exception {
	
	private static final long serialVersionUID = -4405561708047168549L;

	public RegisterWithoutPasswordException() {
		super("No password given for registration !");
	}
	
}
