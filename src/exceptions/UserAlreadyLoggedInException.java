package exceptions;

public class UserAlreadyLoggedInException extends Exception {

	private static final long serialVersionUID = 5500551766025538637L;

	public UserAlreadyLoggedInException() {
		super("Utilisateur déjà connecté");
	}
}
