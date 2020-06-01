package exceptions;

public class UnknownUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5504335989688713687L;

	public UnknownUserException() {
		super("Utilisateur inconnu");
	}
}
