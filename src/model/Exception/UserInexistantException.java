package model.Exception;

public class UserInexistantException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5504335989688713687L;

	public UserInexistantException() {
		super("Utilisateur n'existe pas");
	}
}
